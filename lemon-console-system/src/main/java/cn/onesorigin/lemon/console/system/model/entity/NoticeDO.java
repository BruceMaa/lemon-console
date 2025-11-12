package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公告 实体
 *
 * @author BruceMaa
 * @since 2025-11-11 09:16
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName(value = "sys_notice", autoResultMap = true)
public class NoticeDO extends BaseDO {

    /**
     * 标题
     */
    String title;

    /**
     * 内容
     */
    String content;

    /**
     * 分类（取值于字典 notice_type）
     */
    String type;

    /**
     * 通知范围
     */
    NoticeScopeEnum noticeScope;

    /**
     * 通知用户
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> noticeUsers;

    /**
     * 通知方式
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<Integer> noticeMethods;

    /**
     * 是否定时
     */
    Boolean isTiming;

    /**
     * 发布时间
     */
    LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    Boolean isTop;

    /**
     * 状态
     */
    NoticeStatusEnum status;
}
