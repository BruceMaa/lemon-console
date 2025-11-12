package cn.onesorigin.lemon.console.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.system.enums.NoticeMethodEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeStatusEnum;
import cn.onesorigin.lemon.console.system.mapper.NoticeMapper;
import cn.onesorigin.lemon.console.system.model.entity.NoticeDO;
import cn.onesorigin.lemon.console.system.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告发布任务
 *
 * @author BruceMaa
 * @since 2025-11-11 10:24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticePublishJob {

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 * * * * ?")
    public void publishNoticeWithSchedule() {
        log.info("定时任务【公告发布】开始执行");
        publishNotice();
        log.info("定时任务【公告发布】执行结束");
    }

    private static void publishNotice() {
        NoticeMapper noticeMapper = SpringUtil.getBean(NoticeMapper.class);
        // 查询待发布公告
        List<NoticeDO> list = noticeMapper.lambdaQuery()
                .eq(NoticeDO::getStatus, NoticeStatusEnum.PENDING)
                .le(NoticeDO::getPublishTime, LocalDateTime.now())
                .list();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        // 筛选需要发送消息的公告并发送
        List<NoticeDO> needSendMessageList = list.stream()
                .filter(notice -> CollUtil.isNotEmpty(notice.getNoticeMethods()))
                .filter(notice -> notice.getNoticeMethods().contains(NoticeMethodEnum.SYSTEM_MESSAGE.getValue()))
                .toList();
        if (CollUtil.isNotEmpty(needSendMessageList)) {
            // 发送消息
            NoticeService noticeService = SpringUtil.getBean(NoticeService.class);
            needSendMessageList.parallelStream().forEach(noticeService::publish);
        }
        // 更新状态
        noticeMapper.lambdaUpdate()
                .set(NoticeDO::getStatus, NoticeStatusEnum.PUBLISHED)
                .in(NoticeDO::getId, CollUtils.mapToList(list, NoticeDO::getId))
                .update();
    }
}
