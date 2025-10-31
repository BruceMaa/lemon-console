package cn.onesorigin.lemon.console.system.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 用户角色修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:15
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户角色修改请求参数")
public class UserRoleUpdateReq {

    /**
     * 角色ID列表
     */
    @Schema(description = "所属角色")
    @NotEmpty(message = "所属角色不能为空")
    List<Long> roleIds;
}
