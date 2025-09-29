package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.UserBasicInfoUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.UserReq;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import top.continew.starter.data.service.IService;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author BruceMaa
 * @since 2025-09-17 18:22
 */
public interface UserService extends BaseService<UserResp, UserDetailResp, UserQuery, UserReq>, IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserDO findByUsername(String username);

    /**
     * 根据用户ID查询用户昵称
     *
     * @param id 用户ID
     * @return 用户昵称
     */
    String findNicknameById(Long id);

    /**
     * 修改基础信息
     *
     * @param updateReq 修改的信息
     * @param userId    用户ID
     */
    void updateBasicInfo(UserBasicInfoUpdateReq updateReq, Long userId);

    /**
     * 修改密码
     *
     * @param oldPassword 当前密码
     * @param newPassword 新密码
     * @param userId      用户ID
     */
    void updatePassword(String oldPassword, String newPassword, Long userId);

    /**
     * 修改手机号
     *
     * @param newPhone    新手机号
     * @param oldPassword 当前密码
     * @param userId      用户ID
     */
    void updatePhone(String newPhone, String oldPassword, Long userId);

    /**
     * 修改邮箱地址
     *
     * @param newEmail    新邮箱地址
     * @param oldPassword 当前密码
     * @param userId      用户ID
     */
    void updateEmail(String newEmail, String oldPassword, Long userId);

    /**
     * 根据部门ID列表查询用户数量
     *
     * @param deptIds 部门ID列表
     * @return 用户数量
     */
    Long countByDeptIds(List<Long> deptIds);
}
