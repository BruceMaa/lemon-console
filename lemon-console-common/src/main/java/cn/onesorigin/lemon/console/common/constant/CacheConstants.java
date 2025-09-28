package cn.onesorigin.lemon.console.common.constant;

import top.continew.starter.core.constant.StringConstants;

/**
 * 缓存相关常量
 *
 * @author BruceMaa
 * @since 2025-09-02 14:38
 */
public interface CacheConstants {

    /**
     * 分隔符
     */
    String DELIMITER = StringConstants.COLON;

    /**
     * 验证码缓存前缀
     */
    String CAPTCHA_KEY_PREFIX = "CAPTCHA" + DELIMITER;

    /**
     * 用户缓存键前缀
     */
    String USER_KEY_PREFIX = "USER" + DELIMITER;

    /**
     * 参数缓存键前缀
     */
    String OPTION_KEY_PREFIX = "OPTION" + DELIMITER;

    /**
     * 用户密码错误次数缓存键前缀
     */
    String USER_PASSWORD_ERROR_KEY_PREFIX = USER_KEY_PREFIX + "PASSWORD_ERROR" + DELIMITER;

    /**
     * 字典缓存键前缀
     */
    String DICT_KEY_PREFIX = "DICT" + DELIMITER;
}
