package cn.onesorigin.lemon.console.common.api.system;

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
    String getNicknameById(Long id);
}
