package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 公告通知方式 枚举
 *
 * @author BruceMaa
 * @since 2025-11-11 09:53
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NoticeMethodEnum implements BaseEnum<Integer> {

    /**
     * 系统消息
     */
    SYSTEM_MESSAGE(1, "系统消息"),

    /**
     * 登录弹窗
     */
    LOGIN_POPUP(2, "登录弹窗"),
    ;

    Integer value;
    String description;
}
