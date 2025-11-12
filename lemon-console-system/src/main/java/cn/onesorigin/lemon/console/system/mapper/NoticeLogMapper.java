package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.NoticeLogDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 公告日志 数据层
 *
 * @author BruceMaa
 * @since 2025-11-11 11:02
 */
@Mapper
public interface NoticeLogMapper extends BaseMapper<NoticeLogDO> {
}
