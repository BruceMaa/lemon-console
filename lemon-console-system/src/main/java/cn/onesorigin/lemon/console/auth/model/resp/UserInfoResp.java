package cn.onesorigin.lemon.console.auth.model.resp;

import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.security.mask.annotation.JsonMask;
import top.continew.starter.security.mask.enums.MaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 登录用户响应参数
 *
 * @author BruceMaa
 * @since 2025-09-24 20:01
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "登录用户响应参数")
public class UserInfoResp {
    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    GenderEnum gender;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @JsonMask(MaskType.EMAIL)
    String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码", example = "188****8888")
    @JsonMask(MaskType.MOBILE_PHONE)
    String phone;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    String avatar;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    String description;

    /**
     * 最后一次修改密码时间
     */
    @Schema(description = "最后一次修改密码时间", example = "2023-08-08 08:08:08", type = "string")
    LocalDateTime pwdResetTime;

    /**
     * 密码是否已过期
     */
    @Schema(description = "密码是否已过期", example = "true")
    Boolean pwdExpired;

    /**
     * 创建时间
     */
    @JsonIgnore
    LocalDateTime createdAt;

    /**
     * 注册日期
     */
    @Schema(description = "注册日期", example = "2023-08-08")
    LocalDate registrationDate;

    /**
     * 部门 ID
     */
    @Schema(description = "部门 ID", example = "1")
    Long deptId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "测试部")
    String deptName;

    /**
     * 权限码集合
     */
    @Schema(description = "权限码集合", example = "[\"system:user:list\",\"system:user:add\"]")
    Set<String> permissions;

    /**
     * 角色编码集合
     */
    @Schema(description = "角色编码集合", example = "[\"test\"]")
    Set<String> roles;

    /**
     * 角色名称列表
     */
    @Schema(description = "角色名称列表", example = "测试人员")
    List<String> roleNames;

    public LocalDate getRegistrationDate() {
        return createdAt.toLocalDate();
    }
}
