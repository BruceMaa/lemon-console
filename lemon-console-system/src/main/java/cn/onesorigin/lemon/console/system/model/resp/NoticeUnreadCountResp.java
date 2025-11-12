package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * 未读公告数量响应参数
 *
 * @author BruceMaa
 * @since 2025-11-12 09:46
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "未读公告数量响应参数")
public class NoticeUnreadCountResp {

    /**
     * 未读公告数量
     */
    @Schema(description = "未读公告数量")
    Integer total;
}
