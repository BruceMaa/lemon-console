package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleUserQuery;
import cn.onesorigin.lemon.console.system.model.resp.RoleUserResp;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

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

    /**
     * 分页查询角色关联的用户列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页查询结果
     */
    PageResp<RoleUserResp> pageUser(RoleUserQuery query, PageQuery pageQuery);

    /**
     * 删除用户角色关联
     *
     * @param ids 用户角色关联ID列表
     */
    void deleteByIds(List<Long> ids);

    /**
     * 删除用户角色关联
     *
     * @param userIds 用户ID列表
     */
    void deleteByUserIds(List<Long> userIds);

    /**
     * 批量分配角色给用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID列表
     */
    void assignRoleToUsers(Long roleId, List<Long> userIds);

    /**
     * 判断角色ID列表是否有关联用户
     *
     * @param roleIds 角色ID列表
     * @return 角色是否有关联用户
     */
    boolean isRoleIdExists(List<Long> roleIds);

    /**
     * 批量分配角色给指定用户
     *
     * @param roleIds 角色ID列表
     * @param userId  用户ID
     * @return 是否分配成功
     */
    boolean assignRolesToUser(List<Long> roleIds, Long userId);

    /**
     * 批量保存用户角色关联
     *
     * @param userRoleDOList 用户角色关联列表
     */
    void saveBatch(List<UserRoleDO> userRoleDOList);
}
