package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 文件修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-29 10:49
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "文件修改请求参数")
public class FileReq {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "example")
    @NotBlank(message = "名称不能为空")
    @Size(max = 255, message = "名称长度不能超过 {max} 个字符")
    String originalName;

    /**
     * 上级目录
     */
    @Schema(description = "上级目录", example = "/")
    String parentPath;
}
