package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.model.resp.RoleUserResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 用户角色关联 数据层
 *
 * @author BruceMaa
 * @since 2025-09-24 16:40
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    /**
     * 分页查询列表
     *
     * @param page         分页条件
     * @param queryWrapper 查询条件
     * @return 分页列表信息
     */
    IPage<RoleUserResp> selectUserPage(@Param("page") IPage<UserRoleDO> page,
                                       @Param(Constants.WRAPPER) QueryWrapper<UserRoleDO> queryWrapper);
}
