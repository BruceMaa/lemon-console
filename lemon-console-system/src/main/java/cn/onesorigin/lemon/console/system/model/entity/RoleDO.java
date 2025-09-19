package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DataScopeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.annotation.DictModel;

/**
 * 角色 实体
 *
 * @author BruceMaa
 * @since 2025-09-19 11:23
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@DictModel
@TableName("sys_role")
public class RoleDO extends BaseDO {
    /**
     * 名称
     */
    String name;

    /**
     * 编码
     */
    String code;

    /**
     * 数据权限
     */
    DataScopeEnum dataScope;

    /**
     * 描述
     */
    String description;

    /**
     * 排序
     */
    Integer sort;

    /**
     * 是否为系统内置数据
     */
    Boolean isSystem;

    /**
     * 菜单选择是否父子节点关联
     */
    Boolean menuCheckStrictly;

    /**
     * 部门选择是否父子节点关联
     */
    Boolean deptCheckStrictly;
}
