package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.annotation.TreeField;

/**
 * 菜单响应参数
 *
 * @author BruceMaa
 * @since 2025-09-18 16:26
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TreeField(value = "id")
@Schema(description = "菜单响应参数")
public class MenuResp extends BaseDetailResp {

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
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;
}
