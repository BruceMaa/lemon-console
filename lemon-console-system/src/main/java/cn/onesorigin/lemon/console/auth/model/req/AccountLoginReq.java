package cn.onesorigin.lemon.console.auth.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 账号密码登录请求参数
 *
 * @author BruceMaa
 * @since 2025-09-04 16:21
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "账号密码登录请求参数")
public class AccountLoginReq {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID", example = "123456asdf")
    @NotBlank(message = "客户端ID不能为空")
    String clientId;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空")
    String username;

    /**
     * 密码
     */
    @Schema(description = "密码（加密）")
    @NotBlank(message = "密码不能为空")
    String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码", example = "1234")
    String captchaCode;

    /**
     * 验证码标识
     */
    @Schema(description = "验证码标识", example = "abcdefg")
    String captchaKey;
}
