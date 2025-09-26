package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 参数类别 枚举
 *
 * @author BruceMaa
 * @since 2025-09-24 13:52
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum OptionCategoryEnum {

    /**
     * 系统配置
     */
    SITE,

    /**
     * 密码配置
     */
    PASSWORD,

    /**
     * 邮箱配置
     */
    MAIL,

    /**
     * 登录配置
     */
    LOGIN,
}
