package cn.onesorigin.lemon.console.system.model.resp;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.config.excel.DictExcelProperty;
import cn.onesorigin.lemon.console.common.config.excel.ExcelDictConverter;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.excel.converter.ExcelBaseEnumConverter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告详情响应参数
 *
 * @author BruceMaa
 * @since 2025-11-11 09:32
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "公告详情响应参数")
@ExcelIgnoreUnannotated
public class NoticeDetailResp extends BaseDetailResp {

    /**
     * 标题
     */
    @Schema(description = "标题", example = "这是公告标题")
    @ExcelProperty(value = "标题", order = 2)
    String title;

    /**
     * 分类（取值于字典 notice_type）
     */
    @Schema(description = "分类（取值于字典 notice_type）", example = "1")
    @ExcelProperty(value = "分类", converter = ExcelDictConverter.class, order = 3)
    @DictExcelProperty("notice_type")
    String type;

    /**
     * 内容
     */
    @Schema(description = "内容", example = "这是公告内容")
    String content;

    /**
     * 通知范围
     */
    @Schema(description = "通知范围", example = "2")
    @ExcelProperty(value = "通知范围", converter = ExcelBaseEnumConverter.class, order = 4)
    NoticeScopeEnum noticeScope;

    /**
     * 通知用户
     */
    @Schema(description = "通知用户")
    List<String> noticeUsers;

    /**
     * 通知方式
     */
    @Schema(description = "通知方式")
    List<Integer> noticeMethods;

    /**
     * 是否定时
     */
    @Schema(description = "是否定时", example = "false")
    @ExcelProperty(value = "是否定时", order = 5)
    Boolean isTiming;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间", example = "2023-08-08 00:00:00", type = "string")
    @ExcelProperty(value = "发布时间", order = 6)
    LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶", example = "false")
    @ExcelProperty(value = "是否置顶", order = 7)
    Boolean isTop;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "3")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class, order = 8)
    NoticeStatusEnum status;
}
