package cn.onesorigin.lemon.console.auth.service;

/**
 * 在线用户 业务接口
 *
 * @author BruceMaa
 * @since 2025-10-31 14:58
 */
public interface OnlineUserService {
    /**
     * 踢出用户
     *
     * @param userId 用户ID
     */
    void kickOut(Long userId);
}
