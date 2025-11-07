package cn.onesorigin.lemon.console.auth.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 在线用户查询条件
 *
 * @author BruceMaa
 * @since 2025-11-07 09:30
 */
@Schema(description = "在线用户查询条件")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnlineUserQuery {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "张三")
    String nickname;

    /**
     * 客户端 ID
     */
    @Schema(description = "客户端 ID", example = "ef51c9a3e9046c4f2ea45142c8a8344a")
    String clientId;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间", example = "2023-08-08 00:00:00,2023-08-08 23:59:59")
    List<LocalDateTime> loginTime;

    /**
     * 用户 ID
     */
    @Schema(hidden = true)
    Long userId;

    /**
     * 角色 ID
     */
    @Schema(hidden = true)
    Long roleId;
}
