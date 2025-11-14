package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.req.UserImportRowReq;
import org.mapstruct.*;
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "roleCodes", ignore = true)
    @Mapping(target = "permissionCodes", ignore = true)
    @Mapping(target = "passwordExpirationDays", ignore = true)
    @Mapping(target = "clientType", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    void update(UserDO userDO, @MappingTarget UserContext userContext);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "pwdResetTime", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "isSystem", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deptId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "gender", ignore = true)
    UserDO toDO(UserImportRowReq row);
}
