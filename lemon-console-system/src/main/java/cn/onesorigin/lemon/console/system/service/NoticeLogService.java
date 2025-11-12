package cn.onesorigin.lemon.console.system.service;

import java.util.List;

/**
 * 公告日志 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-11 11:03
 */
public interface NoticeLogService {

    /**
     * 根据公告ID列表删除公告日志
     *
     * @param noticeIds 公告ID列表
     */
    void deleteByNoticeIds(List<Long> noticeIds);

    /**
     * 新增
     *
     * @param userIds  用户 ID 列表
     * @param noticeId 公告 ID
     * @return 是否新增成功（true：成功；false：无变更/失败）
     */
    boolean add(List<Long> userIds, Long noticeId);
}
