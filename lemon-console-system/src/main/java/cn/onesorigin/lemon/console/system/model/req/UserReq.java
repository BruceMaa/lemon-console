package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.validation.CrudValidationGroup;
import top.continew.starter.validation.constraints.Mobile;

import java.util.List;

/**
 * 用户创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-17 17:53
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户创建或修改请求参数")
public class UserReq {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    @NotBlank(message = "用户名不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.USERNAME, message = "用户名长度为 4-64 个字符，支持大小写字母、数字、下划线，以字母开头")
    String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    @NotBlank(message = "昵称不能为空", groups = CrudValidationGroup.Create.class)
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    String nickname;

    /**
     * 密码（加密）
     */
    @Schema(description = "密码（加密）", example = "E7c72TH+LDxKTwavjM99W1MdI9Lljh79aPKiv3XB9MXcplhm7qJ1BJCj28yaflbdVbfc366klMtjLIWQGqb0qw==")
    @NotBlank(message = "密码不能为空", groups = CrudValidationGroup.Create.class)
    String password;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "123456789@qq.com")
    @Size(max = 255, message = "邮箱长度不能超过 {max} 个字符")
    @Email(message = "邮箱格式不正确")
    String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13811111111")
    @Mobile
    String phone;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    @NotNull(message = "性别无效", groups = CrudValidationGroup.Create.class)
    GenderEnum gender;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "5")
    @NotNull(message = "所属部门不能为空", groups = CrudValidationGroup.Create.class)
    Long deptId;

    /**
     * 所属角色
     */
    @Schema(description = "所属角色", example = "2")
    @NotEmpty(message = "所属角色不能为空", groups = CrudValidationGroup.Create.class)
    List<Long> roleIds;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    @Size(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;
}
