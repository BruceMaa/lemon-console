package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.system.enums.FileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

/**
 * 文件查询条件
 *
 * @author BruceMaa
 * @since 2025-09-29 10:51
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "文件查询条件")
public class FileQuery {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "example")
    @Query(type = QueryType.LIKE)
    String originalName;

    /**
     * 上级目录
     */
    @Schema(description = "上级目录", example = "/")
    String parentPath;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    FileTypeEnum type;
}
