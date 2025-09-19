package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.DictItemQuery;
import cn.onesorigin.lemon.console.system.model.req.DictItemReq;
import cn.onesorigin.lemon.console.system.model.resp.DictItemResp;
import cn.onesorigin.lemon.console.system.service.DictItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 字典项 控制器
 *
 * @author BruceMaa
 * @since 2025-09-18 15:23
 */
@Tag(name = "字典项管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/dict-items", api = {
        Api.PAGE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class DictItemController extends BaseController<DictItemService, DictItemResp, DictItemResp, DictItemQuery, DictItemReq> {
}
