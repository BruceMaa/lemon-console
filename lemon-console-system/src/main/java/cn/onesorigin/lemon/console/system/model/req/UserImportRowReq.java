package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;
import top.continew.starter.validation.constraints.Mobile;

/**
 * 用户导入行数据请求参数
 *
 * @author BruceMaa
 * @since 2025-09-30 11:19
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户导入行数据请求参数")
public class UserImportRowReq {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegexConstants.USERNAME, message = "用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头")
    String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = CrudValidationGroup.Create.class)
    String password;

    /**
     * 部门名称
     */
    @NotBlank(message = "所属部门不能为空")
    String deptName;

    /**
     * 角色
     */
    @NotBlank(message = "所属角色不能为空")
    String roleName;

    /**
     * 性别
     */
    String gender;

    /**
     * 邮箱
     */
    @Size(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    @Email(message = "邮箱格式不正确")
    String email;

    /**
     * 手机号码
     */
    @Mobile
    String phone;

    /**
     * 描述
     */
    String description;
}
