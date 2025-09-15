package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 客户端创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-04 17:57
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "客户端创建或修改请求参数")
public class ClientReq {

    /**
     * 客户端类型
     */
    @Schema(description = "客户端类型")
    @NotBlank(message = "客户端类型不能为空")
    @Size(max = 32, message = "客户端类型长度不能超过32个字符")
    String clientType;

    /**
     * 认证类型
     */
    @Schema(description = "认证类型")
    @NotEmpty(message = "认证类型不能为空")
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

    /**
     * 客户端 ID
     */
    @Schema(hidden = true)
    String clientId;
}
