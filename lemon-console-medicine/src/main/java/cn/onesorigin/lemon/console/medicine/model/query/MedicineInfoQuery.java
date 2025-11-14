package cn.onesorigin.lemon.console.medicine.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

/**
 * 药品基本信息查询条件
 *
 * @author BruceMaa
 * @since 2025-11-13 14:52
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineInfoQuery {

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    @Query(columns = {"code", "generic_name", "english_name", "pinyin"}, type = QueryType.LIKE)
    String info;
}
