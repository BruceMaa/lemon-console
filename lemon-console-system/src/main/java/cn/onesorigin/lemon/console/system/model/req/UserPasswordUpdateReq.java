package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户密码修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-26 17:24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户密码修改请求参数")
public class UserPasswordUpdateReq {

    /**
     * 当前密码
     */
    @Schema(description = "当前密码", example = "RSA 公钥加密的当前密码")
    String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码", example = "RSA 公钥加密的新密码")
    @NotBlank(message = "新密码不能为空")
    String newPassword;
}
