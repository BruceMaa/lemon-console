package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 消息日志 实体
 *
 * @author BruceMaa
 * @since 2025-11-12 10:34
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_message_log")
public class MessageLogDO {

    /**
     * 消息ID
     */
    Long messageId;

    /**
     * 用户ID
     */
    Long userId;

    /**
     * 读取时间
     */
    LocalDateTime readTime;
}
