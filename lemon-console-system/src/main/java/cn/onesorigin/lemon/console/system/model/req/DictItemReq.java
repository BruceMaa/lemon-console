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
 * 字典项创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-18 15:26
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "字典项创建或修改请求参数")
public class DictItemReq {

    /**
     * 所属字典
     */
    @Schema(description = "所属字典", example = "1")
    @NotNull(message = "所属字典不能为空", groups = CrudValidationGroup.Create.class)
    Long dictId;

    /**
     * 标签
     */
    @Schema(description = "标签", example = "通知")
    @NotBlank(message = "标签不能为空", groups = CrudValidationGroup.Create.class)
    @Length(max = 30, message = "标签长度不能超过 {max} 个字符")
    String label;

    /**
     * 值
     */
    @Schema(description = "值", example = "1")
    @NotBlank(message = "值不能为空", groups = CrudValidationGroup.Create.class)
    @Length(max = 30, message = "值长度不能超过 {max} 个字符")
    String value;

    /**
     * 标签颜色
     */
    @Schema(description = "标签颜色", example = "blue")
    @Length(max = 30, message = "标签颜色长度不能超过 {max} 个字符")
    String color;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @Min(value = 1, message = "排序最小值为 {value}")
    Integer sort;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "通知描述信息")
    @Length(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;
}
