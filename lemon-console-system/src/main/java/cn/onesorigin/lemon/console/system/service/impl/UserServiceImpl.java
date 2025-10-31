package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.validation.ValidationUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.EasyExcel;
import cn.onesorigin.lemon.console.auth.service.OnlineUserService;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.convert.UserConvert;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.mapper.UserMapper;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.entity.RoleDO;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.*;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserImportParseResp;
import cn.onesorigin.lemon.console.system.model.resp.UserImportResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import cn.onesorigin.lemon.console.system.service.*;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.exception.BusinessException;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.FileUploadUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.query.SortQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.security.crypto.util.EncryptHelper;
import top.continew.starter.web.model.R;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.onesorigin.lemon.console.system.enums.ImportPolicyEnum.*;
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
    private final RoleService roleService;
    private final OptionService optionService;
    private final UserPasswordHistoryService userPasswordHistoryService;
    private final OnlineUserService onlineUserService;
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
    protected void beforeCreate(UserReq req) {
        String password = SecureUtils.decryptPasswordByRsaPrivateKey(req.getPassword(), "密码解密失败", true);
        req.setPassword(password);
        this.checkUsernameRepeat(req.getUsername(), null);
        this.checkEmailRepeat(req.getEmail(), null, "邮箱为 【{}】 的用户已存在");
        this.checkPhoneRepeat(req.getPhone(), null, "手机号为 【{}】 的用户已存在");
    }

    @Override
    public void afterCreate(UserReq req, UserDO user) {
        Long userId = user.getId();
        baseMapper.lambdaUpdate().set(UserDO::getPwdResetTime, LocalDateTime.now()).eq(UserDO::getId, userId).update();
        // 保存用户和角色关联
        userRoleService.assignRolesToUser(req.getRoleIds(), userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheUpdate(key = "#id", value = "#req.nickname", name = CacheConstants.USER_KEY_PREFIX)
    @Override
    public void update(UserReq req, Long id) {
        this.checkUsernameRepeat(req.getUsername(), id);
        this.checkEmailRepeat(req.getEmail(), id, "邮箱为 【{}】 的用户已存在");
        this.checkPhoneRepeat(req.getPhone(), id, "手机号为 【{}】 的用户已存在");
        DisEnableStatusEnum newStatus = req.getStatus();
        CheckUtils.throwIf(DisEnableStatusEnum.DISABLE.equals(newStatus) && ObjectUtil.equal(id, UserContextHolder
                .getUserId()), "不允许禁用当前用户");
        UserDO oldUser = this.getById(id);
        if (Boolean.TRUE.equals(oldUser.getIsSystem())) {
            CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, newStatus, "【{}】 是系统内置用户，不允许禁用", oldUser
                    .getNickname());
            Collection<Long> disjunctionRoleIds = CollUtil.disjunction(req.getRoleIds(), userRoleService
                    .findRoleIdsByUserId(id));
            CheckUtils.throwIfNotEmpty(disjunctionRoleIds, "【{}】 是系统内置用户，不允许变更角色", oldUser.getNickname());
        }
        // 更新信息
        UserDO newUser = BeanUtil.toBean(req, UserDO.class);
        newUser.setId(id);
        baseMapper.updateById(newUser);
        // 保存用户和角色关联
        boolean isSaveUserRoleSuccess = userRoleService.assignRolesToUser(req.getRoleIds(), id);
        // 如果禁用用户，则踢出在线用户
        if (DisEnableStatusEnum.DISABLE.equals(newStatus)) {
            onlineUserService.kickOut(id);
            return;
        }
        // 如果角色有变更，则更新在线用户权限信息
        if (isSaveUserRoleSuccess) {
            this.updateContext(id);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(key = "#ids", name = CacheConstants.USER_KEY_PREFIX, multi = true)
    @Override
    public void delete(List<Long> ids) {
        CheckUtils.throwIf(CollUtil.contains(ids, UserContextHolder.getUserId()), "不允许删除当前用户");
        List<UserDO> list = baseMapper.lambdaQuery()
                .select(UserDO::getId, UserDO::getNickname, UserDO::getIsSystem)
                .in(UserDO::getId, ids)
                .list();
        List<Long> idList = CollUtils.mapToList(list, UserDO::getId);
        Collection<Long> subtractIds = CollUtil.subtract(ids, idList);
        CheckUtils.throwIfNotEmpty(subtractIds, "所选用户 【{}】 不存在", CollUtil.join(subtractIds, StringConstants.COMMA));
        Optional<UserDO> isSystemData = list.stream().filter(UserDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选用户 【{}】 是系统内置用户，不允许删除", isSystemData.orElseGet(UserDO::new)
                .getNickname());
        // 删除用户和角色关联
        userRoleService.deleteByUserIds(ids);
        // 删除历史密码
        userPasswordHistoryService.deleteByUserIds(ids);
        // 删除用户
        super.delete(ids);
        // 踢出在线用户
        ids.forEach(onlineUserService::kickOut);
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
    public Long countByDeptIds(List<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return 0L;
        }
        return baseMapper.lambdaQuery().in(UserDO::getDeptId, deptIds).count();
    }

    @Override
    protected <E> List<E> list(UserQuery query, SortQuery sortQuery, Class<E> targetClass) {
        QueryWrapper<UserDO> queryWrapper = this.buildQueryWrapper(query);
        // 设置排序
        super.sort(queryWrapper, sortQuery);
        List<UserDetailResp> entityList = baseMapper.findUserList(queryWrapper);
        return (List<E>) entityList;
    }

    @Override
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        try {
            FileUploadUtils.download(response, ResourceUtil.getStream("templates/import/user.xlsx"), "用户导入模板.xlsx");
        } catch (Exception e) {
            log.error("下载用户导入模板失败：{}", e.getMessage(), e);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JSONUtil.toJsonStr(R.fail("500", "下载用户导入模板失败")));
        }
    }

    @Override
    public UserImportParseResp parseImport(MultipartFile file) {
        UserImportParseResp userImportResp = new UserImportParseResp();
        List<UserImportRowReq> importRowList;
        // 读取表格数据
        try {
            importRowList = EasyExcel.read(file.getInputStream())
                    .head(UserImportRowReq.class)
                    .sheet()
                    .headRowNumber(1)
                    .doReadSync();
        } catch (Exception e) {
            log.error("用户导入数据文件解析异常：{}", e.getMessage(), e);
            throw new BusinessException("数据文件解析异常");
        }
        // 总计行数
        userImportResp.setTotalRows(importRowList.size());
        CheckUtils.throwIfEmpty(importRowList, "数据文件格式不正确");
        // 有效行数：过滤无效数据
        List<UserImportRowReq> validRowList = this.filterImportData(importRowList);
        userImportResp.setValidRows(validRowList.size());
        CheckUtils.throwIfEmpty(validRowList, "数据文件格式不正确");

        // 检测表格内数据是否合法
        Set<String> seenEmails = new HashSet<>();
        boolean hasDuplicateEmail = validRowList.stream()
                .map(UserImportRowReq::getEmail)
                .anyMatch(email -> email != null && !seenEmails.add(email));
        CheckUtils.throwIf(hasDuplicateEmail, "存在重复邮箱，请检测数据");
        Set<String> seenPhones = new HashSet<>();
        boolean hasDuplicatePhone = validRowList.stream()
                .map(UserImportRowReq::getPhone)
                .anyMatch(phone -> phone != null && !seenPhones.add(phone));
        CheckUtils.throwIf(hasDuplicatePhone, "存在重复手机，请检测数据");

        // 校验是否存在无效角色
        List<String> roleNames = validRowList.stream().map(UserImportRowReq::getRoleName).distinct().toList();
        int existRoleCount = roleService.countByNames(roleNames);
        CheckUtils.throwIf(existRoleCount < roleNames.size(), "存在无效角色，请检查数据");
        // 校验是否存在无效部门（支持多级部门解析）
        Set<String> deptNames = CollUtils.mapToSet(validRowList, UserImportRowReq::getDeptName);
        int existDeptCount = this.countValidMultiLevelDepts(deptNames);
        CheckUtils.throwIf(existDeptCount < deptNames.size(), "存在无效部门，请检查部门名称或部门层级是否正确");

        // 查询重复用户
        userImportResp
                .setDuplicateUserRows(this.countExistByField(validRowList, UserImportRowReq::getUsername, UserDO::getUsername));
        // 查询重复邮箱
        userImportResp.setDuplicateEmailRows(this.countExistByField(validRowList, row -> EncryptHelper.encrypt(row
                .getEmail()), UserDO::getEmail));
        // 查询重复手机
        userImportResp.setDuplicatePhoneRows(this.countExistByField(validRowList, row -> EncryptHelper.encrypt(row
                .getPhone()), UserDO::getPhone));

        // 设置导入会话并缓存数据，有效期10分钟
        String importKey = IdUtil.fastSimpleUUID();
        RedisUtils.set(CacheConstants.DATA_IMPORT_KEY + importKey, JSONUtil.toJsonStr(validRowList), Duration
                .ofMinutes(10));
        userImportResp.setImportKey(importKey);
        return userImportResp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserImportResp importUser(UserImportReq req) {
        // 校验导入会话是否过期
        List<UserImportRowReq> importUserList;
        try {
            String data = RedisUtils.get(CacheConstants.DATA_IMPORT_KEY + req.getImportKey());
            importUserList = JSONUtil.toList(data, UserImportRowReq.class);
            CheckUtils.throwIf(CollUtil.isEmpty(importUserList), "导入会话已过期，请重新上传");
        } catch (Exception e) {
            log.error("导入异常：", e);
            throw new BusinessException("导入已过期，请重新上传");
        }
        // 已存在数据查询
        List<String> existEmails = this.listExistByField(importUserList, row -> EncryptHelper.encrypt(row
                .getEmail()), UserDO::getEmail);
        List<String> existPhones = this.listExistByField(importUserList, row -> EncryptHelper.encrypt(row
                .getPhone()), UserDO::getPhone);
        List<UserDO> existUserList = this.listByUsernames(CollUtils
                .mapToList(importUserList, UserImportRowReq::getUsername));
        List<String> existUsernames = CollUtils.mapToList(existUserList, UserDO::getUsername);
        CheckUtils
                .throwIf(this.isExitImportUser(req, importUserList, existUsernames, existEmails, existPhones), "数据不符合导入策略，已退出导入");

        // 基础数据准备
        Map<String, Long> userMap = existUserList.stream()
                .collect(Collectors.toMap(UserDO::getUsername, UserDO::getId));
        List<RoleDO> roleList = roleService.findByNames(importUserList.stream()
                .map(UserImportRowReq::getRoleName)
                .distinct()
                .toList());
        Map<String, Long> roleMap = roleList.stream().collect(Collectors.toMap(RoleDO::getName, RoleDO::getId));
        // 获取多级部门映射
        Map<String, Long> deptMap = this.buildMultiLevelDeptMapping(importUserList.stream()
                .map(UserImportRowReq::getDeptName)
                .distinct()
                .toList());

        // 批量操作数据库集合
        List<UserDO> insertList = new ArrayList<>();
        List<UserDO> updateList = new ArrayList<>();
        List<UserRoleDO> userRoleDOList = new ArrayList<>();
        for (UserImportRowReq row : importUserList) {
            if (this.isSkipUserImport(req, row, existUsernames, existPhones, existEmails)) {
                // 按规则跳过该行
                continue;
            }
            UserDO userDO = UserConvert.INSTANCE.toDO(row);
            userDO.setStatus(req.getDefaultStatus());
            userDO.setPwdResetTime(LocalDateTime.now());
            userDO.setGender(EnumUtil.getBy(GenderEnum::getDescription, row.getGender(), GenderEnum.UNKNOWN));
            userDO.setDeptId(deptMap.get(row.getDeptName()));
            // 修改 or 新增
            if (UPDATE.validate(req.getDuplicateUser(), row.getUsername(), existUsernames)) {
                userDO.setId(userMap.get(row.getUsername()));
                updateList.add(userDO);
            } else {
                userDO.setId(IdWorker.getId());
                userDO.setIsSystem(false);
                insertList.add(userDO);
            }
            userRoleDOList.add(new UserRoleDO(userDO.getId(), roleMap.get(row.getRoleName())));
        }
        this.doImportUser(insertList, updateList, userRoleDOList);
        RedisUtils.delete(CacheConstants.DATA_IMPORT_KEY + req.getImportKey());
        return new UserImportResp(insertList.size() + updateList.size(), insertList.size(), updateList.size());
    }

    @Override
    public void resetPassword(UserPasswordResetReq req, Long id) {
        this.getById(id);
        baseMapper.lambdaUpdate()
                .set(UserDO::getPassword, req.getNewPassword())
                .set(UserDO::getPwdResetTime, LocalDateTime.now())
                .eq(UserDO::getId, id)
                .update();
    }

    @Override
    public void updateRole(UserRoleUpdateReq updateReq, Long id) {
        this.getById(id);
        List<Long> roleIds = updateReq.getRoleIds();
        // 保存用户和角色关联
        userRoleService.assignRolesToUser(roleIds, id);
        // 更新用户上下文
        this.updateContext(id);
    }

    @Override
    protected QueryWrapper<UserDO> buildQueryWrapper(UserQuery query) {
        String description = query.getDescription();
        DisEnableStatusEnum status = query.getStatus();
        List<LocalDateTime> createTimeList = query.getCreatedAt();
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
     * 检查用户名是否重复
     *
     * @param username 用户名
     * @param id       ID
     */
    private void checkUsernameRepeat(String username, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(UserDO::getUsername, username)
                .ne(id != null, UserDO::getId, id)
                .exists(), "用户名为 【{}】 的用户已存在", username);
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

    /**
     * 更新用户上下文信息
     *
     * @param id ID
     */
    private void updateContext(Long id) {
        UserContext userContext = UserContextHolder.getContext(id);
        if (userContext != null) {
            userContext.setRoles(roleService.findRoleContextsByUserId(id));
            userContext.setPermissionCodes(roleService.findPermissionsByUserId(id));
            UserContextHolder.setContext(userContext);
        }
    }

    /**
     * 根据 ID 获取用户信息（数据权限）
     *
     * @param id ID
     * @return 用户信息
     */
    private UserDO getById(Long id) {
        UserDO user = super.getById(id);
        CheckUtils.throwIfNull(user, "用户不存在");
        return user;
    }

    /**
     * 导入用户
     *
     * @param insertList     新增用户
     * @param updateList     修改用户
     * @param userRoleDOList 用户角色关联
     */
    private void doImportUser(List<UserDO> insertList, List<UserDO> updateList, List<UserRoleDO> userRoleDOList) {
        if (CollUtil.isNotEmpty(insertList)) {
            baseMapper.insert(insertList);
        }
        if (CollUtil.isNotEmpty(updateList)) {
            baseMapper.updateBatchById(updateList);
            userRoleService.deleteByUserIds(CollUtils.mapToList(updateList, UserDO::getId));
        }
        if (CollUtil.isNotEmpty(userRoleDOList)) {
            userRoleService.saveBatch(userRoleDOList);
        }
    }

    /**
     * 判断是否跳过导入
     *
     * @param req            导入参数
     * @param row            导入数据
     * @param existUsernames 导入数据中已存在的用户名
     * @param existEmails    导入数据中已存在的邮箱
     * @param existPhones    导入数据中已存在的手机号
     * @return 是否跳过
     */
    private boolean isSkipUserImport(UserImportReq req,
                                     UserImportRowReq row,
                                     List<String> existUsernames,
                                     List<String> existPhones,
                                     List<String> existEmails) {
        return SKIP.validate(req.getDuplicateUser(), row.getUsername(), existUsernames) || SKIP.validate(req
                .getDuplicateEmail(), row.getEmail(), existEmails) || SKIP.validate(req.getDuplicatePhone(), row
                .getPhone(), existPhones);
    }

    /**
     * 构建多级部门映射关系
     * <p>
     * 将部门名称列表转换为部门名称到ID的映射，支持多级部门路径解析
     * </p>
     *
     * @param deptNames 部门名称列表
     * @return 部门名称到ID的映射
     */
    private Map<String, Long> buildMultiLevelDeptMapping(List<String> deptNames) {
        CheckUtils.throwIfEmpty(deptNames, "部门名称列表不能为空");

        Map<String, Long> deptMap = new HashMap<>();
        for (String deptName : deptNames) {
            DeptDO dept = findDeptByHierarchicalPath(deptName);
            CheckUtils.throwIfNull(dept, "部门 [{}] 不存在或存在歧义", deptName);
            deptMap.put(deptName, dept.getId());
        }
        return deptMap;
    }

    /**
     * 判断是否退出导入
     *
     * @param req            导入参数
     * @param list           导入数据
     * @param existUsernames 导入数据中已存在的用户名
     * @param existEmails    导入数据中已存在的邮箱
     * @param existPhones    导入数据中已存在的手机号
     * @return 是否退出
     */
    private boolean isExitImportUser(UserImportReq req,
                                     List<UserImportRowReq> list,
                                     List<String> existUsernames,
                                     List<String> existEmails,
                                     List<String> existPhones) {
        return list.stream()
                .anyMatch(row -> EXIT.validate(req.getDuplicateUser(), row.getUsername(), existUsernames) || EXIT
                        .validate(req.getDuplicateEmail(), row.getEmail(), existEmails) || EXIT.validate(req
                        .getDuplicatePhone(), row.getPhone(), existPhones));
    }

    /**
     * 根据用户名获取用户列表
     *
     * @param usernames 用户名列表
     * @return 用户列表
     */
    private List<UserDO> listByUsernames(List<String> usernames) {
        return this.list(Wrappers.<UserDO>lambdaQuery()
                .in(UserDO::getUsername, usernames)
                .select(UserDO::getId, UserDO::getUsername));
    }

    /**
     * 按指定数据集获取数据库已存在的数量
     *
     * @param userRowList 导入的数据源
     * @param rowField    导入数据的字段
     * @param dbField     对比数据库的字段
     * @return 存在的数量
     */
    private int countExistByField(List<UserImportRowReq> userRowList,
                                  Function<UserImportRowReq, String> rowField,
                                  SFunction<UserDO, ?> dbField) {
        var fieldValues = CollUtils.mapToList(userRowList, rowField);
        if (fieldValues.isEmpty()) {
            return 0;
        }
        return Math.toIntExact(baseMapper.lambdaQuery().in(dbField, fieldValues).count());
    }

    /**
     * 按指定数据集获取数据库已存在内容
     *
     * @param userRowList 导入的数据源
     * @param rowField    导入数据的字段
     * @param dbField     对比数据库的字段
     * @return 存在的内容
     */
    private List<String> listExistByField(List<UserImportRowReq> userRowList,
                                          Function<UserImportRowReq, String> rowField,
                                          SFunction<UserDO, String> dbField) {
        List<String> fieldValues = CollUtils.mapToList(userRowList, rowField);
        if (fieldValues.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserDO> userList = baseMapper.lambdaQuery().select(dbField).in(dbField, fieldValues).list();
        return CollUtils.mapToList(userList, dbField);
    }

    /**
     * 过滤无效的导入用户数据（批量导入不严格校验数据）
     *
     * @param importRowList 导入数据
     */
    private List<UserImportRowReq> filterImportData(List<UserImportRowReq> importRowList) {
        // 校验过滤
        List<UserImportRowReq> list = importRowList.stream()
                .filter(row -> ValidationUtil.validate(row).isEmpty())
                .toList();
        // 用户名去重
        return list.stream()
                .collect(Collectors.toMap(UserImportRowReq::getUsername, user -> user, (existing, replacement) -> existing))
                .values()
                .stream()
                .toList();
    }

    /**
     * 统计有效的多级部门数量
     * <p>
     * 支持多级部门路径解析，使用冒号(:)作为层级分隔符
     * 例如：公司A:研发部:前端组 或 研发部
     * </p>
     *
     * @param deptNames 部门名称集合
     * @return 有效部门数量
     */
    private int countValidMultiLevelDepts(Set<String> deptNames) {
        CheckUtils.throwIfEmpty(deptNames, "部门名称集合不能为空");

        int validCount = 0;
        List<String> invalidDepts = new ArrayList<>();

        for (String deptName : deptNames) {
            try {
                findDeptByHierarchicalPath(deptName);
                validCount++;
            } catch (Exception e) {
                invalidDepts.add(deptName);
            }
        }

        CheckUtils.throwIf(CollUtil.isNotEmpty(invalidDepts), "以下部门无效或存在歧义：{}", String.join(", ", invalidDepts));

        return validCount;
    }

    /**
     * 根据层级路径查找部门
     * <p>
     * 支持两种格式：
     * <ul>
     * <li>多级部门：公司A/研发部/前端组</li>
     * <li>单级部门：研发部</li>
     * </ul>
     * 使用左斜杠/作为层级分隔符，会逐级查找对应的部门
     * </p>
     *
     * @param deptPath 部门路径
     * @return 部门信息，未找到时返回null
     */
    private DeptDO findDeptByHierarchicalPath(String deptPath) {
        CheckUtils.throwIfBlank(deptPath, "部门路径不能为空");
        return deptPath.contains(StringConstants.SLASH)
                ? findMultiLevelDept(deptPath)
                : findSingleLevelDept(deptPath.trim());
    }

    /**
     * 查找多级部门
     * <p>
     * 从根部门开始逐级查找，确保部门层级关系正确
     * </p>
     *
     * @param deptPath 多级部门路径
     * @return 部门信息，未找到时返回null
     */
    private DeptDO findMultiLevelDept(String deptPath) {
        String[] pathParts = deptPath.split(StringConstants.SLASH);
        CheckUtils.throwIf(pathParts.length == 0, "部门路径格式错误：{}", deptPath);

        // 从根部门开始逐级查找
        DeptDO currentDept = null;
        Long parentId = 0L; // 根部门的parentId为null

        for (String part : pathParts) {
            String trimmedPart = part.trim();
            CheckUtils.throwIfBlank(trimmedPart, "部门路径包含空名称：{}", deptPath);

            // 查找当前层级下指定名称的部门
            currentDept = deptService.lambdaQuery()
                    .eq(DeptDO::getName, trimmedPart)
                    .eq(DeptDO::getParentId, parentId)
                    .one();

            CheckUtils.throwIfNull(currentDept, "找不到部门 【{}】 在路径 【{}】 中", trimmedPart, deptPath);
            parentId = currentDept.getId(); // 更新父级ID为当前部门ID
        }

        return currentDept;
    }

    /**
     * 查找单级部门
     * <p>
     * 当只提供部门名称时，检查是否存在多个同名部门
     * 如果存在多个同名部门，则要求用户提供完整的层级路径
     * </p>
     *
     * @param deptName 部门名称
     * @return 部门信息，未找到或存在歧义时返回null
     */
    private DeptDO findSingleLevelDept(String deptName) {
        // 查找所有同名部门
        List<DeptDO> deptList = deptService.lambdaQuery().eq(DeptDO::getName, deptName).list();

        CheckUtils.throwIfEmpty(deptList, "部门 【{}】 不存在", deptName);
        CheckUtils.throwIf(deptList.size() > 1, "存在多个同名部门 【{}】，请使用完整层级路径，如：公司名:{}", deptName, deptName);

        return deptList.get(0);
    }
}
