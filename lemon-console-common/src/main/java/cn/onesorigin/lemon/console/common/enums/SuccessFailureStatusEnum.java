package cn.onesorigin.lemon.console.common.enums;

import cn.onesorigin.lemon.console.common.constant.UiConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 成功失败状态 枚举
 *
 * @author BruceMaa
 * @since 2025-09-26 16:12
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessFailureStatusEnum implements BaseEnum<Integer> {
    /**
     * 成功
     */
    SUCCESS(1, "成功", UiConstants.COLOR_SUCCESS),

    /**
     * 失败
     */
    FAILURE(2, "失败", UiConstants.COLOR_ERROR),
    ;

    private final Integer value;
    private final String description;
    private final String color;
}
