package cn.onesorigin.lemon.console.common.base.service.impl;

import cn.crane4j.core.support.OperateTemplate;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.common.base.service.BaseService;
import top.continew.starter.data.mapper.BaseMapper;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;
import top.continew.starter.extension.crud.service.CrudServiceImpl;

/**
 * 业务实现基类
 *
 * @param <M> 数据库操作Mapper
 * @param <T> 实体类型
 * @param <L> 列表响应参数类型
 * @param <D> 详情响应参数类型
 * @param <Q> 查询条件类型
 * @param <C> 创建或修改请求参数类型
 * @author BruceMaa
 * @since 2025-09-04 17:03
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseIdDO, L, D, Q, C> extends CrudServiceImpl<M, T, L, D, Q, C> implements BaseService<L, D, Q, C> {

    /**
     * 填充数据
     *
     * @param obj 待填充数据
     */
    @Override
    protected void fill(Object obj) {
        super.fill(obj);
        if (obj == null) {
            return;
        }
        var operateTemplate = SpringUtil.getBean(OperateTemplate.class);
        operateTemplate.execute(obj);
    }
}
