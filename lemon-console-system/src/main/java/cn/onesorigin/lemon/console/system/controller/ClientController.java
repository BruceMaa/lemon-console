package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.ClientQuery;
import cn.onesorigin.lemon.console.system.model.req.ClientReq;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import cn.onesorigin.lemon.console.system.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 客户端管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-28 14:43
 */
@Tag(name = "客户端管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/clients", api = {
        Api.PAGE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class ClientController extends BaseController<ClientService, ClientResp, ClientResp, ClientQuery, ClientReq> {
}
