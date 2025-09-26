package cn.onesorigin.lemon.console.auth.model.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 路由响应参数
 *
 * @author BruceMaa
 * @since 2025-09-24 20:13
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "路由响应参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1010")
    Long id;

    /**
     * 上级菜单 ID
     */
    @Schema(description = "上级菜单ID", example = "1000")
    Long parentId;

    /**
     * 标题
     */
    @Schema(description = "标题", example = "用户管理")
    String title;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    Integer type;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址", example = "/system/user")
    String path;

    /**
     * 组件名称
     */
    @Schema(description = "组件名称", example = "User")
    String name;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径", example = "/system/user/index")
    String component;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    String redirect;

    /**
     * 图标
     */
    @Schema(description = "图标", example = "user")
    String icon;

    /**
     * 是否外链
     */
    @Schema(description = "是否外链", example = "false")
    Boolean isExternal;

    /**
     * 是否缓存
     */
    @Schema(description = "是否缓存", example = "false")
    Boolean isCache;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏", example = "false")
    Boolean isHidden;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识", example = "system:user:list")
    String permission;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    Integer sort;

    /**
     * 子路由列表
     */
    @Schema(description = "子路由列表")
    List<RouteResp> children;
}
