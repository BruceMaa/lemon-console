package cn.onesorigin.lemon.console.auth.convert;

import cn.onesorigin.lemon.console.auth.model.resp.UserInfoResp;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户信息 转换器
 *
 * @author BruceMaa
 * @since 2025-09-24 20:04
 */
@Mapper
public interface UserInfoConvert {

    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "pwdExpired", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    UserInfoResp toResp(UserDetailResp userDetailResp);
}
