package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户密码重置请求参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户密码重置请求参数")
public class UserPasswordResetReq {

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    String newPassword;
}
