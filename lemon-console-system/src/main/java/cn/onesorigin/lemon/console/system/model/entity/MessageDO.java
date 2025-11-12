package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.system.enums.MessageTypeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息 实体
 *
 * @author BruceMaa
 * @since 2025-11-11 13:56
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_message")
public class MessageDO {

    /**
     * ID
     */
    @TableId
    Long id;

    /**
     * 标题
     */
    String title;

    /**
     * 内容
     */
    String content;

    /**
     * 类型
     */
    MessageTypeEnum type;

    /**
     * 跳转路径
     */
    String path;

    /**
     * 通知范围
     */
    NoticeScopeEnum scope;

    /**
     * 通知用户
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> users;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createdAt;
}
