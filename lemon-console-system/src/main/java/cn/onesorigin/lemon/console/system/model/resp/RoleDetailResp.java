package cn.onesorigin.lemon.console.system.model.resp;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.excel.converter.ExcelBaseEnumConverter;

import java.util.List;

/**
 * 角色详情响应参数
 *
 * @author BruceMaa
 * @since 2025-09-19 11:28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExcelIgnoreUnannotated
@Schema(description = "角色详情响应参数")
public class RoleDetailResp extends BaseDetailResp {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试人员")
    @ExcelProperty(value = "名称", index = 2)
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "test")
    @ExcelProperty(value = "编码", index = 3)
    String code;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限", example = "5")
    @ExcelProperty(value = "数据权限", converter = ExcelBaseEnumConverter.class, index = 5)
    DataScopeEnum dataScope;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @ExcelProperty(value = "排序", index = 6)
    Integer sort;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "是否为系统内置数据", example = "false")
    @ExcelProperty(value = "系统内置", index = 7)
    Boolean isSystem;

    /**
     * 菜单选择是否父子节点关联
     */
    @Schema(description = "菜单选择是否父子节点关联", example = "false")
    Boolean menuCheckStrictly;

    /**
     * 部门选择是否父子节点关联
     */
    @Schema(description = "部门选择是否父子节点关联", example = "false")
    Boolean deptCheckStrictly;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "测试人员描述信息")
    @ExcelProperty(value = "描述", index = 8)
    String description;

    /**
     * 功能权限：菜单 ID 列表
     */
    @Schema(description = "功能权限：菜单 ID 列表", example = "[1000,1010,1011,1012,1013,1014]")
    List<Long> menuIds;

    /**
     * 权限范围：部门 ID 列表
     */
    @Schema(description = "权限范围：部门 ID 列表", example = "[5]")
    List<Long> deptIds;

    @Override
    public Boolean getDisabled() {
        return this.getIsSystem();
    }
}
