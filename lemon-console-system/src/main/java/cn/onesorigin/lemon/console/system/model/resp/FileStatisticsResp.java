package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.system.enums.FileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 文件资源统计响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 10:46
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "文件资源统计响应参数")
public class FileStatisticsResp {

    /**
     * 文件类型
     */
    @Schema(description = "类型", example = "2")
    FileTypeEnum type;

    /**
     * 大小（字节）
     */
    @Schema(description = "大小（字节）", example = "4096")
    Long size;

    /**
     * 数量
     */
    @Schema(description = "数量", example = "1000")
    Long number;

    /**
     * 分类数据
     */
    @Schema(description = "分类数据")
    List<FileStatisticsResp> data;
}
