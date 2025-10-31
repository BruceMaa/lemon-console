package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 用户导入结果响应参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:10
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户导入结果响应参数")
public class UserImportResp {

    /**
     * 总计行数
     */
    @Schema(description = "总计行数", example = "100")
    Integer totalRows;

    /**
     * 新增行数
     */
    @Schema(description = "新增行数", example = "100")
    Integer insertRows;

    /**
     * 修改行数
     */
    @Schema(description = "修改行数", example = "100")
    Integer updateRows;
}
