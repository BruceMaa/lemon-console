package cn.onesorigin.lemon.console.common.base.service;

import top.continew.starter.extension.crud.service.CrudService;

/**
 * 业务接口基类
 *
 * @param <L> 列表响应参数类型
 * @param <D> 详情响应参数类型
 * @param <Q> 查询条件类型
 * @param <C> 创建或修改请求参数类型
 * @author BruceMaa
 * @since 2025-09-04 17:01
 */
public interface BaseService<L, D, Q, C> extends CrudService<L, D, Q, C> {
}
