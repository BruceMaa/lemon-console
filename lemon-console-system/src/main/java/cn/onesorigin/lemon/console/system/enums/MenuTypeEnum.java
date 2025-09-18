package cn.onesorigin.lemon.console.system.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 菜单类型 枚举
 *
 * @author BruceMaa
 * @since 2025-09-18 16:20
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum MenuTypeEnum implements BaseEnum<Integer> {
    /**
     * 目录
     */
    DIR(1, "目录"),

    /**
     * 菜单
     */
    MENU(2, "菜单"),

    /**
     * 按钮
     */
    BUTTON(3, "按钮"),;

    Integer value;
    String description;
}
