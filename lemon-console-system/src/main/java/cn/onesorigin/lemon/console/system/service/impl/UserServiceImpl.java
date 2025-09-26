package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.mapper.UserMapper;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.UserBasicInfoUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.UserReq;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import cn.onesorigin.lemon.console.system.service.*;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.security.crypto.util.EncryptHelper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.onesorigin.lemon.console.system.enums.PasswordPolicyEnum.*;

/**
 * 用户业务实现
 *
 * @author BruceMaa
 * @since 2025-09-17 18:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO, UserResp, UserDetailResp, UserQuery, UserReq> implements UserService {

    private final UserRoleService userRoleService;
    private final DeptService deptService;
    private final OptionService optionService;
    private final UserPasswordHistoryService userPasswordHistoryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResp<UserResp> page(UserQuery query, PageQuery pageQuery) {
        QueryWrapper<UserDO> queryWrapper = this.buildQueryWrapper(query);
        super.sort(queryWrapper, pageQuery);
        IPage<UserDetailResp> page = baseMapper.findUserPage(
                new Page<>(pageQuery.getPage(), pageQuery.getSize()),
                queryWrapper);
        PageResp<UserResp> pageResp = PageResp.build(page, super.getListClass());
        pageResp.getList().forEach(this::fill);
        return pageResp;
    }

    @Override
    public UserDO findByUsername(String username) {
        return baseMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username), false);
    }

    @ContainerMethod(namespace = ContainerConstants.USER_NICKNAME, type = MappingType.ORDER_OF_KEYS)
    @Cached(key = "#id", name = CacheConstants.USER_KEY_PREFIX, cacheType = CacheType.BOTH, syncLocal = true)
    @Override
    public String findNicknameById(Long id) {
        var user = baseMapper.lambdaQuery().select(UserDO::getNickname).eq(UserDO::getId, id).one();
        if (user != null) {
            return user.getNickname();
        }
        return "";
    }

    @CacheUpdate(key = "#id", value = "#req.nickname", name = CacheConstants.USER_KEY_PREFIX)
    @Override
    public void updateBasicInfo(UserBasicInfoUpdateReq updateReq, Long userId) {
        super.getById(userId);
        baseMapper.lambdaUpdate()
                .set(UserDO::getNickname, updateReq.getNickname())
                .set(UserDO::getGender, updateReq.getGender())
                .eq(UserDO::getId, userId)
                .update();
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword, Long userId) {
        CheckUtils.throwIfEqual(newPassword, oldPassword, "新密码不能与当前密码相同");
        UserDO user = super.getById(userId);
        String password = user.getPassword();
        if (StrUtil.isNotBlank(password)) {
            CheckUtils.throwIf(!passwordEncoder.matches(oldPassword, password), "当前密码不正确");
        }
        // 校验密码合法性
        int passwordRepetitionTimes = this.checkPassword(newPassword, user);
        // 更新密码和密码重置时间
        baseMapper.lambdaUpdate()
                .set(UserDO::getPassword, newPassword)
                .set(UserDO::getPwdResetTime, LocalDateTime.now())
                .eq(UserDO::getId, userId)
                .update();
        // 保存历史密码
        userPasswordHistoryService.add(userId, password, passwordRepetitionTimes);
        // 修改后登出
        StpUtil.logout();
    }

    @Override
    public void updatePhone(String newPhone, String oldPassword, Long userId) {
        UserDO user = super.getById(userId);
        CheckUtils.throwIf(!passwordEncoder.matches(oldPassword, user.getPassword()), "当前密码不正确");
        this.checkPhoneRepeat(newPhone, userId, "手机号已绑定其他账号，请更换其他手机号");
        CheckUtils.throwIfEqual(newPhone, user.getPhone(), "新手机号不能与当前手机号相同");
        // 更新手机号
        baseMapper.lambdaUpdate().set(UserDO::getPhone, newPhone).eq(UserDO::getId, userId).update();
    }

    @Override
    public void updateEmail(String newEmail, String oldPassword, Long userId) {
        UserDO user = super.getById(userId);
        CheckUtils.throwIf(!passwordEncoder.matches(oldPassword, user.getPassword()), "当前密码不正确");
        this.checkEmailRepeat(newEmail, userId, "邮箱已绑定其他账号，请更换其他邮箱");
        CheckUtils.throwIfEqual(newEmail, user.getEmail(), "新邮箱不能与当前邮箱相同");
        // 更新邮箱
        baseMapper.lambdaUpdate().set(UserDO::getEmail, newEmail).eq(UserDO::getId, userId).update();
    }

    @Override
    protected QueryWrapper<UserDO> buildQueryWrapper(UserQuery query) {
        String description = query.getDescription();
        DisEnableStatusEnum status = query.getStatus();
        List<LocalDateTime> createTimeList = query.getCreateTime();
        Long deptId = query.getDeptId();
        List<Long> userIdList = query.getUserIds();
        // 获取排除用户 ID 列表
        List<Long> excludeUserIdList = null;
        if (query.getRoleId() != null) {
            excludeUserIdList = userRoleService.findUserIdsByRoleId(query.getRoleId());
        }
        return new QueryWrapper<UserDO>().and(StrUtil.isNotBlank(description), q -> q.like("t1.username", description)
                        .or()
                        .like("t1.nickname", description)
                        .or()
                        .like("t1.description", description))
                .eq(status != null, "t1.status", status)
                .between(CollUtil.isNotEmpty(createTimeList), "t1.created_at", CollUtil.getFirst(createTimeList), CollUtil
                        .getLast(createTimeList))
                .and(deptId != null && !SystemConstants.SUPER_DEPT_ID.equals(deptId), q -> {
                    List<Long> deptIdList = CollUtils.mapToList(deptService.findChildren(deptId), DeptDO::getId);
                    deptIdList.add(deptId);
                    q.in("t1.dept_id", deptIdList);
                })
                .in(CollUtil.isNotEmpty(userIdList), "t1.id", userIdList)
                .notIn(CollUtil.isNotEmpty(excludeUserIdList), "t1.id", excludeUserIdList);
    }

    /**
     * 检测密码合法性
     *
     * @param password 密码
     * @param user     用户信息
     * @return 密码允许重复使用次数
     */
    private int checkPassword(String password, UserDO user) {
        Map<String, String> passwordPolicy = optionService.getByCategory(OptionCategoryEnum.PASSWORD);
        // 密码最小长度
        PASSWORD_MIN_LENGTH.validate(password, MapUtil.getInt(passwordPolicy, PASSWORD_MIN_LENGTH.name()), user);
        // 密码是否必须包含特殊字符
        PASSWORD_REQUIRE_SYMBOLS.validate(password, MapUtil.getInt(passwordPolicy, PASSWORD_REQUIRE_SYMBOLS
                .name()), user);
        // 密码是否允许包含正反序账号名
        PASSWORD_ALLOW_CONTAIN_USERNAME.validate(password, MapUtil
                .getInt(passwordPolicy, PASSWORD_ALLOW_CONTAIN_USERNAME.name()), user);
        // 密码重复使用次数
        int passwordRepetitionTimes = MapUtil.getInt(passwordPolicy, PASSWORD_REPETITION_TIMES.name());
        PASSWORD_REPETITION_TIMES.validate(password, passwordRepetitionTimes, user);
        return passwordRepetitionTimes;
    }

    /**
     * 检查手机号码是否重复
     *
     * @param phone    手机号码
     * @param id       ID
     * @param template 提示模板
     */
    private void checkPhoneRepeat(String phone, Long id, String template) {
        CheckUtils.throwIf(StrUtil.isNotBlank(phone) && baseMapper.lambdaQuery()
                .eq(UserDO::getPhone, EncryptHelper.encrypt(phone))
                .ne(ObjectUtil.isNotNull(id), UserDO::getId, id)
                .exists(), template, phone);
    }

    /**
     * 检查邮箱是否重复
     *
     * @param email    邮箱
     * @param id       ID
     * @param template 提示模板
     */
    private void checkEmailRepeat(String email, Long id, String template) {
        CheckUtils.throwIf(StrUtil.isNotBlank(email) && baseMapper.lambdaQuery()
                .eq(UserDO::getEmail, EncryptHelper.encrypt(email))
                .ne(ObjectUtil.isNotNull(id), UserDO::getId, id)
                .exists(), template, email);
    }
}
