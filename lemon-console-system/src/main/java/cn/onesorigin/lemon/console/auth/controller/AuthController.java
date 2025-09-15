package cn.onesorigin.lemon.console.auth.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import cn.onesorigin.lemon.console.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证 API
 *
 * @author BruceMaa
 * @since 2025-09-04 16:00
 */
@RequiredArgsConstructor
@Tag(name = "认证 API")
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @SaIgnore
    @Operation(summary = "登录", description = "用户登录")
    @PostMapping(path = "/login")
    public LoginResp login(@RequestBody @Valid AccountLoginReq req, HttpServletRequest request) {
        return authService.login(req, request);
    }
}
