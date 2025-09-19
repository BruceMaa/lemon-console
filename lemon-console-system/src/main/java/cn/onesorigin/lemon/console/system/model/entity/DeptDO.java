package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 部门 实体
 *
 * @author BruceMaa
 * @since 2025-09-19 10:36
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_dept")
public class DeptDO extends BaseDO {

    /**
     * 名称
     */
    String name;

    /**
     * 上级部门 ID
     */
    Long parentId;

    /**
     * 祖级列表
     */
    String ancestors;

    /**
     * 描述
     */
    String description;

    /**
     * 排序
     */
    Integer sort;

    /**
     * 状态
     */
    DisEnableStatusEnum status;

    /**
     * 是否为系统内置数据
     */
    Boolean isSystem;
}
