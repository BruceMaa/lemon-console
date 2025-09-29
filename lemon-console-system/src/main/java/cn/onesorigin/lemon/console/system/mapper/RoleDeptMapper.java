package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.RoleDeptDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 角色和部门关联 数据层
 *
 * @author BruceMaa
 * @since 2025-09-29 17:35
 */
@Mapper
public interface RoleDeptMapper extends BaseMapper<RoleDeptDO> {
}
