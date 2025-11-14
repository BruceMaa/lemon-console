package cn.onesorigin.lemon.console.medicine.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.medicine.model.query.MedicineInfoQuery;
import cn.onesorigin.lemon.console.medicine.model.req.MedicineInfoReq;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoDetailResp;
import cn.onesorigin.lemon.console.medicine.model.resp.MedicineInfoResp;
import cn.onesorigin.lemon.console.medicine.service.MedicineInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 药品基本信息 控制器
 *
 * @author BruceMaa
 * @since 2025-11-13 14:59
 */
@Tag(name = "药品基本信息管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/medicine/infos", api = {
        Api.CREATE,
        Api.PAGE,
        Api.GET,
        Api.UPDATE,
        Api.BATCH_DELETE,
})
public class MedicineInfoController extends BaseController<MedicineInfoService, MedicineInfoResp, MedicineInfoDetailResp, MedicineInfoQuery, MedicineInfoReq> {
}
