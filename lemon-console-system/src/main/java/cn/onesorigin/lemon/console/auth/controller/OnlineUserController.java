package cn.onesorigin.lemon.console.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.onesorigin.lemon.console.auth.model.query.OnlineUserQuery;
import cn.onesorigin.lemon.console.auth.model.resp.OnlineUserResp;
import cn.onesorigin.lemon.console.auth.service.OnlineUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

/**
 * 在线用户 控制器
 *
 * @author BruceMaa
 * @since 2025-11-07 09:28
 */
@Tag(name = "在线用户")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/monitor/online")
public class OnlineUserController {

    private final OnlineUserService baseService;

    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @SaCheckPermission("monitor:online:list")
    @GetMapping
    public PageResp<OnlineUserResp> page(@Valid OnlineUserQuery query, @Valid PageQuery pageQuery) {
        return baseService.page(query, pageQuery);
    }

    @Operation(summary = "强退在线用户", description = "强退在线用户")
    @Parameter(name = "token", description = "令牌", in = ParameterIn.PATH)
    @SaCheckPermission("monitor:online:kickout")
    @DeleteMapping("/{token}")
    public void kickout(@PathVariable String token) {
        String currentToken = StpUtil.getTokenValue();
        CheckUtils.throwIfEqual(token, currentToken, "不能强退自己");
        StpUtil.kickoutByTokenValue(token);
    }
}
