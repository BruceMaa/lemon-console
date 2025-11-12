package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.query.MessageQuery;
import cn.onesorigin.lemon.console.system.model.req.MessageReq;
import cn.onesorigin.lemon.console.system.model.resp.MessageDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageUnreadResp;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.util.List;

/**
 * 消息 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-11 13:35
 */
public interface MessageService {
    /**
     * 新增消息
     *
     * @param req     请求参数
     * @param userIds 接收人ID列表
     */
    void add(MessageReq req, List<String> userIds);

    /**
     * 查询未读消息数量
     *
     * @param userId   用户ID
     * @param isDetail 是否查询详情
     * @return 未读消息数量
     */
    MessageUnreadResp countUnreadByUserId(Long userId, Boolean isDetail);

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页列表信息
     */
    PageResp<MessageResp> page(MessageQuery query, PageQuery pageQuery);

    /**
     * 根据消息ID查询详情
     *
     * @param messageId 消息ID
     * @return 消息详情
     */
    MessageDetailResp findDetailById(Long messageId);

    /**
     * 标记消息为已读
     *
     * @param messageIds 消息ID列表（为空则将所有消息标记为已读）
     * @param userId     用户ID
     */
    void readMessage(List<Long> messageIds, Long userId);

    /**
     * 删除消息
     *
     * @param messageIds 消息ID列表
     */
    void delete(List<Long> messageIds);
}
