package cn.onesorigin.lemon.console.auth.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.onesorigin.lemon.console.auth.convert.UserInfoConvert;
import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import cn.onesorigin.lemon.console.auth.model.resp.RouteResp;
import cn.onesorigin.lemon.console.auth.model.resp.UserInfoResp;
import cn.onesorigin.lemon.console.auth.service.AuthService;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证 API
 *
 * @author BruceMaa
 * @since 2025-09-04 16:00
 */
@Tag(name = "认证管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @SaIgnore
    @Operation(summary = "登录", description = "用户登录")
    @PostMapping(path = "/login")
    public LoginResp login(@RequestBody @Valid AccountLoginReq req, HttpServletRequest request) {
        return authService.login(req, request);
    }

    @Operation(summary = "登出", description = "用户登出")
    @Parameter(name = "Authorization", description = "令牌", required = true, in = ParameterIn.HEADER)
    @PostMapping(path = "/logout")
    public Object logout() {
        var loginId = StpUtil.getLoginId(-1L);
        StpUtil.logout();
        return loginId;
    }

    @Operation(summary = "获取用户信息", description = "获取登录用户信息")
    @GetMapping("/user/info")
    public UserInfoResp getUserInfo() {
        var userContext = UserContextHolder.getContext();
        var userDetailResp = userService.get(userContext.getId());
        var userInfoResp = UserInfoConvert.INSTANCE.toResp(userDetailResp);
        userInfoResp.setPermissions(userContext.getPermissionCodes());
        userInfoResp.setRoles(userContext.getRoleCodes());
        userInfoResp.setPwdExpired(userContext.isPasswordExpired());
        return userInfoResp;
    }

    @Operation(summary = "获取路由信息", description = "获取登录用户的路由信息")
    @GetMapping("/user/route")
    public List<RouteResp> listRoute() {
        return authService.buildRouteTree(UserContextHolder.getUserId());
    }
}
