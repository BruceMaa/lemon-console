package cn.onesorigin.lemon.console.common.api.system;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;

/**
 * 用户业务 API
 *
 * @author BruceMaa
 * @since 2025-09-02 13:58
 */
public interface UserApi {

    /**
     * 根据用户ID获取用户昵称
     *
     * @param id 用户ID
     * @return 用户昵称
     */
    @ContainerMethod(namespace = ContainerConstants.USER_NICKNAME, type = MappingType.ORDER_OF_KEYS)
    String getNicknameById(Long id);

    /**
     * 重置密码
     *
     * @param newPassword 新密码
     * @param id          用户ID
     */
    void resetPassword(String newPassword, Long id);
}
