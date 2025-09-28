package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseUpdateDO;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 参数实体
 *
 * @author BruceMaa
 * @since 2025-09-02 14:33
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_option")
public class OptionDO extends BaseUpdateDO {

    /**
     * 类别
     */
    OptionCategoryEnum category;

    /**
     * 名称
     */
    String name;

    /**
     * 键
     */
    String code;

    /**
     * 值
     */
    String value;

    /**
     * 默认值
     */
    String defaultValue;

    /**
     * 描述
     */
    String description;
}
