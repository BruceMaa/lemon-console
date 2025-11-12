package cn.onesorigin.lemon.console.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 消息查询条件
 *
 * @author BruceMaa
 * @since 2025-11-12 09:43
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "消息查询条件")
public class MessageQuery {

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
    Integer type;

    /**
     * 是否已读
     */
    @Schema(description = "是否已读", example = "true")
    Boolean isRead;

    /**
     * 用户 ID
     */
    @Schema(hidden = true)
    Long userId;
}
