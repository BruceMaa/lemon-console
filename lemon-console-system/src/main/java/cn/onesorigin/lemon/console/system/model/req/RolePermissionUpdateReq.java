package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色功能权限修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-29 19:34
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色功能权限修改请求参数")
public class RolePermissionUpdateReq {

    /**
     * 角色 ID
     */
    @Schema(description = "角色 ID", example = "1")
    Long roleId;

    /**
     * 功能权限：菜单 ID 列表
     */
    @Schema(description = "功能权限：菜单 ID 列表", example = "1000,1010,1011,1012,1013,1014")
    List<Long> menuIds = new ArrayList<>();

    /**
     * 菜单选择是否父子节点关联
     */
    @Schema(description = "菜单选择是否父子节点关联", example = "false")
    Boolean menuCheckStrictly;
}
