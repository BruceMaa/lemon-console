package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 角色 数据层
 *
 * @author BruceMaa
 * @since 2025-09-19 13:22
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
}
