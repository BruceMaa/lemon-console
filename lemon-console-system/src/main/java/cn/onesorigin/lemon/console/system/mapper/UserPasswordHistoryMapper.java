package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.UserPasswordHistoryDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 用户历史密码 数据层
 *
 * @author BruceMaa
 * @since 2025-09-24 15:37
 */
@Mapper
public interface UserPasswordHistoryMapper extends BaseMapper<UserPasswordHistoryDO> {
}
