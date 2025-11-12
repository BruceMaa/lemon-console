package cn.onesorigin.lemon.console.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 公告查询条件
 *
 * @author BruceMaa
 * @since 2025-11-11 09:31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "公告查询条件")
public class NoticeQuery {

    /**
     * 标题
     */
    @Schema(description = "标题", example = "这是公告标题")
    String title;

    /**
     * 分类（取值于字典 notice_type）
     */
    @Schema(description = "分类（取值于字典 notice_type）", example = "1")
    String type;

    /**
     * 用户 ID
     */
    @Schema(hidden = true)
    Long userId;
}
