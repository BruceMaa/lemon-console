package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;

/**
 * 菜单创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-18 16:21
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "菜单创建或修改请求参数")
public class MenuReq {

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    @NotNull(message = "类型无效", groups = CrudValidationGroup.Create.class)
    MenuTypeEnum type;

    /**
     * 图标
     */
    @Schema(description = "图标", example = "user")
    @Length(max = 50, message = "图标长度不能超过 {max} 个字符")
    String icon;

    /**
     * 标题
     */
    @Schema(description = "标题", example = "用户管理")
    @NotBlank(message = "标题不能为空", groups = CrudValidationGroup.Create.class)
    @Length(max = 30, message = "标题长度不能超过 {max} 个字符")
    String title;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @NotNull(message = "排序不能为空", groups = CrudValidationGroup.Create.class)
    @Min(value = 1, message = "排序最小值为 {value}")
    Integer sort;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识", example = "system:user:list")
    @Length(max = 100, message = "权限标识长度不能超过 {max} 个字符")
    String permission;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址", example = "/system/user")
    @Length(max = 255, message = "路由地址长度不能超过 {max} 个字符")
    String path;

    /**
     * 组件名称
     */
    @Schema(description = "组件名称", example = "User")
    @Length(max = 50, message = "组件名称长度不能超过 {max} 个字符")
    String name;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径", example = "/system/user/index")
    @Length(max = 255, message = "组件路径长度不能超过 {max} 个字符")
    String component;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    String redirect;

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
     * 上级菜单 ID
     */
    @Schema(description = "上级菜单 ID", example = "1000")
    Long parentId;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    @NotNull(message = "状态无效", groups = CrudValidationGroup.Create.class)
    DisEnableStatusEnum status;
}
