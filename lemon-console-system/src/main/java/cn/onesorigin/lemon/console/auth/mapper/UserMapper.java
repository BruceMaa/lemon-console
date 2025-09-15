package cn.onesorigin.lemon.console.auth.mapper;

import cn.onesorigin.lemon.console.auth.model.entity.UserDO;
import cn.onesorigin.lemon.console.common.base.mapper.DataPermissionMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author BruceMaa
 * @since 2025-09-17 18:20
 */
@Mapper
public interface UserMapper extends DataPermissionMapper<UserDO> {
}
