package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 未读消息响应参数
 *
 * @author BruceMaa
 * @since 2025-11-12 09:30
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "未读消息响应参数")
public class MessageUnreadResp {

    /**
     * 未读消息数量
     */
    @Schema(description = "未读消息数量")
    Long total;

    /**
     * 各类型未读消息数量
     */
    @Schema(description = "各类型未读消息数量")
    List<MessageTypeUnreadResp> details;
}
