package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.convert.MessageConvert;
import cn.onesorigin.lemon.console.system.enums.MessageTypeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.mapper.MessageMapper;
import cn.onesorigin.lemon.console.system.model.entity.MessageDO;
import cn.onesorigin.lemon.console.system.model.query.MessageQuery;
import cn.onesorigin.lemon.console.system.model.req.MessageReq;
import cn.onesorigin.lemon.console.system.model.resp.MessageDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageTypeUnreadResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageUnreadResp;
import cn.onesorigin.lemon.console.system.service.MessageLogService;
import cn.onesorigin.lemon.console.system.service.MessageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.messaging.websocket.util.WebSocketUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-11 13:37
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper baseMapper;
    private final MessageLogService messageLogService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(MessageReq req, List<String> userIds) {
        var message = MessageConvert.INSTANCE.toDO(req);
        message.setScope(CollUtil.isEmpty(userIds) ? NoticeScopeEnum.ALL : NoticeScopeEnum.USER);
        message.setUsers(userIds);
        baseMapper.insert(message);

        // 发送消息给指定在线用户
        if (CollUtil.isNotEmpty(userIds)) {
            userIds.parallelStream().forEach(userId -> {
                List<String> tokenList = StpUtil.getTokenValueListByLoginId(userId);
                tokenList.parallelStream().forEach(token -> WebSocketUtils.sendMessage(token, "1"));
            });
            return;
        }
        // 发送消息给所有在线用户
        WebSocketUtils.sendMessage("1");
    }

    @Override
    public MessageUnreadResp countUnreadByUserId(Long userId, Boolean isDetail) {
        MessageUnreadResp result = new MessageUnreadResp();
        Long total = 0L;
        if (Boolean.TRUE.equals(isDetail)) {
            List<MessageTypeUnreadResp> detailList = new ArrayList<>();
            for (MessageTypeEnum messageType : MessageTypeEnum.values()) {
                MessageTypeUnreadResp resp = new MessageTypeUnreadResp();
                resp.setType(messageType);
                Long count = baseMapper.findUnreadCountByUserIdAndType(userId, messageType.getValue());
                resp.setCount(count);
                detailList.add(resp);
                total += count;
            }
            result.setDetails(detailList);
        } else {
            total = baseMapper.findUnreadCountByUserIdAndType(userId, null);
        }
        result.setTotal(total);
        return result;
    }

    @AutoOperate(type = MessageResp.class, on = "list")
    @Override
    public PageResp<MessageResp> page(MessageQuery query, PageQuery pageQuery) {
        IPage<MessageResp> page = baseMapper.findPage(new Page<>(pageQuery.getPage(), pageQuery.getSize()), query);
        return PageResp.build(page);
    }

    @Override
    public MessageDetailResp findDetailById(Long messageId) {
        return baseMapper.findDetailById(messageId);
    }

    @Override
    public void readMessage(List<Long> messageIds, Long userId) {
        // 查询当前用户的未读消息
        List<MessageDO> list = baseMapper.findUnreadListByUserId(userId);
        List<Long> unreadIds = CollUtils.mapToList(list, MessageDO::getId);
        messageLogService.addWithUserId(CollUtil.isNotEmpty(messageIds)
                ? CollUtil.intersection(unreadIds, messageIds).stream().toList()
                : unreadIds, userId);
        WebSocketUtils.sendMessage(StpUtil.getTokenValueByLoginId(userId), String.valueOf(baseMapper
                .findUnreadListByUserId(userId)
                .size()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> messageIds) {
        baseMapper.deleteByIds(messageIds);
        messageLogService.deleteByMessageIds(messageIds);
    }
}
