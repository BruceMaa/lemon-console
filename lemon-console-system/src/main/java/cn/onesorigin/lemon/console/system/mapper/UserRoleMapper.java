package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 用户角色关联 数据层
 *
 * @author BruceMaa
 * @since 2025-09-24 16:40
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
}
