package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 存储 数据层
 *
 * @author BruceMaa
 * @since 2025-09-29 09:19
 */
@Mapper
public interface StorageMapper extends BaseMapper<StorageDO> {
}
