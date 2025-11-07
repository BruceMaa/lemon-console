package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.LogDO;
import cn.onesorigin.lemon.console.system.model.resp.LogResp;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.data.mapper.BaseMapper;

import java.util.List;

/**
 * 系统日志 数据层
 *
 * @author BruceMaa
 * @since 2025-11-07 14:28
 */
@Mapper
public interface LogMapper extends BaseMapper<LogDO> {

    /**
     * 分页查询列表
     *
     * @param page         分页条件
     * @param queryWrapper 查询条件
     * @return 分页列表信息
     */
    IPage<LogResp> findPage(@Param("page") IPage<LogDO> page,
                            @Param(Constants.WRAPPER) QueryWrapper<LogDO> queryWrapper);

    /**
     * 查询列表
     *
     * @param queryWrapper 查询条件
     * @return 列表信息
     */
    List<LogResp> findList(@Param(Constants.WRAPPER) QueryWrapper<LogDO> queryWrapper);
}
