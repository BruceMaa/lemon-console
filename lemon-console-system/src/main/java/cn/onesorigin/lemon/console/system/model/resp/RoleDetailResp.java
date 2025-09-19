package cn.onesorigin.lemon.console.system.model.resp;

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
@Schema(description = "角色详情响应参数")
public class RoleDetailResp extends BaseDetailResp {
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
