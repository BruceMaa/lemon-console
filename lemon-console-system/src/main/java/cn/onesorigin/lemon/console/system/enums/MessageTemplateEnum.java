package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * 消息模板 枚举
 *
 * @author BruceMaa
 * @since 2025-11-11 13:29
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MessageTemplateEnum {

    /**
     * 第三方登录
     */
    SOCIAL_REGISTER("欢迎注册 %s", "尊敬的 %s，欢迎注册使用，请及时配置您的密码。", "/user/profile"),

    /**
     * 公告发布
     */
    NOTICE_PUBLISH("您有一条新的公告", "公告《%s》已发布，请及时查看。", "/user/notice?id=%s"),
    ;
    String title;
    String content;
    String path;
}
