package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-19 11:25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色创建或修改请求参数")
public class RoleReq {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "测试人员")
    @NotBlank(message = "名称不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "名称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "test")
    @NotBlank(message = "编码不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.GENERAL_CODE, message = "编码长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头")
    String code;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    @Min(value = 1, message = "排序最小值为 {value}")
    Integer sort;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "测试人员描述信息")
    @Size(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限", example = "5")
    DataScopeEnum dataScope;

    /**
     * 权限范围：部门 ID 列表
     */
    @Schema(description = "权限范围：部门 ID 列表", example = "[5]")
    List<Long> deptIds = new ArrayList<>();

    /**
     * 部门选择是否父子节点关联
     */
    @Schema(description = "部门选择是否父子节点关联", example = "false")
    Boolean deptCheckStrictly;
}
