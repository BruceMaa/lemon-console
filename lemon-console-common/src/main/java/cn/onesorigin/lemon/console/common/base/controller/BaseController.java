package cn.onesorigin.lemon.console.common.base.controller;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.controller.AbstractCrudController;

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
    public void preHandle(CrudApi crudApi, Object[] args, Method targetMethod, Class<?> targetClass) throws Exception {
        // TODO
    }
}
