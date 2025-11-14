package cn.onesorigin.lemon.console.medicine.convert;

import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseExtDO;
import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseInfoDO;
import cn.onesorigin.lemon.console.medicine.model.req.MedicineInfoReq;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoDetailResp;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 药品基本信息 转换器
 *
 * @author BruceMaa
 * @since 2025-11-13 15:55
 */
@Mapper
public interface MedicineInfoConvert {

    MedicineInfoConvert INSTANCE = Mappers.getMapper(MedicineInfoConvert.class);

    @Mapping(target = "id", ignore = true)
    MedicineBaseExtDO toExtDO(MedicineInfoReq req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void toExtDO(MedicineInfoReq req, @MappingTarget MedicineBaseExtDO extDO);

    @Mapping(target = "modifiedUsername", ignore = true)
    @Mapping(target = "disabled", ignore = true)
    @Mapping(target = "createdUsername", ignore = true)
    @Mapping(target = "id", source = "medicineBaseInfoDO.id")
    MedicineInfoDetailResp toDetailResp(MedicineBaseInfoDO medicineBaseInfoDO, MedicineBaseExtDO medicineBaseExtDO);
}
