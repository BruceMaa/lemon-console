package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;

import java.time.LocalDateTime;

/**
 * 用户历史密码 实体
 *
 * @author BruceMaa
 * @since 2025-09-24 15:33
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@TableName("sys_user_password_history")
public class UserPasswordHistoryDO extends BaseIdDO {

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 密码
     */
    String password;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdAt;

    public UserPasswordHistoryDO(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
