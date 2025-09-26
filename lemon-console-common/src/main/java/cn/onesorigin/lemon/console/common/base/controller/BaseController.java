package cn.onesorigin.lemon.console.common.base.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sign.template.SaSignTemplate;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.common.config.crud.CrudApiPermissionPrefixCache;
import cn.onesorigin.lemon.console.common.base.service.BaseService;
import top.continew.starter.auth.satoken.autoconfigure.SaTokenExtensionProperties;
import top.continew.starter.core.util.ServletUtils;
import top.continew.starter.core.util.SpringWebUtils;
import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.controller.AbstractCrudController;
import top.continew.starter.extension.crud.enums.Api;

import java.lang.reflect.Method;

/**
 * 控制器基类
 *
 * @param <S> 业务接口
 * @param <L> 列表类型
 * @param <D> 详情类型
 * @param <Q> 查询条件类型
 * @param <C> 创建或修改请求参数类型
 * @author BruceMaa
 * @since 2025-09-17 18:31
 */

public class BaseController<S extends BaseService<L, D, Q, C>, L, D, Q, C> extends AbstractCrudController<S, L, D, Q, C> {
    @Override
    public void preHandle(CrudApi crudApi, Object[] args, Method targetMethod, Class<?> targetClass) {
        // 忽略带 sign 请求权限校验
        var saRequest = SaHolder.getRequest();
        var paramNames = saRequest.getParamNames();
        if (paramNames.stream().anyMatch(SaSignTemplate.sign::equals)) {
            return;
        }
        // 忽略接口类和方法上带 @SaIgnore 注解的权限校验
        if (AnnotationUtil.hasAnnotation(targetMethod, SaIgnore.class)
                || AnnotationUtil.hasAnnotation(targetClass, SaIgnore.class)) {
            return;
        }
        // 忽略排除（放行）路径
        var saTokenExtensionProperties = SpringUtil.getBean(SaTokenExtensionProperties.class);
        if (saTokenExtensionProperties.isEnabled()) {
            var excludes = saTokenExtensionProperties.getSecurity().getExcludes();
            if (SpringWebUtils.isMatch(ServletUtils.getRequestPath(), excludes)) {
                return;
            }
        }
        // 不需要校验 DICT、DICT_TREE接口权限
        if (Api.DICT.equals(crudApi.value()) || Api.DICT_TREE.equals(crudApi.value())) {
            return;
        }

        // 校验权限
        // 校验权限，例如：创建用户接口（POST /system/user） => 校验 system:user:create 权限
        String permissionPrefix = CrudApiPermissionPrefixCache.get(targetClass);
        String apiName = getApiName(crudApi.value());
        StpUtil.checkPermission("%s:%s".formatted(permissionPrefix, apiName.toLowerCase()));
    }

    public static String getApiName(Api api) {
        return switch (api) {
            case PAGE, TREE, LIST -> Api.LIST.name();
            case DELETE, BATCH_DELETE -> Api.DELETE.name();
            default -> api.name();
        };
    }
}
