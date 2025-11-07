package cn.onesorigin.lemon.console.system.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.condition.ConditionOnPropertyNotNull;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.system.enums.LogStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 日志详情响应参数
 *
 * @author BruceMaa
 * @since 2025-11-07 15:01
 */
@Schema(description = "日志详情响应参数")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogDetailResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 链路 ID
     */
    @Schema(description = "链路 ID", example = "904846526308876288")
    String traceId;

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
     * 请求 URL
     */
    @Schema(description = "请求 URL")
    String requestUrl;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式", example = "POST")
    String requestMethod;

    /**
     * 请求头
     */
    @Schema(description = "请求头")
    String requestHeaders;

    /**
     * 请求体
     */
    @Schema(description = "请求体", example = "{\"name\": \"测试部\",...}")
    String requestBody;

    /**
     * 状态码
     */
    @Schema(description = "状态码", example = "200")
    Integer statusCode;

    /**
     * 响应头
     */
    @Schema(description = "响应头", example = "{\"Content-Type\": [\"application/json\"],...}")
    String responseHeaders;

    /**
     * 响应体
     */
    @Schema(description = "响应体", example = "{\"success\":true},...")
    String responseBody;

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

    /**
     * 创建人
     */
    @JsonIgnore
    @ConditionOnPropertyNotNull
    @Assemble(prop = ":createdUsername", container = ContainerConstants.USER_NICKNAME)
    private Long createdBy;

    /**
     * 创建人
     */
    @Schema(description = "创建人", example = "张三")
    private String createdUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2023-08-08 08:08:08", type = "string")
    private LocalDateTime createdAt;
}
