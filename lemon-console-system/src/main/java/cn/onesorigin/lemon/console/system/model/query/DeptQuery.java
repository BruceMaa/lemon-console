package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

/**
 * 部门查询条件
 *
 * @author BruceMaa
 * @since 2025-09-19 10:39
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "部门查询条件")
public class DeptQuery {
    /**
     * 关键词
     */
    @Schema(description = "关键词", example = "测试部")
    @Query(columns = {"name", "description"}, type = QueryType.LIKE)
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;
}
