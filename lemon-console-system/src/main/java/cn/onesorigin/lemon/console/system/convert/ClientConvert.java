package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.ClientDO;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author BruceMaa
 * @since 2025-09-04 18:50
 */
@Mapper
public interface ClientConvert {

    ClientConvert INSTANCE = Mappers.getMapper(ClientConvert.class);

    @Mapping(target = "modifiedUsername", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "createdUsername", ignore = true)
    ClientResp doToResp(ClientDO clientDO);
}
