package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.system.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 消息创建请求参数
 *
 * @author BruceMaa
 * @since 2025-11-11 13:31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "消息创建请求参数")
public class MessageReq {

    /**
     * 标题
     */
    @Schema(description = "标题", example = "欢迎注册 xxx")
    @NotBlank(message = "标题不能为空")
    @Size(max = 50, message = "标题长度不能超过 {max} 个字符")
    String title;

    /**
     * 内容
     */
    @Schema(description = "内容", example = "尊敬的 xx，欢迎注册使用，请及时配置您的密码。")
    @NotBlank(message = "内容不能为空")
    @Size(max = 255, message = "内容长度不能超过 {max} 个字符")
    String content;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "1")
    @NotNull(message = "类型无效")
    MessageTypeEnum type;

    /**
     * 跳转路径
     */
    @Schema(description = "跳转路径", example = "/user/profile")
    String path;
}
