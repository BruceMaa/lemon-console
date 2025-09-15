package cn.onesorigin.lemon.console.common.constant;

/**
 * 正则相关常量
 *
 * @author BruceMaa
 * @since 2025-09-17 17:36
 */
public interface RegexConstants {
    /**
     * 用户名正则（用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头）
     */
    String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{3,64}$";

    /**
     * 通用名称正则（长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线）
     */
    String GENERAL_NAME = "^[\\u4e00-\\u9fa5a-zA-Z0-9_-]{2,30}$";

    /**
     * 密码正则（密码长度为 8-32 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字）
     */
    String PASSWORD = "^(?=.*\\d)(?=.*[a-z]).{8,32}$";
}
