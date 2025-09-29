package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import cn.onesorigin.lemon.console.system.model.req.StorageReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 存储 转换器
 *
 * @author BruceMaa
 * @since 2025-09-29 09:47
 */
@Mapper
public interface StorageConvert {

    StorageConvert INSTANCE = Mappers.getMapper(StorageConvert.class);

    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    StorageDO toDO(StorageReq req);
}
