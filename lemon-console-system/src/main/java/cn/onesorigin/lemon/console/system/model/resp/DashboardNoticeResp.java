package cn.onesorigin.lemon.console.system.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 仪表盘-公告响应参数
 *
 * @author BruceMaa
 * @since 2025-11-12 17:17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "仪表盘-公告响应参数")
public class DashboardNoticeResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 标题
     */
    @Schema(description = "标题", example = "这是公告标题")
    String title;

    /**
     * 类型（取值于字典 notice_type）
     */
    @Schema(description = "类型（取值于字典 notice_type）", example = "1")
    String type;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶", example = "false")
    Boolean isTop;
}
