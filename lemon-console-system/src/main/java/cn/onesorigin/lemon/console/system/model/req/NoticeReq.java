package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeStatusEnum;
import cn.sticki.spel.validator.constrain.SpelFuture;
import cn.sticki.spel.validator.constrain.SpelNotEmpty;
import cn.sticki.spel.validator.constrain.SpelNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告创建活修改请求参数
 *
 * @author BruceMaa
 * @since 2025-11-11 09:25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "公告创建活修改请求参数")
public class NoticeReq {

    /**
     * 标题
     */
    @Schema(description = "标题", example = "这是公告标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 150, message = "标题长度不能超过 {max} 个字符")
    String title;

    /**
     * 内容
     */
    @Schema(description = "内容", example = "这是公告内容")
    @NotBlank(message = "内容不能为空")
    String content;

    /**
     * 分类（取值于字典 notice_type）
     */
    @Schema(description = "分类（取值于字典 notice_type）", example = "1")
    @NotBlank(message = "分类不能为空")
    @Size(max = 30, message = "分类长度不能超过 {max} 个字符")
    String type;

    /**
     * 通知范围
     */
    @Schema(description = "通知范围", example = "2")
    @NotNull(message = "通知范围不能为空")
    NoticeScopeEnum noticeScope;

    /**
     * 通知用户
     */
    @Schema(description = "通知用户", example = "[1,2,3]")
    @SpelNotEmpty(condition = "#this.noticeScope == T(cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum).USER", message = "通知用户不能为空")
    List<String> noticeUsers;

    /**
     * 通知方式
     */
    @Schema(description = "通知方式", example = "[1,2]")
    List<Integer> noticeMethods;

    /**
     * 是否定时
     */
    @Schema(description = "是否定时", example = "true")
    @NotNull(message = "是否定时不能为空")
    Boolean isTiming;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间", example = "2023-08-08 00:00:00", type = "string")
    @SpelNotNull(condition = "#this.isTiming == true", message = "定时发布时间不能为空")
    @SpelFuture(condition = "#this.isTiming == true", message = "定时发布时间不能早于当前时间")
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
}
