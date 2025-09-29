package cn.onesorigin.lemon.console.system.model.resp;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.excel.converter.ExcelBaseEnumConverter;
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
@ExcelIgnoreUnannotated
@Schema(description = "部门响应参数")
public class DeptResp extends BaseDetailResp {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试部")
    @ExcelProperty(value = "名称", order = 2)
    String name;

    /**
     * 上级部门 ID
     */
    @Schema(description = "上级部门 ID", example = "2")
    @ExcelProperty(value = "上级部门 ID", order = 3)
    Long parentId;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class, order = 5)
    DisEnableStatusEnum status;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @ExcelProperty(value = "排序", order = 6)
    Integer sort;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "是否为系统内置数据", example = "false")
    @ExcelProperty(value = "系统内置", order = 7)
    Boolean isSystem;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "测试部描述信息")
    @ExcelProperty(value = "描述", order = 8)
    String description;
}
