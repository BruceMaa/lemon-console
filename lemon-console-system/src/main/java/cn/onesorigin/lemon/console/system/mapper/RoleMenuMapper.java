package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.RoleMenuDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 角色和菜单关联 数据层
 *
 * @author BruceMaa
 * @since 2025-09-30 09:13
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {
}
