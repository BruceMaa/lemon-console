package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 客户端实体
 *
 * @author BruceMaa
 * @since 2025-09-04 18:05
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName(value = "sys_client", autoResultMap = true)
public class ClientDO extends BaseDO {

    /**
     * 客户端ID
     */
    String clientId;

    /**
     * 客户端类型
     */
    String clientType;

    /**
     * 认证类型
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> authType;

    /**
     * Token 最低活跃频率（单位：秒， -1：不限制）
     */
    Long activeTimeout;

    /**
     * Token 有效期（单位：秒，-1：不限制）
     */
    Long timeout;

    /**
     * 状态
     */
    DisEnableStatusEnum status;
}
