package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.system.enums.FileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 文件响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 10:51
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "文件响应参数")
public class FileResp extends BaseDetailResp {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "6824afe8408da079832dcfb6.jpg")
    String name;

    /**
     * 原始名称
     */
    @Schema(description = "原始名称", example = "example.jpg")
    String originalName;

    /**
     * 大小（字节）
     */
    @Schema(description = "大小（字节）", example = "4096")
    Long size;

    /**
     * URL
     */
    @Schema(description = "URL")
    String url;

    /**
     * 上级目录
     */
    @Schema(description = "上级目录")
    String parentPath;

    /**
     * 路径
     */
    @Schema(description = "路径")
    String path;

    /**
     * 扩展名
     */
    @Schema(description = "扩展名", example = "jpg")
    String extension;

    /**
     * 内容类型
     */
    @Schema(description = "内容类型", example = "image/jpeg")
    String contentType;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    FileTypeEnum type;

    /**
     * SHA256 值
     */
    @Schema(description = "SHA256 值", example = "722f185c48bed892d6fa12e2b8bf1e5f8200d4a70f522fb62112b6caf13cb74e")
    String sha256;

    /**
     * 元数据
     */
    @Schema(description = "元数据", example = "{width:1024,height:1024}")
    String metadata;

    /**
     * 缩略图名称
     */
    @Schema(description = "缩略图名称", example = "example.jpg.min.jpg")
    String thumbnailName;

    /**
     * 缩略图大小（字节)
     */
    @Schema(description = "缩略图大小（字节)", example = "1024")
    Long thumbnailSize;

    /**
     * 缩略图元数据
     */
    @Schema(description = "缩略图文件元数据", example = "{width:100,height:100}")
    String thumbnailMetadata;

    /**
     * 缩略图 URL
     */
    @Schema(description = "缩略图 URL")
    String thumbnailUrl;

    /**
     * 存储 ID
     */
    @Schema(description = "存储 ID", example = "1")
    Long storageId;

    /**
     * 存储名称
     */
    @Schema(description = "存储名称", example = "MinIO")
    String storageName;
}
