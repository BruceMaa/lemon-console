package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户邮箱修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-26 17:45
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户邮箱修改请求参数")
public class UserEmailUpdateReq {

    /**
     * 新邮箱
     */
    @Schema(description = "新邮箱", example = "123456789@qq.com")
    @NotBlank(message = "新邮箱不能为空")
    @Email(message = "新邮箱格式不正确")
    String email;

    /**
     * 当前密码
     */
    @Schema(description = "当前密码", example = "RSA 公钥加密的当前密码")
    @NotBlank(message = "当前密码不能为空")
    String oldPassword;
}
