package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.annotation.DictModel;

/**
 * 字典实体
 *
 * @author BruceMaa
 * @since 2025-09-18 10:29
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@DictModel(valueKey = "code")
@TableName("sys_dict")
public class DictDO extends BaseDO {

    /**
     * 名称
     */
    String name;

    /**
     * 编码
     */
    String code;

    /**
     * 描述
     */
    String description;

    /**
     * 是否为系统内置数据
     */
    Boolean isSystem;
}
