package cn.onesorigin.lemon.console.auth.model.resp.user;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.content.UserContextHolder;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.excel.converter.ExcelBaseEnumConverter;
import top.continew.starter.excel.converter.ExcelListConverter;
import top.continew.starter.security.crypto.annotation.FieldEncrypt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 用户详情响应参数
 *
 * @author BruceMaa
 * @since 2025-09-17 18:10
 */
@ExcelIgnoreUnannotated
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户详情响应参数")
public class UserDetailResp extends BaseDetailResp {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    @ExcelProperty(value = "用户名", order = 2)
    String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    @ExcelProperty(value = "昵称", order = 3)
    String nickname;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    @ExcelProperty(value = "状态", converter = ExcelBaseEnumConverter.class, order = 4)
    DisEnableStatusEnum status;

    /**
     * 性别
     */
    @Schema(description = "性别", example = "1")
    @ExcelProperty(value = "性别", converter = ExcelBaseEnumConverter.class, order = 5)
    GenderEnum gender;

    /**
     * 部门 ID
     */
    @Schema(description = "部门 ID", example = "5")
    @ExcelProperty(value = "部门 ID", order = 6)
    Long deptId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "测试部")
    @ExcelProperty(value = "所属部门", order = 7)
    String deptName;

    /**
     * 角色 ID 列表
     */
    @Schema(description = "角色 ID 列表", example = "2")
    @ExcelProperty(value = "角色 ID 列表", converter = ExcelListConverter.class, order = 8)
    List<Long> roleIds;

    /**
     * 角色名称列表
     */
    @Schema(description = "角色名称列表", example = "测试人员")
    @ExcelProperty(value = "角色", converter = ExcelListConverter.class, order = 9)
    List<String> roleNames;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码", example = "13811111111")
    @ExcelProperty(value = "手机号码", order = 10)
    @FieldEncrypt
    String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "123456789@qq.com")
    @ExcelProperty(value = "邮箱", order = 11)
    @FieldEncrypt
    String email;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "系统内置", example = "false")
    @ExcelProperty(value = "系统内置", order = 12)
    Boolean isSystem;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    @ExcelProperty(value = "描述", order = 13)
    String description;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @ExcelProperty(value = "头像地址", order = 14)
    String avatar;

    /**
     * 最后一次修改密码时间
     */
    @Schema(description = "最后一次修改密码时间", example = "2023-08-08 08:08:08", type = "string")
    LocalDateTime pwdResetTime;

    @Override
    public Boolean getDisabled() {
        return this.getIsSystem() || Objects.equals(this.getId(), UserContextHolder.getUserId());
    }
}
