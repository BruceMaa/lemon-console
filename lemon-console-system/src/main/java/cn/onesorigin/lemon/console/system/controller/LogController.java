package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.onesorigin.lemon.console.system.model.query.LogQuery;
import cn.onesorigin.lemon.console.system.model.resp.LogDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.LogResp;
import cn.onesorigin.lemon.console.system.service.LogService;
import com.feiniaojin.gracefulresponse.api.ExcludeFromGracefulResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.query.SortQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.log.annotation.Log;

/**
 * 系统日志 控制器
 *
 * @author BruceMaa
 * @since 2025-11-07 14:52
 */
@Tag(name = "系统日志")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/system/logs")
public class LogController {

    private final LogService baseService;

    @Log(ignore = true)
    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @SaCheckPermission("monitor:logs:list")
    @GetMapping
    public PageResp<LogResp> page(@Valid LogQuery query, @Valid PageQuery pageQuery) {
        return baseService.page(query, pageQuery);
    }

    @Log(ignore = true)
    @Operation(summary = "查询详情", description = "查询详情")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("monitor:logs:get")
    @GetMapping("/{id}")
    public LogDetailResp get(@PathVariable Long id) {
        return baseService.get(id);
    }

    @ExcludeFromGracefulResponse
    @Operation(summary = "导出登录日志", description = "导出登录日志")
    @SaCheckPermission("monitor:logs:export")
    @GetMapping("/export/login")
    public void exportLoginLog(@Valid LogQuery query, @Valid SortQuery sortQuery, HttpServletResponse response) {
        baseService.exportLoginLog(query, sortQuery, response);
    }

    @ExcludeFromGracefulResponse
    @Operation(summary = "导出操作日志", description = "导出操作日志")
    @SaCheckPermission("monitor:logs:export")
    @GetMapping("/export/operation")
    public void exportOperationLog(@Valid LogQuery query, @Valid SortQuery sortQuery, HttpServletResponse response) {
        baseService.exportOperationLog(query, sortQuery, response);
    }
}
