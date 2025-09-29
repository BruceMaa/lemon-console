package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.StorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

/**
 * 存储查询条件
 *
 * @author BruceMaa
 * @since 2025-09-29 09:15
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "存储查询条件")
public class StorageQuery {

    /**
     * 关键词
     */
    @Schema(description = "关键词", example = "本地存储")
    @Query(columns = {"name", "code", "description"}, type = QueryType.LIKE)
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum stats;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    StorageTypeEnum type;
}
