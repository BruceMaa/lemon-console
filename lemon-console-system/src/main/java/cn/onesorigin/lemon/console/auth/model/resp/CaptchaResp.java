package cn.onesorigin.lemon.console.auth.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 验证码响应参数
 *
 * @author BruceMaa
 * @since 2025-09-02 14:24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Schema(description = "验证码响应参数")
public class CaptchaResp {

    /**
     * 验证码标识
     */
    @Schema(description = "验证码标识", example = "123456")
    String uuid;

    /**
     * 验证码图片
     */
    @Schema(description = "验证码图片", example = "data:image/png;base64,")
    String img;

    /**
     * 过期时间戳
     */
    @Schema(description = "过期时间戳", example = "1693600000000")
    Long expireTime;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用", example = "true")
    Boolean isEnabled;

    /**
     * 构建验证码信息
     *
     * @param uuid       验证码标识
     * @param img        验证码图片
     * @param expireTime 过期时间戳
     * @return 验证码响应参数
     */
    public static CaptchaResp of(String uuid, String img, Long expireTime) {
        return CaptchaResp.builder()
                .uuid(uuid)
                .img(img)
                .expireTime(expireTime)
                .isEnabled(true)
                .build();
    }
}
