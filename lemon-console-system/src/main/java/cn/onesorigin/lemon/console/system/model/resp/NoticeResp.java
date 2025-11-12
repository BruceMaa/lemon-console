package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseResp;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告响应参数
 *
 * @author BruceMaa
 * @since 2025-11-11 09:36
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "公告响应参数")
public class NoticeResp extends BaseResp {

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
     * 通知范围
     */
    @Schema(description = "通知范围(1.所有人 2.指定用户)", example = "1")
    NoticeScopeEnum noticeScope;

    /**
     * 通知方式
     */
    @Schema(description = "通知方式", example = "[1,2]")
    List<Integer> noticeMethods;

    /**
     * 是否定时
     */
    @Schema(description = "是否定时", example = "false")
    Boolean isTiming;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间", example = "2023-08-08 00:00:00", type = "string")
    LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶", example = "false")
    Boolean isTop;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "3")
    NoticeStatusEnum status;

    /**
     * 是否已读
     */
    @Schema(description = "是否已读", example = "false")
    Boolean isRead;
}
