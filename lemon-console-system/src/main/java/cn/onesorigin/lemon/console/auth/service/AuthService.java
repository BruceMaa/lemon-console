package cn.onesorigin.lemon.console.auth.service;

import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import cn.onesorigin.lemon.console.auth.model.resp.RouteResp;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 认证业务接口
 *
 * @author BruceMaa
 * @since 2025-09-04 16:49
 */
public interface AuthService {

    /**
     * 登录
     *
     * @param req     登录请求
     * @param request HTTP 请求
     * @return 登录响应
     */
    LoginResp login(AccountLoginReq req, HttpServletRequest request);

    /**
     * 构建路由树
     *
     * @param userId 用户ID
     * @return 路由树
     */
    List<RouteResp> buildRouteTree(Long userId);
}
