package cn.onesorigin.lemon.console.auth.enums;

import cn.onesorigin.lemon.console.common.constant.UiConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 认证类型枚举
 *
 * @author BruceMaa
 * @since 2025-09-04 16:14
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum AuthTypeEnum implements BaseEnum<String> {
    /**
     * 账号密码
     */
    ACCOUNT("ACCOUNT", "账号密码", UiConstants.COLOR_SUCCESS),

    /**
     * 邮箱
     */
    EMAIL("EMAIL", "邮箱", UiConstants.COLOR_PRIMARY),

    /**
     * 手机号
     */
    PHONE("PHONE", "手机号", UiConstants.COLOR_PRIMARY),

    /**
     * 第三方账号
     */
    SOCIAL("SOCIAL", "第三方账号", UiConstants.COLOR_ERROR),
    ;
    String value;
    String description;
    String color;
}
