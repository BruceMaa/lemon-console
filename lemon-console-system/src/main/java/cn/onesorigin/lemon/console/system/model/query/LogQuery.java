package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志查询条件
 *
 * @author BruceMaa
 * @since 2025-11-07 14:57
 */
@Schema(description = "系统日志查询条件")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogQuery {

    /**
     * 日志描述
     */
    @Schema(description = "日志描述", example = "新增数据")
    String description;

    /**
     * 所属模块
     */
    @Schema(description = "所属模块", example = "所属模块")
    String module;

    /**
     * IP
     */
    @Schema(description = "IP")
    String ip;

    /**
     * 操作人
     */
    @Schema(description = "操作人", example = "admin")
    String createdUsername;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间", example = "2023-08-08 00:00:00,2023-08-08 23:59:59")
    @Size(max = 2, message = "操作时间必须是一个范围")
    List<LocalDateTime> createdAt;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;
}
