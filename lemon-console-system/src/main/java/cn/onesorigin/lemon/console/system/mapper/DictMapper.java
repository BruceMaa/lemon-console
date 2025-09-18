package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.DictDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 字典 数据层
 *
 * @author BruceMaa
 * @since 2025-09-18 10:47
 */
@Mapper
public interface DictMapper extends BaseMapper<DictDO> {
}
