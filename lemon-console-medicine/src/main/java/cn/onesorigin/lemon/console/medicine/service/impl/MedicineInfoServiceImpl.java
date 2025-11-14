package cn.onesorigin.lemon.console.medicine.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.medicine.convert.MedicineInfoConvert;
import cn.onesorigin.lemon.console.medicine.mapper.MedicineExtInfoMapper;
import cn.onesorigin.lemon.console.medicine.mapper.MedicineInfoMapper;
import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseInfoDO;
import cn.onesorigin.lemon.console.medicine.model.query.MedicineInfoQuery;
import cn.onesorigin.lemon.console.medicine.model.req.MedicineInfoReq;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoDetailResp;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoResp;
import cn.onesorigin.lemon.console.medicine.service.MedicineInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品基本信息 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-13 14:55
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MedicineInfoServiceImpl extends BaseServiceImpl<MedicineInfoMapper, MedicineBaseInfoDO, MedicineInfoResp, MedicineInfoDetailResp, MedicineInfoQuery, MedicineInfoReq> implements MedicineInfoService {

    private final MedicineExtInfoMapper extInfoMapper;

    @Override
    protected void afterCreate(MedicineInfoReq req, MedicineBaseInfoDO entity) {
        var medicineBaseExtDO = MedicineInfoConvert.INSTANCE.toExtDO(req);
        medicineBaseExtDO.setId(entity.getId());
        extInfoMapper.insert(medicineBaseExtDO);
    }

    @Override
    protected void afterUpdate(MedicineInfoReq req, MedicineBaseInfoDO entity) {
        var medicineBaseExtDO = extInfoMapper.selectById(entity.getId());
        MedicineInfoConvert.INSTANCE.toExtDO(req, medicineBaseExtDO);
        extInfoMapper.updateById(medicineBaseExtDO);
    }

    @Override
    protected void afterDelete(List<Long> ids) {
        extInfoMapper.deleteByIds(ids);
    }

    @Override
    public MedicineInfoDetailResp get(Long id) {
        var medicineBaseInfoDO = super.getById(id);
        var medicineBaseExtDO = extInfoMapper.selectById(id);
        var detail = MedicineInfoConvert.INSTANCE.toDetailResp(medicineBaseInfoDO, medicineBaseExtDO);
        super.fill(detail);
        return detail;
    }
}
