package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 菜单管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-18 16:47
 */
@Tag(name = "菜单管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/menu", api = {
        Api.TREE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE,
        Api.DICT_TREE
})
public class MenuController extends BaseController<MenuService, MenuResp, MenuResp, MenuQuery, MenuReq> {
}
