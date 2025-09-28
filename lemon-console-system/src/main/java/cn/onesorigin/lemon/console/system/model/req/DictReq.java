package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;

/**
 * 字典创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-18 10:40
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "字典创建或修改请求参数")
public class DictReq {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "公告类型")
    @NotBlank(message = "名称不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "名称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "notice_type")
    @NotBlank(message = "编码不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.GENERAL_CODE, message = "编码长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头")
    String code;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "公告类型描述信息")
    @Size(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;
}
