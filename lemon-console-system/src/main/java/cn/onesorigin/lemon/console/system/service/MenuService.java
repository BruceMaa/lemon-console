package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import top.continew.starter.data.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 菜单 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-18 16:45
 */
public interface MenuService extends BaseService<MenuResp, MenuResp, MenuQuery, MenuReq>, IService<MenuDO> {
    /**
     * 根据用户ID获取权限码集合
     *
     * @param userId 用户ID
     * @return 权限码集合
     */
    Set<String> findPermissionsByUserId(Long userId);

    /**
     * 根据角色ID获取菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<MenuResp> findByRoleId(Long roleId);
}
