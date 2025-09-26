package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.validation.constraints.Mobile;

/**
 * 用户手机号修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-26 17:39
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户手机号修改请求参数")
public class UserPhoneUpdateReq {

    /**
     * 新手机号
     */
    @Schema(description = "新手机号", example = "13811111111")
    @NotBlank(message = "新手机号不能为空")
    @Mobile(message = "新手机号格式不正确")
    String phone;

    /**
     * 当前密码
     */
    @Schema(description = "当前密码", example = "RSA 公钥加密的当前密码")
    @NotBlank(message = "当前密码不能为空")
    String oldPassword;
}
