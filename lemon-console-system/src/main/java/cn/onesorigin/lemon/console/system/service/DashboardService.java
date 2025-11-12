package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.resp.DashboardNoticeResp;

import java.util.List;

/**
 * 仪表盘 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-12 17:16
 */
public interface DashboardService {

    /**
     * 查询公告列表
     *
     * @return 公告列表
     */
    List<DashboardNoticeResp> findNotices();
}
