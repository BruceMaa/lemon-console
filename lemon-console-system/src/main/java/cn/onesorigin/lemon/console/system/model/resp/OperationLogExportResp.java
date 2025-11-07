package cn.onesorigin.lemon.console.system.model.resp;

import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.system.enums.LogStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.excel.converter.ExcelBaseEnumConverter;

import java.time.LocalDateTime;

/**
 * 操作日志导出响应参数
 *
 * @author BruceMaa
 * @since 2025-11-07 15:17
 */
@Schema(description = "操作日志导出响应参数")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperationLogExportResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    @ExcelProperty(value = "ID")
    Long id;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间", example = "2023-08-08 08:08:08", type = "string")
    @ExcelProperty(value = "操作时间")
    LocalDateTime createdAt;

    /**
     * 操作人
     */
    @Schema(description = "操作人", example = "张三")
    @ExcelProperty(value = "操作人")
    String createdUsername;

    /**
     * 操作内容
     */
    @Schema(description = "操作内容", example = "账号登录")
    @ExcelProperty(value = "操作内容")
    String description;

    /**
     * 所属模块
     */
    @Schema(description = "所属模块", example = "部门管理")
    @ExcelProperty(value = "所属模块")
    String module;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class)
    LogStatusEnum status;

    /**
     * 操作 IP
     */
    @Schema(description = "操作 IP")
    @ExcelProperty(value = "操作 IP")
    String ip;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点", example = "中国北京北京市")
    @ExcelProperty(value = "操作地点")
    String address;

    /**
     * 耗时（ms）
     */
    @Schema(description = "耗时（ms）", example = "58")
    @ExcelProperty(value = "耗时（ms）")
    Long timeTaken;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器", example = "Chrome 115.0.0.0")
    @ExcelProperty(value = "浏览器")
    String browser;

    /**
     * 终端系统
     */
    @Schema(description = "终端系统", example = "Windows 10")
    @ExcelProperty(value = "终端系统")
    String os;
}
