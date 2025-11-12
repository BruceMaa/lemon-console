package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.system.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 消息响应参数
 *
 * @author BruceMaa
 * @since 2025-11-12 09:41
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "消息响应参数")
public class MessageResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 标题
     */
    @Schema(description = "标题", example = "欢迎注册 xxx")
    String title;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "1")
    MessageTypeEnum type;

    /**
     * 跳转路径
     */
    @Schema(description = "跳转路径")
    String path;

    /**
     * 是否已读
     */
    @Schema(description = "是否已读", example = "true")
    Boolean isRead;

    /**
     * 读取时间
     */
    @Schema(description = "读取时间", example = "2023-08-08 23:59:59", type = "string")
    LocalDateTime readTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2023-08-08 08:08:08", type = "string")
    LocalDateTime createdAt;
}
