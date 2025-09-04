package cn.onesorigin.lemon.console.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 数据权限枚举
 *
 * @author BruceMaa
 * @since 2025-09-02 13:48
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum DataScopeEnum implements BaseEnum<Integer> {
    /**
     * 全部数据权限
     */
    ALL(1, "全部数据权限"),
    /**
     * 本部门及以下数据权限
     */
    DEPT_AND_CHILD(2, "本部门及以下数据权限"),
    /**
     * 本部门数据权限
     */
    DEPT(3, "本部门数据权限"),
    /**
     * 仅本人数据权限
     */
    SELF(4, "仅本人数据权限"),
    /**
     * 自定义数据权限
     */
    CUSTOM(5, "自定义数据权限");

    Integer value;
    String description;
}
