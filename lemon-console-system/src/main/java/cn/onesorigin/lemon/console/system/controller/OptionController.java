package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.onesorigin.lemon.console.system.model.query.OptionQuery;
import cn.onesorigin.lemon.console.system.model.req.OptionReq;
import cn.onesorigin.lemon.console.system.model.req.OptionValueResetReq;
import cn.onesorigin.lemon.console.system.model.resp.OptionResp;
import cn.onesorigin.lemon.console.system.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-28 09:51
 */
@Tag(name = "参数管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/system/options")
public class OptionController {

    private final OptionService optionService;

    @Operation(summary = "查询参数列表")
    @SaCheckPermission(value = {
            "system:siteConfig:get",
            "system:securityConfig:get",
            "system:loginConfig:get",
            "system:mailConfig:get"
    }, mode = SaMode.OR)
    @GetMapping(path = "/list")
    public List<OptionResp> list(@Valid OptionQuery optionQuery) {
        return optionService.list(optionQuery);
    }


    @Operation(summary = "修改参数")
    @SaCheckPermission(value = {
            "system:siteConfig:update",
            "system:securityConfig:update",
            "system:loginConfig:update",
            "system:mailConfig:update"
    })
    @PutMapping
    public void update(@RequestBody @Valid List<OptionReq> options) {
        optionService.updateBatch(options);
    }

    @Operation(summary = "重置参数")
    @SaCheckPermission(value = {
            "system:siteConfig:update",
            "system:securityConfig:update",
            "system:loginConfig:update",
            "system:mailConfig:update"
    })
    @PatchMapping(path = "/value")
    public void resetValue(@RequestBody @Valid OptionValueResetReq req) {
        optionService.resetValue(req);
    }

}
