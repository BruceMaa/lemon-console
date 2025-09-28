package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 参数修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-28 09:59
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "参数修改请求参数")
public class OptionReq {

    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    Long id;

    /**
     * 键
     */
    @Schema(description = "键")
    @NotBlank(message = "键不能为空")
    @Size(max = 100, message = "键长度不饿能超过 {max} 个字符")
    String code;

    /**
     * 值
     */
    @Schema(description = "值")
    String value;
}
