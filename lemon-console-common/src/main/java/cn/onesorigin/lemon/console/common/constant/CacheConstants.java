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
     * 参数缓存键前缀
     */
    String OPTION_KEY_PREFIX = "OPTION" + DELIMITER;
}
