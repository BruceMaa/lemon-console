package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 公告通知范围 枚举
 *
 * @author BruceMaa
 * @since 2025-11-11 09:20
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NoticeScopeEnum implements BaseEnum<Integer> {

    /**
     * 所有人
     */
    ALL(1, "所有人"),

    /**
     * 指定用户
     */
    USER(2, "指定用户"),
    ;

    Integer value;
    String description;
}
