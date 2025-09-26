package cn.onesorigin.lemon.console.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.onesorigin.lemon.console.auth.convert.RouteConvert;
import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import cn.onesorigin.lemon.console.auth.model.resp.RouteResp;
import cn.onesorigin.lemon.console.auth.service.AuthService;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.GlobalConstants;
import cn.onesorigin.lemon.console.common.constant.OptionConstants;
import cn.onesorigin.lemon.console.common.context.RoleContext;
import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.common.context.UserExtraContext;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.convert.UserConvert;
import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import cn.onesorigin.lemon.console.system.enums.PasswordPolicyEnum;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.util.ServletUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.core.util.validation.Validator;
import top.continew.starter.extension.crud.annotation.TreeField;
import top.continew.starter.extension.crud.autoconfigure.CrudProperties;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static cn.onesorigin.lemon.console.system.enums.PasswordPolicyEnum.PASSWORD_EXPIRATION_DAYS;

/**
 * 认证业务实现
 *
 * @author BruceMaa
 * @since 2025-09-04 16:49
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final ClientService clientService;
    private final OptionService optionService;
    private final UserService userService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final MenuService menuService;
    private final CrudProperties crudProperties;
    private final PasswordEncoder passwordEncoder;
    private final ThreadPoolTaskExecutor executor;
    private static final String CLIENT_ID = "clientId";

    @Override
    public LoginResp login(AccountLoginReq req, HttpServletRequest request) {
        // 校验客户端
        var client = clientService.getByClientId(req.getClientId());
        ValidationUtils.throwIfNull(client, "客户端不存在");
        ValidationUtils.throwIf(client.getStatus() == DisEnableStatusEnum.DISABLE, "客户端已禁用");
        // 登录前置处理
        this.preLogin(req);
        // 登录
        return this.login(req, client, request);
    }

    @Override
    public List<RouteResp> buildRouteTree(Long userId) {
        Set<RoleContext> roleSet = roleService.findRoleContextsByUserId(userId);
        if (CollUtil.isEmpty(roleSet)) {
            return new ArrayList<>(0);
        }
        // 查询菜单列表
        Set<MenuResp> menuSet = new LinkedHashSet<>();
        if (roleSet.stream().anyMatch(r -> SystemConstants.SUPER_ADMIN_ROLE_ID.equals(r.getId()))) {
            menuSet.addAll(menuService.findByRoleId(SystemConstants.SUPER_ADMIN_ROLE_ID));
        } else {
            roleSet.forEach(r -> menuSet.addAll(menuService.findByRoleId(r.getId())));
        }
        List<MenuResp> menuList = menuSet.stream().filter(m -> !MenuTypeEnum.BUTTON.equals(m.getType())).toList();
        if (CollUtil.isEmpty(menuList)) {
            return new ArrayList<>(0);
        }
        // 构建路由树
        TreeField treeField = MenuResp.class.getDeclaredAnnotation(TreeField.class);
        TreeNodeConfig treeNodeConfig = crudProperties.getTree().genTreeNodeConfig(treeField);
        List<Tree<Long>> treeList = TreeUtil.build(menuList, treeField.rootId(), treeNodeConfig, (m, tree) -> {
            tree.setId(m.getId());
            tree.setParentId(m.getParentId());
            tree.setName(m.getTitle());
            tree.setWeight(m.getSort());
            tree.putExtra("type", m.getType().getValue());
            tree.putExtra("path", m.getPath());
            tree.putExtra("name", m.getName());
            tree.putExtra("component", m.getComponent());
            tree.putExtra("redirect", m.getRedirect());
            tree.putExtra("icon", m.getIcon());
            tree.putExtra("isExternal", m.getIsExternal());
            tree.putExtra("isCache", m.getIsCache());
            tree.putExtra("isHidden", m.getIsHidden());
            tree.putExtra("permission", m.getPermission());
        });
        return RouteConvert.INSTANCE.convert(treeList);
    }

    private void preLogin(AccountLoginReq req) {
        // 参数校验
        Validator.validate(req);

        // 校验验证码
        int loginCaptchaEnabled = optionService.getValueByCode2Int(OptionConstants.LOGIN_CAPTCHA_ENABLED);
        if (GlobalConstants.Boolean.YES == loginCaptchaEnabled) {
            ValidationUtils.throwIfBlank(req.getCaptchaCode(), "验证码不能为空");
            ValidationUtils.throwIfBlank(req.getCaptchaKey(), "验证码标识不能为空");
            String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + req.getCaptchaKey();
            String captcha = RedisUtils.get(captchaKey);
            ValidationUtils.throwIfBlank(captcha, "验证码已失效");
            RedisUtils.delete(captchaKey);
            ValidationUtils.throwIfNotEqualIgnoreCase(req.getCaptchaCode(), captcha, "验证码错误");
        }
    }

    private LoginResp login(AccountLoginReq req, ClientResp client, HttpServletRequest request) {
        // 解密密码
        String password = SecureUtils.decryptPasswordByRsaPrivateKey(req.getPassword(), "密码解密失败");
        // 验证用户名密码
        String username = req.getUsername();
        var user = userService.findByUsername(username);
        var isError = Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword());
        // 检查账号锁定状态
        this.checkUserLocked(req.getUsername(), request, isError);
        ValidationUtils.throwIf(isError, "用户名或密码错误");
        // 检查用户状态
        this.checkUserStatus(user);
        // 执行认证
        return this.authenticate(user, client);
    }

    /**
     * 认证
     *
     * @param user   用户信息
     * @param client 客户端信息
     * @return 登录响应参数
     */
    private LoginResp authenticate(UserDO user, ClientResp client) {
        // 获取权限、角色、密码过期天数
        Long userId = user.getId();
        CompletableFuture<Set<String>> permissionFuture = CompletableFuture.supplyAsync(() -> roleService.findPermissionsByUserId(userId), executor);
        CompletableFuture<Set<RoleContext>> roleFuture = CompletableFuture.supplyAsync(() -> roleService.findRoleContextsByUserId(userId), executor);
        CompletableFuture<Integer> passwordExpirationDaysFuture = CompletableFuture.supplyAsync(() -> optionService
                .getValueByCode2Int(PASSWORD_EXPIRATION_DAYS.name()));
        CompletableFuture.allOf(permissionFuture, roleFuture, passwordExpirationDaysFuture);
        UserContext userContext = new UserContext(permissionFuture.join(), roleFuture
                .join(), passwordExpirationDaysFuture.join());
        UserConvert.INSTANCE.copyField(user, userContext);
        // 设置登录配置参数
        SaLoginParameter loginParameter = new SaLoginParameter();
        loginParameter.setActiveTimeout(client.getActiveTimeout());
        loginParameter.setTimeout(client.getTimeout());
        loginParameter.setDeviceType(client.getClientType());
        userContext.setClientType(client.getClientType());
        loginParameter.setExtra(CLIENT_ID, client.getClientId());
        userContext.setClientId(client.getClientId());
        // 登录并缓存用户信息
        StpUtil.login(userContext.getId(), loginParameter.setExtraData(BeanUtil
                .beanToMap(new UserExtraContext(ServletUtils.getRequest()))));
        UserContextHolder.setContext(userContext);
        return LoginResp.builder()
                .token(StpUtil.getTokenValue())
                .build();
    }

    /**
     * 检查用户状态
     *
     * @param user 用户信息
     */
    private void checkUserStatus(UserDO user) {
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, user.getStatus(), "此账号已被禁用，如有疑问，请联系管理员");
        DeptDO dept = deptService.getById(user.getDeptId());
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, dept.getStatus(), "此账号所属部门已被禁用，如有疑问，请联系管理员");
    }

    /**
     * 检测用户是否已被锁定
     *
     * @param username 用户名
     * @param request  请求对象
     * @param isError  是否登录失败
     */
    private void checkUserLocked(String username, HttpServletRequest request, boolean isError) {
        // 是否不锁定
        var maxErrorCount = optionService.getValueByCode2Int(PasswordPolicyEnum.PASSWORD_ERROR_LOCK_COUNT.name());
        if (maxErrorCount <= 0) {
            return;
        }
        // 检测是否已被锁定
        String key = CacheConstants.USER_PASSWORD_ERROR_KEY_PREFIX + RedisUtils.formatKey(username, JakartaServletUtil
                .getClientIP(request));
        Integer currentErrorCount = ObjectUtil.defaultIfNull(RedisUtils.get(key), 0);
        CheckUtils.throwIf(currentErrorCount >= maxErrorCount, PasswordPolicyEnum.PASSWORD_ERROR_LOCK_MINUTES.getMsg()
                .formatted(this.getUnlockTime(key)));
        // 登录成功清除计数
        if (!isError) {
            RedisUtils.delete(key);
            return;
        }
        // 登录失败递增计数
        currentErrorCount++;
        int lockMinutes = optionService.getValueByCode2Int(PasswordPolicyEnum.PASSWORD_ERROR_LOCK_MINUTES.name());
        RedisUtils.set(key, currentErrorCount, Duration.ofMinutes(lockMinutes));
        CheckUtils.throwIf(currentErrorCount >= maxErrorCount, PasswordPolicyEnum.PASSWORD_ERROR_LOCK_COUNT.getMsg()
                .formatted(maxErrorCount, lockMinutes, this.getUnlockTime(key)));
    }

    private String getUnlockTime(String key) {
        long timeToLive = RedisUtils.getTimeToLive(key);
        return timeToLive > 0
                ? DateUtil.date()
                .offset(DateField.MILLISECOND, (int) timeToLive)
                .toString(DatePattern.CHINESE_DATE_TIME_FORMAT)
                : "";
    }
}
