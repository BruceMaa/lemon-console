package cn.onesorigin.lemon.console.system.enums;

import cn.onesorigin.lemon.console.common.constant.UiConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 消息类型 枚举
 *
 * @author BruceMaa
 * @since 2025-11-11 13:32
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MessageTypeEnum implements BaseEnum<Integer> {

    /**
     * 系统消息
     */
    SYSTEM(1, "系统消息", UiConstants.COLOR_PRIMARY),

    /**
     * 安全消息
     */
    SECURITY(2, "安全消息", UiConstants.COLOR_WARNING),
    ;

    Integer value;
    String description;
    String color;
}
