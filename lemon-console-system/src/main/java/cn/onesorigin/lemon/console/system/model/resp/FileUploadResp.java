package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * 文件上传响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 11:06
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema
public class FileUploadResp {

    /**
     * 文件 id
     */
    @Schema(description = "文件 id", example = "1897293810343682049")
    String id;

    /**
     * 文件 URL
     */
    @Schema(description = "文件 URL", example = "http://localhost:8080/file/65e87dc3fb377a6fb58bdece.jpg")
    String url;

    /**
     * 缩略图文件 URL
     */
    @Schema(description = "缩略图文件 URL", example = "http://localhost:8080/file/65e87dc3fb377a6fb58bdece.jpg")
    String thUrl;

    /**
     * 元数据
     */
    @Schema(description = "元数据")
    Map<String, String> metadata;
}
