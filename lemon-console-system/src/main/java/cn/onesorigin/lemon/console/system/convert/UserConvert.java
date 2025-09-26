package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 用户信息 转换器
 *
 * @author BruceMaa
 * @since 2025-09-24 16:06
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "roleCodes", ignore = true)
    @Mapping(target = "permissionCodes", ignore = true)
    @Mapping(target = "passwordExpirationDays", ignore = true)
    @Mapping(target = "clientType", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    void copyField(UserDO userDO, @MappingTarget UserContext userContext);
}
