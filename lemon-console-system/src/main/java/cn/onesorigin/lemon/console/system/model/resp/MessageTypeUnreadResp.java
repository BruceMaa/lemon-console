package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.system.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 各类型未读消息响应参数
 *
 * @author BruceMaa
 * @since 2025-11-12 09:32
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "各类型未读消息响应参数")
public class MessageTypeUnreadResp {

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    MessageTypeEnum type;

    /**
     * 数量
     */
    @Schema(description = "数量")
    Long count;
}
