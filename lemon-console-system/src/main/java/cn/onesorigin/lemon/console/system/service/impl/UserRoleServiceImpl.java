package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.mapper.UserRoleMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleUserQuery;
import cn.onesorigin.lemon.console.system.model.resp.RoleUserResp;
import cn.onesorigin.lemon.console.system.service.RoleService;
import cn.onesorigin.lemon.console.system.service.UserRoleService;
import cn.onesorigin.lemon.console.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.data.util.QueryWrapperHelper;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户角色关联 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-24 16:35
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper baseMapper;
    @Lazy
    @Resource
    private UserService userService;
    @Lazy
    @Resource
    private RoleService roleService;

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return baseMapper.lambdaQuery()
                .select(UserRoleDO::getRoleId)
                .eq(UserRoleDO::getUserId, userId)
                .list()
                .stream()
                .map(UserRoleDO::getRoleId)
                .toList();
    }

    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_ID_LIST, resultKey = "userId", resultType = UserRoleDO.class, type = MappingType.ONE_TO_MANY)
    @Override
    public List<UserRoleDO> findByUserIds(List<Long> userIds) {
        return baseMapper.lambdaQuery()
                .in(UserRoleDO::getUserId, userIds)
                .list();
    }

    @Override
    public List<Long> findUserIdsByRoleId(Long roleId) {
        return baseMapper.lambdaQuery()
                .select(UserRoleDO::getUserId)
                .eq(UserRoleDO::getRoleId, roleId)
                .list()
                .stream()
                .map(UserRoleDO::getUserId)
                .toList();
    }

    @AutoOperate(type = RoleUserResp.class, on = "list")
    @Override
    public PageResp<RoleUserResp> pageUser(RoleUserQuery query, PageQuery pageQuery) {
        String description = query.getDescription();
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<UserRoleDO>().eq("t1.role_id", query.getRoleId())
                .and(StrUtil.isNotBlank(description), q -> q.like("t2.username", description)
                        .or()
                        .like("t2.nickname", description)
                        .or()
                        .like("t2.description", description));
        QueryWrapperHelper.sort(queryWrapper, pageQuery.getSort());
        IPage<RoleUserResp> page = baseMapper.selectUserPage(new Page<>(pageQuery.getPage(), pageQuery
                .getSize()), queryWrapper);
        return PageResp.build(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Long> ids) {
        baseMapper.deleteByIds(ids);
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(UserRoleDO::getUserId, userIds).remove();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoleToUsers(Long roleId, List<Long> userIds) {
        List<UserRoleDO> userRoleList = CollUtils.mapToList(userIds, userId -> new UserRoleDO(userId, roleId));
        baseMapper.insertBatch(userRoleList);
    }

    @Override
    public boolean isRoleIdExists(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        return baseMapper.lambdaQuery().in(UserRoleDO::getRoleId, roleIds).exists();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean assignRolesToUser(List<Long> roleIds, Long userId) {
        UserDO userDO = userService.getById(userId);
        if (Objects.equals(Boolean.TRUE, userDO.getIsSystem())) {
            Collection<Long> disjunctionRoleIds = CollUtil.disjunction(roleIds, this.findRoleIdsByUserId(userId));
            CheckUtils.throwIfNotEmpty(disjunctionRoleIds, "【{}】 是系统内置用户，不允许变更角色", userDO.getNickname());
        }
        // 超级管理员和租户管理员角色不允许分配
        CheckUtils.throwIf(roleIds.contains(SystemConstants.SUPER_ADMIN_ROLE_ID), "不允许分配超级管理员角色");
        // 检查是否有变更
        List<Long> oldRoleIdList = baseMapper.lambdaQuery()
                .select(UserRoleDO::getRoleId)
                .eq(UserRoleDO::getUserId, userId)
                .list()
                .stream()
                .map(UserRoleDO::getRoleId)
                .toList();
        if (CollUtil.isEmpty(CollUtil.disjunction(roleIds, oldRoleIdList))) {
            return false;
        }
        // 删除原有关联
        baseMapper.lambdaUpdate().eq(UserRoleDO::getUserId, userId).remove();
        // 保存最新关联
        List<UserRoleDO> userRoleList = CollUtils.mapToList(roleIds, roleId -> new UserRoleDO(userId, roleId));
        return baseMapper.insertBatch(userRoleList);
    }

    @Override
    public void saveBatch(List<UserRoleDO> userRoleDOList) {
        baseMapper.insertBatch(userRoleDOList);
    }
}
