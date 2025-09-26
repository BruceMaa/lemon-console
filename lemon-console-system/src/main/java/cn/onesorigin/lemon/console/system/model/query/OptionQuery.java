package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;
import top.continew.starter.validation.constraints.EnumValue;

import java.util.List;

/**
 * 参数查询条件
 *
 * @author BruceMaa
 * @since 2025-09-24 13:50
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "参数查询条件")
public class OptionQuery {

    /**
     * 键列表
     */
    @Schema(description = "键列表")
    @Query(type = QueryType.IN)
    List<String> code;

    /**
     * 类别
     */
    @Schema(description = "类别")
    @EnumValue(value = OptionCategoryEnum.class, message = "类别无效")
    String category;
}
