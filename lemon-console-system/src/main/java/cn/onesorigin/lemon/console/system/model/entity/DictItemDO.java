package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 字典项 实体
 *
 * @author BruceMaa
 * @since 2025-09-18 15:17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_dict_item")
public class DictItemDO extends BaseDO {

    /**
     * 字典ID
     */
    Long dictId;

    /**
     * 标签
     */
    String label;

    /**
     * 值
     */
    String value;

    /**
     * 标签颜色
     */
    String color;

    /**
     * 排序
     */
    Integer sort;

    /**
     * 描述
     */
    String description;

    /**
     * 状态
     */
    DisEnableStatusEnum status;
}
