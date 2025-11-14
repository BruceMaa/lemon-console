package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.common.context.RoleContext;
import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import cn.onesorigin.lemon.console.common.enums.RoleCodeEnum;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.mapper.RoleMapper;
import cn.onesorigin.lemon.console.system.model.entity.RoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleQuery;
import cn.onesorigin.lemon.console.system.model.req.RolePermissionUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.RoleReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleResp;
import cn.onesorigin.lemon.console.system.service.*;
import com.alicp.jetcache.anno.CacheInvalidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.validation.CheckUtils;

import java.util.*;

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
    private final RoleMenuService roleMenuService;
    private final RoleDeptService roleDeptService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long create(RoleReq req) {
        this.checkNameRepeat(req.getName(), null);
        String code = req.getCode();
        this.checkCodeRepeat(code);
        // 新增信息
        Long roleId = super.create(req);
        // 保存角色和部门关联
        roleDeptService.saveOrUpdate(req.getDeptIds(), roleId);
        return roleId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(RoleReq req, Long id) {
        this.checkNameRepeat(req.getName(), id);
        RoleDO oldRole = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldRole.getCode(), "角色编码不允许修改", oldRole.getName());
        DataScopeEnum oldDataScope = oldRole.getDataScope();
        if (Objects.equals(Boolean.TRUE, oldRole.getIsSystem())) {
            CheckUtils.throwIfNotEqual(req.getDataScope(), oldDataScope, "【{}】 是系统内置角色，不允许修改角色数据权限", oldRole.getName());
        }
        // 更新信息
        super.update(req, id);
        if (RoleCodeEnum.isSuperRoleCode(req.getCode())) {
            return;
        }
        // 保存角色和部门关联
        boolean isSaveDeptSuccess = roleDeptService.saveOrUpdate(req.getDeptIds(), id);
        // 如果数据权限有变更，则更新在线用户权限信息
        if (isSaveDeptSuccess || req.getDataScope() != oldDataScope) {
            this.updateUserContext(id);
        }
    }

    @Override
    protected void fill(Object obj) {
        super.fill(obj);
        if (obj instanceof RoleDetailResp detail) {
            Long roleId = detail.getId();
            List<MenuResp> list = menuService.findByRoleId(roleId);
            List<Long> menuIds = CollUtils.mapToList(list, MenuResp::getId);
            detail.setMenuIds(menuIds);
        }
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<RoleDO> list = baseMapper.lambdaQuery()
                .select(RoleDO::getName, RoleDO::getIsSystem)
                .in(RoleDO::getId, ids)
                .list();
        Optional<RoleDO> isSystemData = list.stream().filter(RoleDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选角色 【{}】 是系统内置角色，不允许删除", isSystemData.orElseGet(RoleDO::new)
                .getName());
        CheckUtils.throwIf(userRoleService.isRoleIdExists(ids), "所选角色存在用户关联，请解除关联后重试");
        // 删除角色和菜单关联
        roleMenuService.deleteByRoleIds(ids);
        // 删除角色和部门关联
        roleDeptService.deleteByRoleIds(ids);
    }

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
            return new ArrayList<>(0);
        }
        return baseMapper.lambdaQuery().in(RoleDO::getId, ids).list();
    }

    @CacheInvalidate(key = "#id", name = CacheConstants.ROLE_MENU_KEY_PREFIX)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePermission(Long id, RolePermissionUpdateReq req) {
        RoleDO role = super.getById(id);
        CheckUtils.throwIf(Objects.equals(Boolean.TRUE, role.getIsSystem()), "【{}】 是系统内置角色，不允许修改角色功能权限", role.getName());
        // 保存角色和菜单关联
        boolean isSaveMenuSuccess = roleMenuService.saveOrUpdate(req.getMenuIds(), id);
        // 如果功能权限有变更，则更新在线用户权限信息
        if (isSaveMenuSuccess) {
            this.updateUserContext(id);
        }
        baseMapper.lambdaUpdate()
                .set(RoleDO::getMenuCheckStrictly, req.getMenuCheckStrictly())
                .eq(RoleDO::getId, id)
                .update();
    }

    @Override
    public void updateUserContext(Long roleId) {
        List<Long> userIdList = userRoleService.findUserIdsByRoleId(roleId);
        userIdList.forEach(userId -> {
            UserContext userContext = UserContextHolder.getContext(userId);
            if (userContext != null) {
                userContext.setRoles(this.findRoleContextsByUserId(userId));
                userContext.setPermissionCodes(this.findPermissionsByUserId(userId));
                UserContextHolder.setContext(userContext);
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignToUsers(Long id, List<Long> userIds) {
        RoleDO role = super.getById(id);
        CheckUtils.throwIf(Objects.equals(Boolean.TRUE, role.getIsSystem()), "【{}】 是系统内置角色，不允许分配角色给其他用户", role.getName());
        // 保存用户和角色关联
        userRoleService.assignRoleToUsers(id, userIds);
        // 更新用户上下文
        this.updateUserContext(id);
    }

    @Override
    public int countByNames(List<String> roleNames) {
        if (CollUtil.isEmpty(roleNames)) {
            return 0;
        }
        return Convert.toInt(baseMapper.lambdaQuery().in(RoleDO::getName, roleNames).count(), 0);
    }

    @Override
    public List<RoleDO> findByNames(List<String> list) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>(0);
        }
        return baseMapper.lambdaQuery().in(RoleDO::getName, list).list();
    }

    /**
     * 检查名称是否重复
     *
     * @param name 名称
     * @param id   ID
     */
    private void checkNameRepeat(String name, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(RoleDO::getName, name)
                .ne(id != null, RoleDO::getId, id)
                .exists(), "名称为 【{}】 的角色已存在", name);
    }

    /**
     * 检查编码是否重复
     *
     * @param code 编码
     */
    private void checkCodeRepeat(String code) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(RoleDO::getCode, code)
                .exists(), "编码为 【{}】 的角色已存在", code);
    }
}
