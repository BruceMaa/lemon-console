package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.system.enums.LogStatusEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 系统日志 实体
 *
 * @author BruceMaa
 * @since 2025-11-07 14:23
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_log")
public class LogDO {

    /**
     * ID
     */
    @TableId
    Long id;

    /**
     * 链路 ID
     */
    String traceId;

    /**
     * 日志描述
     */
    String description;

    /**
     * 所属模块
     */
    String module;

    /**
     * 请求 URL
     */
    String requestUrl;

    /**
     * 请求方式
     */
    String requestMethod;

    /**
     * 请求头
     */
    String requestHeaders;

    /**
     * 请求体
     */
    String requestBody;

    /**
     * 状态码
     */
    Integer statusCode;

    /**
     * 响应头
     */
    String responseHeaders;

    /**
     * 响应体
     */
    String responseBody;

    /**
     * 耗时（ms）
     */
    Long timeTaken;

    /**
     * IP
     */
    String ip;

    /**
     * IP 归属地
     */
    String address;

    /**
     * 浏览器
     */
    String browser;

    /**
     * 操作系统
     */
    String os;

    /**
     * 状态
     */
    LogStatusEnum status;

    /**
     * 错误信息
     */
    String errorMsg;

    /**
     * 创建人
     */
    Long createdBy;

    /**
     * 创建时间
     */
    LocalDateTime createdAt;
}
