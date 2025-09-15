package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.ClientDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 客户端 Mapper
 *
 * @author BruceMaa
 * @since 2025-09-04 18:04
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientDO> {
}
