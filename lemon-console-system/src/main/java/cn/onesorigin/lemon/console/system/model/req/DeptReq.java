package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
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
 * 部门创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-19 10:38
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "部门创建或修改请求参数")
public class DeptReq {
    /**
     * 上级部门 ID
     */
    @Schema(description = "上级部门 ID", example = "2")
    @NotNull(message = "上级部门不能为空", groups = CrudValidationGroup.Create.class)
    Long parentId;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试部")
    @NotBlank(message = "名称不能为空", groups = CrudValidationGroup.Create.class)
    @Length(max = 30, message = "名称长度不能超过 {max} 个字符")
    String name;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @Min(value = 1, message = "排序最小值为 {value}")
    Integer sort;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "测试部描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    /**
     * 祖级列表
     */
    @Schema(hidden = true)
    String ancestors;
}
