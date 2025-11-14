package cn.onesorigin.lemon.console.medicine.mapper;

import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseExtDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 药品基本信息扩展 数据层
 *
 * @author BruceMaa
 * @since 2025-11-13 15:53
 */
@Mapper
public interface MedicineExtInfoMapper extends BaseMapper<MedicineBaseExtDO> {
}
