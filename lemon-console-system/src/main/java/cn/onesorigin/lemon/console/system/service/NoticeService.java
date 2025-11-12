package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.enums.NoticeMethodEnum;
import cn.onesorigin.lemon.console.system.model.entity.NoticeDO;
import cn.onesorigin.lemon.console.system.model.query.NoticeQuery;
import cn.onesorigin.lemon.console.system.model.req.NoticeReq;
import cn.onesorigin.lemon.console.system.model.resp.NoticeDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.NoticeResp;
import top.continew.starter.data.service.IService;

import java.util.List;

/**
 * 公告 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-11 09:24
 */
public interface NoticeService extends BaseService<NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq>, IService<NoticeDO> {

    /**
     * 发布公告
     *
     * @param notice 公告信息
     */
    void publish(NoticeDO notice);

    /**
     * 获取未读公告ID列表
     *
     * @param noticeMethod 通知方式
     * @param userId       用户ID
     * @return 未读公告ID列表
     */
    List<Long> findUnreadIdsByUserId(NoticeMethodEnum noticeMethod, Long userId);

    /**
     * 读取公告
     *
     * @param noticeId 公告ID
     * @param userId   用户ID
     */
    void readNotice(Long noticeId, Long userId);
}
