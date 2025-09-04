package cn.onesorigin.lemon.console.common.content;

import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 角色上下文
 *
 * @author BruceMaa
 * @since 2025-09-02 13:47
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RoleContext {

    /**
     * ID
     */
    Long id;

    /**
     * 角色编码
     */
    String code;

    /**
     * 数据权限
     */
    DataScopeEnum dataScope;
}
