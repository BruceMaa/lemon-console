package cn.onesorigin.lemon.console.auth.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.AssembleMethod;
import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.onesorigin.lemon.console.auth.service.OnlineUserService;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 在线用户响应参数
 *
 * @author BruceMaa
 * @since 2025-11-07 09:33
 */
@Schema(description = "在线用户响应参数")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OnlineUserResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    @Assemble(prop = ":nickname", container = ContainerConstants.USER_NICKNAME)
    Long id;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    @AssembleMethod(prop = ":lastActiveTime", targetType = OnlineUserService.class, method = @ContainerMethod(bindMethod = "getLastActiveTime", type = MappingType.ORDER_OF_KEYS))
    String token;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    String nickname;

    /**
     * 客户端类型
     */
    @Schema(description = "客户端类型", example = "PC")
    String clientType;

    /**
     * 客户端 ID
     */
    @Schema(description = "客户端 ID", example = "ef51c9a3e9046c4f2ea45142c8a8344a")
    String clientId;

    /**
     * 登录 IP
     */
    @Schema(description = "登录 IP", example = "127.0.0.1")
    String ip;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点", example = "中国北京北京市")
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
     * 登录时间
     */
    @Schema(description = "登录时间", example = "2023-08-08 08:08:08", type = "string")
    LocalDateTime loginTime;

    /**
     * 最后活跃时间
     */
    @Schema(description = "最后活跃时间", example = "2023-08-08 08:08:08", type = "string")
    LocalDateTime lastActiveTime;
}
