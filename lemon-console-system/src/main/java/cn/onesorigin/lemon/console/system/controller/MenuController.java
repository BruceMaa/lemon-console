package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.URLUtils;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

import java.lang.reflect.Method;

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
@CrudRequestMapping(value = "/system/menus", api = {
        Api.TREE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE,
        Api.DICT_TREE
})
public class MenuController extends BaseController<MenuService, MenuResp, MenuResp, MenuQuery, MenuReq> {

    @Operation(summary = "清除缓存", description = "清除缓存")
    @SaCheckPermission("system:menus:clearCache")
    @DeleteMapping("/cache")
    public void clearCache() {
        RedisUtils.deleteByPattern(CacheConstants.ROLE_MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public void preHandle(CrudApi crudApi, Object[] args, Method targetMethod, Class<?> targetClass) throws Exception {
        super.preHandle(crudApi, args, targetMethod, targetClass);
        Api api = crudApi.value();
        if (Api.CREATE != api && Api.UPDATE != api) {
            return;
        }
        MenuReq req = (MenuReq) args[0];
        Boolean isExternal = ObjectUtil.defaultIfNull(req.getIsExternal(), false);
        String path = req.getPath();
        ValidationUtils.throwIf(Boolean.TRUE.equals(isExternal) && !URLUtils
                .isHttpUrl(path), "路由地址格式不正确，请以 http:// 或 https:// 开头");
        // 非外链菜单参数修正
        if (Boolean.FALSE.equals(isExternal)) {
            ValidationUtils.throwIf(URLUtils.isHttpUrl(path), "路由地址格式不正确");
            req.setPath(StrUtil.isBlank(path) ? path : StrUtil.prependIfMissing(path, StringConstants.SLASH));
            req.setName(StrUtil.removePrefix(req.getName(), StringConstants.SLASH));
            req.setComponent(StrUtil.removePrefix(req.getComponent(), StringConstants.SLASH));
        }
    }
}
