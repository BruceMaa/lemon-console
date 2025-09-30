package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.entity.RoleDeptDO;
import top.continew.starter.data.service.IService;

import java.util.List;

/**
 * 角色和部门关联 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-29 17:31
 */
public interface RoleDeptService extends IService<RoleDeptDO> {

    /**
     * 根据部门ID列表删除
     *
     * @param deptIds 部门ID列表
     */
    void deleteByDeptIds(List<Long> deptIds);

    /**
     * 新增
     *
     * @param deptIds 部门ID列表
     * @param roleId  角色ID
     * @return 是否成功
     */
    boolean saveOrUpdate(List<Long> deptIds, Long roleId);

    /**
     * 根据角色ID列表删除
     *
     * @param roleIds 角色ID列表
     */
    void deleteByRoleIds(List<Long> roleIds);
}
