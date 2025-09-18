package cn.onesorigin.lemon.console.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

/**
 * 字典查询条件
 *
 * @author BruceMaa
 * @since 2025-09-18 10:43
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "字典查询条件")
public class DictQuery {

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    @Query(columns = {"name", "code", "description"}, type = QueryType.LIKE)
    String description;
}
