package cn.onesorigin.lemon.console.system.service;

import java.util.List;

/**
 * 角色和部门关联 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-29 17:31
 */
public interface RoleDeptService {

    /**
     * 根据部门ID列表删除
     *
     * @param deptIds 部门ID列表
     */
    void deleteByDeptIds(List<Long> deptIds);
}
