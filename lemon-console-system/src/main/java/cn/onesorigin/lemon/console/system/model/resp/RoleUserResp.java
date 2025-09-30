package cn.onesorigin.lemon.console.system.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.Mapping;
import cn.crane4j.core.executor.handler.ManyToManyAssembleOperationHandler;
import cn.crane4j.core.executor.handler.OneToManyAssembleOperationHandler;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 角色关联用户响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 19:36
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "角色关联用户响应参数")
public class RoleUserResp {

    /**
     * ID
     */
    @Schema(description = "ID", example = "1")
    Long id;

    /**
     * 角色 ID
     */
    @Schema(description = "角色 ID", example = "1")
    Long roleId;

    /**
     * 用户 ID
     */
    @Schema(description = "用户 ID", example = "1")
    @Assemble(props = @Mapping(src = "roleId", ref = "roleIds"), sort = 0, container = ContainerConstants.USER_ROLE_ID_LIST, handlerType = OneToManyAssembleOperationHandler.class)
    Long userId;

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
    @Schema(description = "描述", example = "测试人员描述信息")
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
    @Assemble(props = @Mapping(src = "name", ref = "roleNames"), container = ContainerConstants.USER_ROLE_NAME_LIST, handlerType = ManyToManyAssembleOperationHandler.class)
    List<Long> roleIds;

    /**
     * 角色名称列表
     */
    @Schema(description = "角色名称列表", example = "测试人员")
    List<String> roleNames;

    public Boolean getDisabled() {
        return this.getIsSystem();
    }
}
