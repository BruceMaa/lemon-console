package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.DictItemDO;
import cn.onesorigin.lemon.console.system.model.query.DictItemQuery;
import cn.onesorigin.lemon.console.system.model.req.DictItemReq;
import cn.onesorigin.lemon.console.system.model.resp.DictItemResp;
import top.continew.starter.data.service.IService;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.io.Serializable;
import java.util.List;

/**
 * 字典项 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-18 15:21
 */
public interface DictItemService extends BaseService<DictItemResp, DictItemResp, DictItemQuery, DictItemReq>, IService<DictItemDO> {

    /**
     * 根据字典编码查询字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<LabelValueResp<Serializable>> findByDictCode(String dictCode);

    /**
     * 根据字典ID列表删除字典项
     *
     * @param dictIds 字典ID列表
     */
    void deleteByDictIds(List<Long> dictIds);

    /**
     * 查询枚举字典名称列表
     *
     * @return 枚举字典名称列表
     */
    List<String> findEnumDictNames();
}
