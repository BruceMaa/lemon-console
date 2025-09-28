package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 客户端查询条件
 *
 * @author BruceMaa
 * @since 2025-09-04 17:32
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "客户端查询条件")
public class ClientQuery {

    /**
     * 客户端类型
     */
    @Schema(description = "客户端类型")
    String clientType;

    /**
     * 状态
     */
    @Schema(description = "状态")
    DisEnableStatusEnum status;
}
