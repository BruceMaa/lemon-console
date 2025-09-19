package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 部门 数据层
 *
 * @author BruceMaa
 * @since 2025-09-19 10:42
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptDO> {
}
