package cn.onesorigin.lemon.console.common.api.system;

import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.util.List;

/**
 * 字典项业务API
 *
 * @author BruceMaa
 * @since 2025-09-26 15:57
 */
public interface DictItemApi {

    /**
     * 根据字典编码查询
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<LabelValueResp<?>> findByDictCode(String dictCode);
}
