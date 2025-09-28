package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.model.query.DictQuery;
import cn.onesorigin.lemon.console.system.model.req.DictReq;
import cn.onesorigin.lemon.console.system.model.resp.DictResp;
import cn.onesorigin.lemon.console.system.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.cache.redisson.util.RedisUtils;
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
@CrudRequestMapping(value = "/system/dicts", api = {
        Api.LIST,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class DictController extends BaseController<DictService, DictResp, DictResp, DictQuery, DictReq> {

    @Operation(summary = "清除缓存", description = "清除缓存")
    @SaCheckPermission("system:dicts:clearCache")
    @DeleteMapping("/cache/{code}")
    public void clearCache(@PathVariable String code) {
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + code);
    }
}
