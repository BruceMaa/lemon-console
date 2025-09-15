package cn.onesorigin.lemon.console.auth.model.resp.user;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.content.UserContextHolder;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.security.mask.annotation.JsonMask;
import top.continew.starter.security.mask.enums.MaskType;

import java.util.List;
import java.util.Objects;

/**
 * 用户响应参数
 *
 * @author BruceMaa
 * @since 2025-09-17 18:17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResp extends BaseDetailResp {

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
     * 头像地址
     */
    @Schema(description = "头像地址")
    String avatar;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "c*******@126.com")
    @JsonMask(MaskType.EMAIL)
    String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码", example = "188****8888")
    @JsonMask(MaskType.MOBILE_PHONE)
    String phone;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "是否为系统内置数据", example = "false")
    Boolean isSystem;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "张三描述信息")
    String description;

    /**
     * 部门 ID
     */
    @Schema(description = "部门 ID", example = "5")
    Long deptId;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门", example = "测试部")
    String deptName;

    /**
     * 角色 ID 列表
     */
    @Schema(description = "角色 ID 列表", example = "2")
    List<Long> roleIds;

    /**
     * 角色名称列表
     */
    @Schema(description = "角色名称列表", example = "测试人员")
    List<String> roleNames;

    @Override
    public Boolean getDisabled() {
        return this.getIsSystem() || Objects.equals(this.getId(), UserContextHolder.getUserId());
    }
}
