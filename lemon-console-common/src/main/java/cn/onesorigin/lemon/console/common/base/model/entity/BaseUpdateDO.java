package cn.onesorigin.lemon.console.common.base.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;

import java.time.LocalDateTime;

/**
 * 实体类基类，ID，更新人，更新时间
 *
 * @author BruceMaa
 * @since 2025-09-02 09:44
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseUpdateDO extends BaseIdDO {

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Long modifiedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime modifiedAt;

}
