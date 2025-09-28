package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 参数重置请求参数
 *
 * @author BruceMaa
 * @since 2025-09-28 10:47
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "参数重置请求参数")
public class OptionValueResetReq {

    @Schema(description = "键列表")
    List<String> code;

    /**
     * 类别
     */
    @Schema(description = "类别")
    OptionCategoryEnum category;
}
