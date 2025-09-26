package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;

/**
 * 用户角色关联 实体
 *
 * @author BruceMaa
 * @since 2025-09-24 16:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_user_role")
public class UserRoleDO extends BaseIdDO {

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 角色ID
     */
    Long roleId;
}
