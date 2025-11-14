package cn.onesorigin.lemon.console.medicine.mapper;

import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseInfoDO;
import org.apache.ibatis.annotations.Mapper;
import top.continew.starter.data.mapper.BaseMapper;

/**
 * 药品基本信息 数据层
 *
 * @author BruceMaa
 * @since 2025-11-13 14:44
 */
@Mapper
public interface MedicineInfoMapper extends BaseMapper<MedicineBaseInfoDO> {
}
