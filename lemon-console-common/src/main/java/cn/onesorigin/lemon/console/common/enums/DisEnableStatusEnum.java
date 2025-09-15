package cn.onesorigin.lemon.console.common.enums;

import cn.onesorigin.lemon.console.common.constant.UiConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 启用/禁用状态枚举
 *
 * @author BruceMaa
 * @since 2025-09-04 17:27
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum DisEnableStatusEnum implements BaseEnum<Integer> {
    /**
     * 启用
     */
    ENABLE(1, "启用", UiConstants.COLOR_SUCCESS),

    /**
     * 禁用
     */
    DISABLE(0, "禁用", UiConstants.COLOR_ERROR),
    ;

    Integer value;
    String description;
    String color;
}
