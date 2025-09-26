package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.common.base.mapper.DataPermissionMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.extension.datapermission.annotation.DataPermission;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author BruceMaa
 * @since 2025-09-17 18:20
 */
@Mapper
public interface UserMapper extends DataPermissionMapper<UserDO> {

    /**
     * 分页查询用户列表
     *
     * @param page         分页条件
     * @param queryWrapper 查询条件
     * @return 分页列表信息
     */
    @DataPermission(tableAlias = "t1")
    IPage<UserDetailResp> findUserPage(@Param("page") IPage<UserDO> page,
                                       @Param(Constants.WRAPPER) QueryWrapper<UserDO> queryWrapper);

    /**
     * 查询列表
     *
     * @param queryWrapper 查询条件
     * @return 列表信息
     */
    @DataPermission(tableAlias = "t1")
    List<UserDetailResp> findUserList(@Param(Constants.WRAPPER) QueryWrapper<UserDO> queryWrapper);
}
