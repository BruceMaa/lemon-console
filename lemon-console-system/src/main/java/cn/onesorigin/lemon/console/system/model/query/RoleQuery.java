package cn.onesorigin.lemon.console.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

import java.util.List;

/**
 * 角色查询条件
 *
 * @author BruceMaa
 * @since 2025-09-19 11:27
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色查询条件")
public class RoleQuery {
    /**
     * 关键词
     */
    @Schema(description = "关键词", example = "测试人员")
    @Query(columns = {"name", "code", "description"}, type = QueryType.LIKE)
    String description;

    /**
     * 排除的编码列表
     */
    @Schema(description = "排除的编码列表", example = "[super_admin,tenant_admin]")
    @Query(columns = "code", type = QueryType.NOT_IN)
    List<String> excludeRoleCodes;
}
