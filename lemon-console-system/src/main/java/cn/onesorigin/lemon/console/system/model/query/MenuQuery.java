package cn.onesorigin.lemon.console.system.model.query;

import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.data.annotation.Query;
import top.continew.starter.data.enums.QueryType;

import java.util.List;

/**
 * 菜单查询条件
 *
 * @author BruceMaa
 * @since 2025-09-18 16:25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuQuery {

    /**
     * 标题
     */
    @Schema(description = "标题", example = "用户管理")
    @Query(type = QueryType.LIKE)
    String title;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    public MenuQuery(DisEnableStatusEnum status) {
        this.status = status;
    }

    /**
     * 排除的菜单 ID 列表
     */
    @Schema(hidden = true, description = "排除的菜单 ID 列表", example = "[9000]")
    @Query(columns = "id", type = QueryType.NOT_IN)
    List<Long> excludeMenuIdList;
}
