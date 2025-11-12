package cn.onesorigin.lemon.console.system.service;

import java.util.List;

/**
 * 消息日志 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-12 10:36
 */
public interface MessageLogService {

    /**
     * 添加消息日志
     *
     * @param messageIds 消息ID列表
     * @param userId     用户ID
     */
    void addWithUserId(List<Long> messageIds, Long userId);

    /**
     * 根据消息ID列表删除消息日志
     *
     * @param messageIds 消息ID列表
     */
    void deleteByMessageIds(List<Long> messageIds);
}
