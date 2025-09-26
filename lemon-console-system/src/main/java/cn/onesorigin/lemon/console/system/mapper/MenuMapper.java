package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.continew.starter.data.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * 菜单 数据层
 *
 * @author BruceMaa
 * @since 2025-09-18 16:44
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
    /**
     * 根据用户ID查询权限码集合
     *
     * @param userId 用户ID
     * @return 权限码集合
     */
    @Select("""
            SELECT DISTINCT t1.permission
            FROM sys_menu AS t1
                LEFT JOIN sys_role_menu AS t2 ON t2.menu_id = t1.id
                LEFT JOIN sys_role AS t3 ON t3.id = t2.role_id
                LEFT JOIN sys_user_role AS t4 ON t4.role_id = t3.id
                LEFT JOIN sys_user AS t5 ON t5.id = t4.user_id
            WHERE t5.id = #{userId}
              AND t1.status = 1
              AND t1.permission IS NOT NULL
            """)
    Set<String> findPermissionByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询菜单集合
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    @Select("""
            SELECT t1.*
            FROM sys_menu AS t1
            LEFT JOIN sys_role_menu AS t2 ON t2.menu_id = t1.id
            LEFT JOIN sys_role AS t3 ON t3.id = t2.role_id
            WHERE t3.id = #{roleId} AND t1.status = 1
            """)
    List<MenuDO> findByRoleId(@Param("roleId") Long roleId);
}
