package cn.onesorigin.lemon.console.medicine.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.medicine.model.entity.MedicineBaseInfoDO;
import cn.onesorigin.lemon.console.medicine.model.query.MedicineInfoQuery;
import cn.onesorigin.lemon.console.medicine.model.req.MedicineInfoReq;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoDetailResp;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoResp;
import top.continew.starter.data.service.IService;

/**
 * 药品基本信息 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-13 14:46
 */
public interface MedicineInfoService extends BaseService<MedicineInfoResp, MedicineInfoDetailResp, MedicineInfoQuery, MedicineInfoReq>, IService<MedicineBaseInfoDO> {
}
