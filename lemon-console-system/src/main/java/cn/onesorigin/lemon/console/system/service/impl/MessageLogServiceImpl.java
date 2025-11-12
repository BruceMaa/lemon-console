package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.mapper.MessageLogMapper;
import cn.onesorigin.lemon.console.system.model.entity.MessageLogDO;
import cn.onesorigin.lemon.console.system.service.MessageLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.CollUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息日志 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-12 10:36
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageLogServiceImpl implements MessageLogService {

    private final MessageLogMapper baseMapper;

    @Override
    public void addWithUserId(List<Long> messageIds, Long userId) {
        if (CollUtil.isEmpty(messageIds)) {
            return;
        }
        List<MessageLogDO> list = CollUtils
                .mapToList(messageIds, messageId -> new MessageLogDO(messageId, userId, LocalDateTime.now()));
        baseMapper.insert(list);
    }

    @Override
    public void deleteByMessageIds(List<Long> messageIds) {
        if (CollUtil.isEmpty(messageIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(MessageLogDO::getMessageId, messageIds).remove();
    }
}
