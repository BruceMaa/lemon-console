package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 角色和部门关联 实体
 *
 * @author BruceMaa
 * @since 2025-09-29 17:34
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_dept")
public class RoleDeptDO {

    /**
     * 角色ID
     */
    Long roleId;

    /**
     * 部门ID
     */
    Long deptId;
}
