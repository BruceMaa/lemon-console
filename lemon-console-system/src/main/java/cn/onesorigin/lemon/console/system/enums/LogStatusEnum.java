package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 日志状态 枚举
 *
 * @author BruceMaa
 * @since 2025-11-07 14:26
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LogStatusEnum implements BaseEnum<Integer> {
    SUCCESS(1, "成功"),
    FAILURE(2, "失败"),
    ;
    Integer value;
    String description;
}
