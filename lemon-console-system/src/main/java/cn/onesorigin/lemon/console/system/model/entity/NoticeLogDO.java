package cn.onesorigin.lemon.console.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 公告日志 实体
 *
 * @author BruceMaa
 * @since 2025-11-11 11:00
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_notice_log")
public class NoticeLogDO {

    /**
     * 公告 ID
     */
    Long noticeId;

    /**
     * 用户 ID
     */
    Long userId;

    /**
     * 阅读时间
     */
    LocalDateTime readTime;
}
