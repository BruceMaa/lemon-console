package cn.onesorigin.lemon.console.controller;

import cn.onesorigin.lemon.console.system.model.resp.DashboardNoticeResp;
import cn.onesorigin.lemon.console.system.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.log.annotation.Log;

import java.util.List;

/**
 * 仪表盘 控制器
 *
 * @author BruceMaa
 * @since 2025-11-12 17:15
 */
@Tag(name = "仪表盘")
@Log(ignore = true)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {

    private final DashboardService baseService;

    @Operation(summary = "查询公告列表", description = "查询公告列表")
    @GetMapping("/notices")
    public List<DashboardNoticeResp> findNotices() {
        return baseService.findNotices();
    }
}
