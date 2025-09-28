package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜单 转换器
 *
 * @author BruceMaa
 * @since 2025-09-25 09:20
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "createdUsername", ignore = true)
    MenuResp toResp(MenuDO menuDO);

    List<MenuResp> toResp(List<MenuDO> menuDOList);
}
