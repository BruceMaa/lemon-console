package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.system.model.resp.DashboardNoticeResp;
import cn.onesorigin.lemon.console.system.service.DashboardService;
import cn.onesorigin.lemon.console.system.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仪表盘 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-12 17:35
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final NoticeService noticeService;

    @Override
    public List<DashboardNoticeResp> findNotices() {
        return noticeService.findDashboard();
    }
}
