package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.MessageDO;
import cn.onesorigin.lemon.console.system.model.req.MessageReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 消息 转换器
 *
 * @author BruceMaa
 * @since 2025-11-12 09:11
 */
@Mapper
public interface MessageConvert {

    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "scope", ignore = true)
    @Mapping(target = "id", ignore = true)
    MessageDO toDO(MessageReq req);
}
