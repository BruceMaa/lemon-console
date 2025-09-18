package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 菜单 数据层
 *
 * @author BruceMaa
 * @since 2025-09-18 16:44
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
}
