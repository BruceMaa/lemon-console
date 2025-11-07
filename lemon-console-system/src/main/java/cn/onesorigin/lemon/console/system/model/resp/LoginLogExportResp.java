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
 * 登录日志导出响应参数
 *
 * @author BruceMaa
 * @since 2025-11-07 15:15
 */
@Schema(description = "登录日志导出响应参数")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginLogExportResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    @ExcelProperty(value = "ID")
    Long id;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间", example = "2023-08-08 08:08:08", type = "string")
    @ExcelProperty(value = "登录时间")
    LocalDateTime createdAt;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "张三")
    @ExcelProperty(value = "用户昵称")
    String createdUsername;

    /**
     * 登录行为
     */
    @Schema(description = "登录行为", example = "账号登录")
    @ExcelProperty(value = "登录行为")
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class)
    LogStatusEnum status;

    /**
     * 登录 IP
     */
    @Schema(description = "登录 IP")
    @ExcelProperty(value = "登录 IP")
    String ip;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点", example = "中国北京北京市")
    @ExcelProperty(value = "登录地点")
    String address;

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
