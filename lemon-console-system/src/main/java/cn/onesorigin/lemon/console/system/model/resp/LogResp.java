package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseResp;
import cn.onesorigin.lemon.console.system.enums.LogStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 系统日志响应参数
 *
 * @author BruceMaa
 * @since 2025-11-07 14:59
 */
@Schema(description = "系统日志响应参数")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogResp extends BaseResp {

    /**
     * 日志描述
     */
    @Schema(description = "日志描述", example = "新增数据")
    String description;

    /**
     * 所属模块
     */
    @Schema(description = "所属模块", example = "部门管理")
    String module;

    /**
     * 耗时（ms）
     */
    @Schema(description = "耗时（ms）", example = "58")
    Long timeTaken;

    /**
     * IP
     */
    @Schema(description = "IP")
    String ip;

    /**
     * IP 归属地
     */
    @Schema(description = "IP 归属地", example = "中国北京北京市")
    String address;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器", example = "Chrome 115.0.0.0")
    String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统", example = "Windows 10")
    String os;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    LogStatusEnum status;

    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    String errorMsg;

}
