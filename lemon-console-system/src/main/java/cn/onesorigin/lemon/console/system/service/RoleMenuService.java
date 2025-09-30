package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.entity.RoleMenuDO;
import top.continew.starter.data.service.IService;

import java.util.List;

/**
 * 角色和菜单关联 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-30 09:12
 */
public interface RoleMenuService extends IService<RoleMenuDO> {

    /**
     * 新增
     *
     * @param menuIds 菜单 ID 列表
     * @param roleId  角色 ID
     * @return 是否新增成功（true：成功；false：无变更/失败）
     */
    boolean saveOrUpdate(List<Long> menuIds, Long roleId);

    /**
     * 根据角色 ID 列表删除
     *
     * @param roleIds 角色 ID 列表
     */
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 根据角色 ID 列表查询
     *
     * @param roleIds 角色 ID 列表
     * @return 菜单 ID 列表
     */
    List<Long> findMenuIdsByRoleIds(List<Long> roleIds);
}
