package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户导入解析响应参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:09
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户导入解析响应参数")
public class UserImportParseResp {

    /**
     * 导入会话 Key
     */
    @Schema(description = "导入会话Key")
    String importKey;

    /**
     * 总计行数
     */
    @Schema(description = "总计行数", example = "100")
    Integer totalRows;

    /**
     * 有效行数
     */
    @Schema(description = "有效行数", example = "100")
    Integer validRows;

    /**
     * 重复行数
     */
    @Schema(description = "重复行数", example = "100")
    Integer duplicateUserRows;

    /**
     * 重复邮箱行数
     */
    @Schema(description = "重复邮箱行数", example = "100")
    Integer duplicateEmailRows;

    /**
     * 重复手机行数
     */
    @Schema(description = "重复手机行数", example = "100")
    Integer duplicatePhoneRows;
}
