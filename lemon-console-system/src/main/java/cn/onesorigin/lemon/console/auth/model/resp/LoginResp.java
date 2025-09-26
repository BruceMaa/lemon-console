package cn.onesorigin.lemon.console.auth.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 登录响应参数
 *
 * @author BruceMaa
 * @since 2025-09-04 16:47
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Schema(description = "登录响应参数")
public class LoginResp {

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    String token;

}
