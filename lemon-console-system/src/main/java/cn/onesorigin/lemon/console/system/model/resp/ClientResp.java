package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 客户端响应参数
 *
 * @author BruceMaa
 * @since 2025-09-04 17:21
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "客户端响应参数")
public class ClientResp extends BaseDetailResp {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    String clientId;

    /**
     * 客户端类型
     */
    @Schema(description = "客户端类型")
    String clientType;

    /**
     * 认证类型
     */
    @Schema(description = "认证类型")
    List<String> authType;

    /**
     * Token 最低活跃频率（单位：秒， -1：不限制）
     */
    @Schema(description = "Token 最低活跃频率（单位：秒， -1：不限制）")
    Long activeTimeout;

    /**
     * Token 有效期（单位：秒，-1：不限制）
     */
    @Schema(description = "Token 有效期（单位：秒，-1：不限制）")
    Long timeout;

    /**
     * 状态
     */
    @Schema(description = "状态")
    DisEnableStatusEnum status;
}
