package cn.onesorigin.lemon.console.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

import java.util.List;

/**
 * 角色编码枚举
 *
 * @author BruceMaa
 * @since 2025-09-02 13:51
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum RoleCodeEnum implements BaseEnum<String> {

    /**
     * 超级管理员，内置且仅有一位超级管理员
     */
    SUPER_ADMIN("super_admin", "超级管理员"),

    /**
     * 系统管理员
     */
    SYSTEM_ADMIN("sys_admin", "系统管理员"),

    /**
     * 普通用户
     */
    GENERAL_USER("general", "普通用户"),
    ;

    String value;
    String description;

    /**
     * @return 超级管理员角色编码列表
     */
    public static List<String> getSuperRoleCodes() {
        return List.of(SUPER_ADMIN.getValue());
    }

    /**
     * @param code 角色编码
     * @return 是否为超级管理员角色编码
     */
    public static boolean isSuperRoleCode(String code) {
        return getSuperRoleCodes().contains(code);
    }
}
