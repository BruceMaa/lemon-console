package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;

import java.util.List;

/**
 * 用户角色关联 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-24 16:33
 */
public interface UserRoleService {
    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(Long userId);

    /**
     * 根据用户ID列表查询用户角色关联列表
     *
     * @param userIds 用户ID列表
     * @return 用户角色关联列表
     */
    List<UserRoleDO> findByUserIds(List<Long> userIds);

    /**
     * 根据角色ID查询用户ID列表
     *
     * @param roleId 角色ID
     * @return 用户ID列表
     */
    List<Long> findUserIdsByRoleId(Long roleId);
}
