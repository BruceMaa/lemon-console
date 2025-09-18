package cn.onesorigin.lemon.console.common.content;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.common.api.system.UserApi;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.continew.starter.core.util.ExceptionUtils;

/**
 * 用户上下文 Holder
 *
 * @author BruceMaa
 * @since 2025-09-02 10:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserContextHolder {

    private static final TransmittableThreadLocal<UserContext> CONTEXT_HOLDER = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<UserExtraContext> EXTRA_CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    /**
     * 设置用户上下文
     *
     * @param context 用户上下文
     */
    public static void setContext(UserContext context) {
        setContext(context, true);
    }

    /**
     * 设置用户上下文
     *
     * @param context  用户上下文
     * @param isUpdate 是否更新会话
     */
    public static void setContext(UserContext context, boolean isUpdate) {
        CONTEXT_HOLDER.set(context);
        if (isUpdate) {
            StpUtil.getSessionByLoginId(context.getId()).set(SaSession.USER, context);
        }
    }

    /**
     * 获取用户上下文
     *
     * @return 用户上下文
     */
    public static UserContext getContext() {
        var context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = StpUtil.getSession().getModel(SaSession.USER, UserContext.class);
            setContext(context, false);
        }
        return context;
    }

    /**
     * 获取指定用户的上下文
     *
     * @param userId 用户ID
     * @return 用户上下文
     */
    public static UserContext getContext(Long userId) {
        var session = StpUtil.getSessionByLoginId(userId, false);
        if (session == null) {
            return null;
        }
        return session.getModel(SaSession.USER, UserContext.class);
    }

    /**
     * 设置用户额外上下文
     *
     * @param context 用户额外上下文
     */
    public static void setExtraContext(UserExtraContext context) {
        EXTRA_CONTEXT_HOLDER.set(context);
    }

    /**
     * 获取用户额外上下文
     *
     * @return 用户额外上下文
     */
    public static UserExtraContext getExtraContext() {
        var extraContext = EXTRA_CONTEXT_HOLDER.get();
        if (extraContext == null) {
            extraContext = getExtraContext(StpUtil.getTokenValue());
            setExtraContext(extraContext);
        }
        return extraContext;
    }

    /**
     * 获取指定令牌的用户额外上下文
     *
     * @param token 令牌
     * @return 用户额外上下文
     */
    public static UserExtraContext getExtraContext(String token) {
        var extraContext = new UserExtraContext();
        extraContext.setIp(Convert.toStr(StpUtil.getExtra(token, "ip")));
        extraContext.setAddress(Convert.toStr(StpUtil.getExtra(token, "address")));
        extraContext.setBrowser(Convert.toStr(StpUtil.getExtra(token, "browser")));
        extraContext.setOs(Convert.toStr(StpUtil.getExtra(token, "os")));
        extraContext.setLoginTime(Convert.toLocalDateTime(StpUtil.getExtra(token, "loginTime")));
        return extraContext;
    }

    /**
     * 清除上下文
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
        EXTRA_CONTEXT_HOLDER.remove();
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID
     */
    public static Long getUserId() {
        return ExceptionUtils.exToNull(() -> getContext().getId());
    }

    /**
     * 获取当前登录用户名
     *
     * @return 当前登录用户名
     */
    public static String getUsername() {
        return ExceptionUtils.exToNull(() -> getContext().getUsername());
    }

    /**
     * 获取当前登录用户昵称
     *
     * @return 当前登录用户昵称
     */
    public static String getNickname() {
        return getNickname(getUserId());
    }

    /**
     * 获取指定用户ID的昵称
     *
     * @param userId 登录用户ID
     * @return 登录用户昵称
     */
    public static String getNickname(Long userId) {
        return ExceptionUtils.exToNull(() -> SpringUtil.getBean(UserApi.class).getNicknameById(userId));
    }

    /**
     * @return 当前登录用户是否为超级管理员
     */
    public static boolean isSuperAdminUser() {
        StpUtil.checkLogin();
        return getContext().isSuperAdminUser();
    }
}
