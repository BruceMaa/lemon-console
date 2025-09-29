package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.common.model.req.CommonStatusUpdateReq;
import cn.onesorigin.lemon.console.system.model.query.StorageQuery;
import cn.onesorigin.lemon.console.system.model.req.StorageReq;
import cn.onesorigin.lemon.console.system.model.resp.StorageResp;
import cn.onesorigin.lemon.console.system.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 存储管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-29 09:24
 */
@Tag(name = "存储管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/storages", api = {
        Api.LIST,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class StorageController extends BaseController<StorageService, StorageResp, StorageResp, StorageQuery, StorageReq> {

    @Operation(summary = "修改状态", description = "修改状态")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:storages:updateStatus")
    @PatchMapping({"/{id}/status"})
    public void updateStatus(@RequestBody @Valid CommonStatusUpdateReq req, @PathVariable("id") Long id) {
        baseService.updateStatus(req, id);
    }

    @Operation(summary = "设为默认存储", description = "设为默认存储")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:storages:setDefault")
    @PatchMapping({"/{id}/default"})
    public void setDefault(@PathVariable("id") Long id) {
        baseService.setDefaultStorage(id);
    }
}
