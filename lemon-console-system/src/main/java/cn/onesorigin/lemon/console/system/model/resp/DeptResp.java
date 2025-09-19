package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.annotation.TreeField;

/**
 * 部门响应参数
 *
 * @author BruceMaa
 * @since 2025-09-19 10:40
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TreeField(value = "id", nameKey = "name")
@Schema(description = "部门响应参数")
public class DeptResp extends BaseDetailResp {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试部")
    String name;

    /**
     * 上级部门 ID
     */
    @Schema(description = "上级部门 ID", example = "2")
    Long parentId;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

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
    @Schema(description = "描述", example = "测试部描述信息")
    String description;
}
