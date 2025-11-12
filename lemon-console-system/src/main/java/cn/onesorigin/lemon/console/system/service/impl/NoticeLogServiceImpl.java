package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.mapper.NoticeLogMapper;
import cn.onesorigin.lemon.console.system.model.entity.NoticeLogDO;
import cn.onesorigin.lemon.console.system.service.NoticeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 公告日志 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-11 11:12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeLogServiceImpl implements NoticeLogService {

    private final NoticeLogMapper baseMapper;

    @Override
    public void deleteByNoticeIds(List<Long> noticeIds) {
        if (CollUtil.isEmpty(noticeIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(NoticeLogDO::getNoticeId, noticeIds).remove();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(List<Long> userIds, Long noticeId) {
        // 检查是否有变更
        List<Long> oldUserIdList = baseMapper.lambdaQuery()
                .select(NoticeLogDO::getUserId)
                .eq(NoticeLogDO::getNoticeId, noticeId)
                .list()
                .stream()
                .map(NoticeLogDO::getUserId)
                .toList();
        Collection<Long> subtract = CollUtil.subtract(userIds, oldUserIdList);
        if (CollUtil.isEmpty(subtract)) {
            return false;
        }
        // 新增没有关联的
        LocalDateTime now = LocalDateTime.now();
        List<NoticeLogDO> list = CollUtils.mapToList(subtract, userId -> new NoticeLogDO(noticeId, userId, now));
        return baseMapper.insertBatch(list);
    }
}
