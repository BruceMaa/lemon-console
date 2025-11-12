package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.MessageLogDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 消息日志 数据层
 *
 * @author BruceMaa
 * @since 2025-11-12 10:35
 */
@Mapper
public interface MessageLogMapper extends BaseMapper<MessageLogDO> {
}
