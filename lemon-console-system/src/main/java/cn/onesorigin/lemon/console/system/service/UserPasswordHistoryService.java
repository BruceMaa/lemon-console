package cn.onesorigin.lemon.console.system.service;

import java.util.List;

/**
 * 用户历史密码 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-24 15:28
 */
public interface UserPasswordHistoryService {

    /**
     * 新增历史密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @param count    保留N条历史记录
     */
    void add(Long userId, String password, int count);

    /**
     * 根据用户ID删除历史密码
     *
     * @param userIds 用户ID列表
     */
    void deleteByUserIds(List<Long> userIds);

    /**
     * 判断密码是否重复使用
     *
     * @param userId   用户ID
     * @param password 密码
     * @param count    最近N次
     * @return 是否为重复使用
     */
    boolean isPasswordReused(Long userId, String password, int count);
}
