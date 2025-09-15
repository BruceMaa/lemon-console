package cn.onesorigin.lemon.console.common.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.data.mapper.BaseMapper;
import top.continew.starter.extension.datapermission.annotation.DataPermission;

import java.io.Serializable;
import java.util.List;

/**
 * 数据权限 Mapper 基类
 *
 * @author BruceMaa
 * @since 2025-09-04 16:55
 */
public interface DataPermissionMapper<T> extends BaseMapper<T> {

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类，可以为null
     * @return 全部记录
     */
    @DataPermission
    @Override
    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类，可以为null
     * @return 全部记录
     */
    @DataPermission
    @Override
    List<T> selectList(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据ID删除
     *
     * @param id id
     * @return 删除个数
     */
    @DataPermission
    @Override
    int deleteById(@Param("id") Serializable id);
}
