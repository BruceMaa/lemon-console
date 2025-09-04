package cn.onesorigin.lemon.console.common.content;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.util.ExceptionUtils;
import top.continew.starter.core.util.IpUtils;
import top.continew.starter.core.util.ServletUtils;

import java.time.LocalDateTime;

/**
 * 用户额外上下文
 *
 * @author BruceMaa
 * @since 2025-09-02 10:58
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserExtraContext {

    /**
     * IP
     */
    String ip;

    /**
     * IP 归属地
     */
    String address;

    /**
     * 浏览器
     */
    String browser;

    /**
     * 操作系统
     */
    String os;

    /**
     * 登录时间
     */
    LocalDateTime loginTime;

    public UserExtraContext(HttpServletRequest request) {
        this.setIp(JakartaServletUtil.getClientIP(request));
        this.setAddress(ExceptionUtils.exToNull(() -> IpUtils.getIpv4Address(this.getIp())));
        this.setBrowser(ServletUtils.getBrowser(request));
        this.setOs(StrUtil.subBefore(ServletUtils.getOs(request), "or", true));
        this.setLoginTime(LocalDateTime.now());
    }
}
