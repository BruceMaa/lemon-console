package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 角色响应参数
 *
 * @author BruceMaa
 * @since 2025-09-19 13:20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色响应参数")
public class RoleResp extends BaseDetailResp {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试人员")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "test")
    String code;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限", example = "5")
    DataScopeEnum dataScope;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    Integer sort;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "是否为系统内置数据", example = "false")
    Boolean isSystem;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "测试人员描述信息")
    String description;
}
