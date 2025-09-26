package cn.onesorigin.lemon.console.common.model.req;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 状态修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-26 16:13
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "状态修改请求参数")
public class CommonStatusUpdateReq {

    /**
     * 状态
     */
    @Schema(description = "状态")
    @NotNull(message = "状态无效")
    DisEnableStatusEnum status;
}
