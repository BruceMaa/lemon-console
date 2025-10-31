package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.ImportPolicyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户导入请求参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户导入请求参数")
public class UserImportReq {

    /**
     * 导入会话KEY
     */
    @Schema(description = "导入会话KEY")
    @NotBlank(message = "导入已过期，请重新上传")
    String importKey;

    /**
     * 用户重复策略
     */
    @Schema(description = "重复用户策略", example = "1")
    @NotNull(message = "重复用户策略不能为空")
    ImportPolicyEnum duplicateUser;

    /**
     * 重复邮箱策略
     */
    @Schema(description = "重复邮箱策略", example = "1")
    @NotNull(message = "重复邮箱策略不能为空")
    ImportPolicyEnum duplicateEmail;

    /**
     * 重复手机策略
     */
    @Schema(description = "重复手机策略", example = "1")
    @NotNull(message = "重复手机策略不能为空")
    ImportPolicyEnum duplicatePhone;

    /**
     * 默认状态
     */
    @Schema(description = "默认状态", example = "1")
    DisEnableStatusEnum defaultStatus;
}
