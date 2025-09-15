package cn.onesorigin.lemon.console.auth.service;

import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import jakarta.servlet.http.HttpServletRequest;

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
}
