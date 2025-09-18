package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.DictQuery;
import cn.onesorigin.lemon.console.system.model.req.DictReq;
import cn.onesorigin.lemon.console.system.model.resp.DictResp;
import cn.onesorigin.lemon.console.system.service.DictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 字典管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-18 11:29
 */
@Tag(name = "字段管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/dict", api = {
        Api.LIST,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class DictController extends BaseController<DictService, DictResp, DictResp, DictQuery, DictReq> {
}
