package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.DeptQuery;
import cn.onesorigin.lemon.console.system.model.req.DeptReq;
import cn.onesorigin.lemon.console.system.model.resp.DeptResp;
import cn.onesorigin.lemon.console.system.service.DeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 部门管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-19 10:45
 */
@Tag(name = "部门管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/depts", api = {
        Api.TREE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE,
        Api.EXPORT,
        Api.DICT_TREE
})
public class DeptController extends BaseController<DeptService, DeptResp, DeptResp, DeptQuery, DeptReq> {
}
