package cn.onesorigin.lemon.console.common.config.websocket;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import top.continew.starter.core.exception.BusinessException;
import top.continew.starter.messaging.websocket.core.WebSocketClientService;

/**
 * 当前登录用户 Provider
 *
 * @author BruceMaa
 * @since 2025-09-26 16:07
 */
@Component
public class WebSocketClientServiceImpl implements WebSocketClientService {
    @Override
    public String getClientId(ServletServerHttpRequest request) {
        HttpServletRequest servletRequest = request.getServletRequest();
        String token = servletRequest.getParameter("token");
        if (StpUtil.getLoginIdByToken(token) == null) {
            throw new BusinessException("登录已过期，请重新登录");
        }
        return token;
    }
}
