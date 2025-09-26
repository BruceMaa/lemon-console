package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.common.context.RoleContext;
import cn.onesorigin.lemon.console.system.model.entity.RoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleQuery;
import cn.onesorigin.lemon.console.system.model.req.RoleReq;
import cn.onesorigin.lemon.console.system.model.resp.RoleDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleResp;
import top.continew.starter.data.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-19 13:22
 */
public interface RoleService extends BaseService<RoleResp, RoleDetailResp, RoleQuery, RoleReq>, IService<RoleDO> {
    /**
     * 根据用户ID查询所有权限码
     *
     * @param userId 用户ID
     * @return 权限码集合
     */
    Set<String> findPermissionsByUserId(Long userId);

    /**
     * 根据用户ID查询所有角色编码
     *
     * @param userId 用户ID
     * @return 角色编码集合
     */
    Set<String> findRoleCodesByUserId(Long userId);

    /**
     * 根据用户ID查询所有角色上下文
     *
     * @param userId 用户ID
     * @return 角色上下文集合
     */
    Set<RoleContext> findRoleContextsByUserId(Long userId);

    /**
     * 根据用户ID集合查询所有角色
     *
     * @param ids 用户ID集合
     * @return 角色集合
     */
    List<RoleDO> findRolesByUserIds(List<Long> ids);
}
