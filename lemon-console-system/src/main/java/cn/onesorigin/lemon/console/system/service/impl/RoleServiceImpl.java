package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.common.context.RoleContext;
import cn.onesorigin.lemon.console.common.enums.RoleCodeEnum;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.mapper.RoleMapper;
import cn.onesorigin.lemon.console.system.model.entity.RoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleQuery;
import cn.onesorigin.lemon.console.system.model.req.RoleReq;
import cn.onesorigin.lemon.console.system.model.resp.RoleDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import cn.onesorigin.lemon.console.system.service.RoleService;
import cn.onesorigin.lemon.console.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.CollUtils;

import java.util.List;
import java.util.Set;

/**
 * 角色 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-19 13:23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleDO, RoleResp, RoleDetailResp, RoleQuery, RoleReq> implements RoleService {

    private final MenuService menuService;
    private final UserRoleService userRoleService;

    @Override
    public Set<String> findPermissionsByUserId(Long userId) {
        var roleCodeSet = this.findRoleCodesByUserId(userId);
        // 超级管理员赋予全部权限
        if (roleCodeSet.contains(RoleCodeEnum.SUPER_ADMIN.getValue())) {
            return CollUtil.newHashSet(SystemConstants.ALL_PERMISSION);
        }
        return menuService.findPermissionsByUserId(userId);
    }

    @Override
    public Set<String> findRoleCodesByUserId(Long userId) {
        var roleIds = userRoleService.findRoleIdsByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return Set.of();
        }
        var roles = baseMapper.lambdaQuery().select(RoleDO::getCode).in(RoleDO::getId, roleIds).list();
        return CollUtils.mapToSet(roles, RoleDO::getCode);
    }

    @Override
    public Set<RoleContext> findRoleContextsByUserId(Long userId) {
        var roleIds = userRoleService.findRoleIdsByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return Set.of();
        }
        var roles = baseMapper.lambdaQuery()
                .select(RoleDO::getId, RoleDO::getCode, RoleDO::getDataScope)
                .in(RoleDO::getId, roleIds)
                .list();
        return CollUtils.mapToSet(roles, role -> new RoleContext(role.getId(), role.getCode(), role.getDataScope()));
    }

    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_NAME_LIST, resultType = RoleDO.class)
    @Override
    public List<RoleDO> findRolesByUserIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return List.of();
        }
        return baseMapper.lambdaQuery().in(RoleDO::getId, ids).list();
    }
}
