package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.enums.*;
import cn.onesorigin.lemon.console.system.mapper.NoticeMapper;
import cn.onesorigin.lemon.console.system.model.entity.NoticeDO;
import cn.onesorigin.lemon.console.system.model.query.NoticeQuery;
import cn.onesorigin.lemon.console.system.model.req.MessageReq;
import cn.onesorigin.lemon.console.system.model.req.NoticeReq;
import cn.onesorigin.lemon.console.system.model.resp.NoticeDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.NoticeResp;
import cn.onesorigin.lemon.console.system.service.MessageService;
import cn.onesorigin.lemon.console.system.service.NoticeLogService;
import cn.onesorigin.lemon.console.system.service.NoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 公告 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-11 09:39
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, NoticeDO, NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq> implements NoticeService {

    private final NoticeLogService noticeLogService;
    private final MessageService messageService;

    @Override
    public PageResp<NoticeResp> page(NoticeQuery query, PageQuery pageQuery) {
        IPage<NoticeResp> page = baseMapper.findNotices(new Page<>(pageQuery.getPage(),
                pageQuery.getSize()), query);
        PageResp<NoticeResp> pageResp = PageResp.build(page);
        pageResp.getList().forEach(this::fill);
        return pageResp;
    }

    @Override
    protected void beforeCreate(NoticeReq req) {
        if (NoticeStatusEnum.DRAFT != req.getStatus()) {
            if (Objects.equals(Boolean.TRUE, req.getIsTiming())) {
                // 待发布
                req.setStatus(NoticeStatusEnum.PENDING);
            } else {
                // 已发布
                req.setStatus(NoticeStatusEnum.PUBLISHED);
                req.setPublishTime(LocalDateTime.now());
            }
        }
    }

    @Override
    protected void afterCreate(NoticeReq req, NoticeDO entity) {
        // 发送消息
        if (entity.getStatus() == NoticeStatusEnum.PUBLISHED) {
            this.publish(entity);
        }
    }

    @Override
    protected void beforeUpdate(NoticeReq req, Long id) {
        var oldNotice = super.getById(id);
        switch (oldNotice.getStatus()) {
            case PUBLISHED -> {
                CheckUtils.throwIfNotEqual(req.getStatus(), oldNotice.getStatus(), "公告已发布，不允许修改状态");
                CheckUtils.throwIfNotEqual(req.getIsTiming(), oldNotice.getIsTiming(), "公告已发布，不允许修改定时发布信息");
                CheckUtils.throwIfNotEqual(req.getNoticeScope(), oldNotice.getNoticeScope(), "公告已发布，不允许修改通知范围");
                if (NoticeScopeEnum.USER == oldNotice.getNoticeScope()) {
                    CheckUtils.throwIfNotEmpty(CollUtil.disjunction(req.getNoticeUsers(), oldNotice
                            .getNoticeUsers()), "公告已发布，不允许修改通知用户");
                }
                CheckUtils.throwIf(!CollUtil.isEqualList(req.getNoticeMethods(), oldNotice
                        .getNoticeMethods()), "公告已发布，不允许修改通知方式");
                // 修正定时发布信息
                if (Objects.equals(Boolean.TRUE, oldNotice.getIsTiming())) {
                    CheckUtils.throwIfNotEqual(req.getPublishTime(), oldNotice.getPublishTime(), "公告已发布，不允许修改定时发布信息");
                }
                req.setPublishTime(oldNotice.getPublishTime());
            }
            case DRAFT, PENDING -> {
                // 发布
                if (NoticeStatusEnum.PUBLISHED == req.getStatus()) {
                    if (Objects.equals(Boolean.TRUE, req.getIsTiming())) {
                        // 待发布
                        req.setStatus(NoticeStatusEnum.PENDING);
                    } else {
                        // 已发布
                        req.setStatus(NoticeStatusEnum.PUBLISHED);
                        req.setPublishTime(LocalDateTime.now());
                    }
                }
            }
            default -> throw new IllegalArgumentException("状态无效");
        }
    }

    @Override
    protected void afterUpdate(NoticeReq req, NoticeDO entity) {
        // 重置定时发布时间
        if (NoticeStatusEnum.PUBLISHED != entity.getStatus()
                && Objects.equals(Boolean.FALSE, entity.getIsTiming())
                && entity.getPublishTime() != null) {
            baseMapper.lambdaUpdate().set(NoticeDO::getPublishTime, null).eq(NoticeDO::getId, entity.getId()).update();
        }
        // 发送消息
        if (Objects.equals(Boolean.FALSE, entity.getIsTiming())
                && NoticeStatusEnum.PUBLISHED == entity.getStatus()) {
            this.publish(entity);
        }
    }

    @Override
    protected void afterDelete(List<Long> ids) {
        // 删除公告日志
        noticeLogService.deleteByNoticeIds(ids);
    }

    @Override
    public void publish(NoticeDO notice) {
        List<Integer> noticeMethods = notice.getNoticeMethods();
        if (CollUtil.isNotEmpty(noticeMethods) && noticeMethods.contains(NoticeMethodEnum.SYSTEM_MESSAGE.getValue())) {
            MessageTemplateEnum template = MessageTemplateEnum.NOTICE_PUBLISH;
            MessageReq req = new MessageReq();
            req.setType(MessageTypeEnum.SYSTEM);
            req.setTitle(template.getTitle());
            req.setContent(template.getContent().formatted(notice.getTitle()));
            req.setPath(template.getPath().formatted(notice.getId()));
            // 新增消息
            messageService.add(req, notice.getNoticeUsers());
        }
    }

    @Override
    public List<Long> findUnreadIdsByUserId(NoticeMethodEnum noticeMethod, Long userId) {
        return baseMapper.findUnreadIdsByUserId(noticeMethod == null ? null : noticeMethod.getValue(), userId);
    }

    @Override
    public void readNotice(Long noticeId, Long userId) {
        noticeLogService.add(List.of(userId), noticeId);
    }
}
