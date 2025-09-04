package cn.onesorigin.lemon.console.common.base.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;

import java.time.LocalDateTime;

/**
 * 实体类基类，创建人，创建时间，ID
 *
 * @author BruceMaa
 * @since 2025-09-02 09:44
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseCreateDO extends BaseIdDO {

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    Long createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdAt;
}
