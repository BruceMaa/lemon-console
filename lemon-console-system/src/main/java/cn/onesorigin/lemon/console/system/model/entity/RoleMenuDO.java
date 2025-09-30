package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 角色和菜单关联 实体
 *
 * @author BruceMaa
 * @since 2025-09-30 09:10
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
public class RoleMenuDO {

    /**
     * 角色ID
     */
    Long roleId;

    /**
     * 菜单ID
     */
    Long menuId;
}
