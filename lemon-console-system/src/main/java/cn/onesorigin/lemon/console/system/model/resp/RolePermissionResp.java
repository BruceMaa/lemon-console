package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 角色权限树响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 19:31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色权限树响应参数")
public class RolePermissionResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 标题
     */
    @Schema(description = "标题", example = "用户管理")
    String title;

    /**
     * 上级菜单 ID
     */
    @Schema(description = "上级菜单 ID", example = "1000")
    Long parentId;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    MenuTypeEnum type;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识", example = "system:users:list")
    String permission;

    /**
     * 子菜单列表
     */
    @Schema(description = "子菜单")
    List<RolePermissionResp> children;
}
