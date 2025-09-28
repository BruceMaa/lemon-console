package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.model.entity.DictItemDO;
import com.alicp.jetcache.anno.Cached;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.continew.starter.data.mapper.BaseMapper;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.io.Serializable;
import java.util.List;

/**
 * 字典项 数据层
 *
 * @author BruceMaa
 * @since 2025-09-18 15:19
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItemDO> {

    /**
     * 根据字典编码查询字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    @Select("""
            SELECT t1.label, t1.value, t1.color AS extra
            FROM sys_dict_item AS t1
            LEFT JOIN sys_dict AS t2 ON t1.dict_id = t2.id
            WHERE t1.status = 1 AND t2.code = #{dictCode}
            ORDER BY t1.sort
            """)
    @Cached(key = "#dictCode", name = CacheConstants.DICT_KEY_PREFIX)
    List<LabelValueResp<Serializable>> findByDictCode(@Param("dictCode") String dictCode);
}
