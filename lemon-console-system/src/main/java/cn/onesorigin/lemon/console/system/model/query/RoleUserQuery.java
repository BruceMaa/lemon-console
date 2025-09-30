package cn.onesorigin.lemon.console.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 角色关联用户查询条件
 *
 * @author BruceMaa
 * @since 2025-09-30 08:53
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色关联用户查询条件")
public class RoleUserQuery {

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    Long roleId;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    String description;
}
