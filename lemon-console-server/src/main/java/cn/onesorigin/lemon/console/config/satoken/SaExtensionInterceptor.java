package cn.onesorigin.lemon.console.config.satoken;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Sa-Token 扩展拦截器
 *
 * @author BruceMaa
 * @since 2025-09-24 19:25
 */
@Slf4j
public class SaExtensionInterceptor extends SaInterceptor {

    public SaExtensionInterceptor(SaParamFunction<Object> auth) {
        super(auth);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = super.preHandle(request, response, handler);
        if (!flag || !StpUtil.isLogin()) {
            return flag;
        }
        // 设置上下文
        UserContext userContext = UserContextHolder.getContext();
        if (userContext == null) {
            return true;
        }
        UserContextHolder.getExtraContext();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除上下文
        try {
            super.afterCompletion(request, response, handler, ex);
        } finally {
            UserContextHolder.clearContext();
        }
    }
}
