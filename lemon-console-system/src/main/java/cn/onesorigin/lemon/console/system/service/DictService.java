package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.DictDO;
import cn.onesorigin.lemon.console.system.model.query.DictQuery;
import cn.onesorigin.lemon.console.system.model.req.DictReq;
import cn.onesorigin.lemon.console.system.model.resp.DictResp;
import top.continew.starter.data.service.IService;

/**
 * 字典 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-18 10:48
 */
public interface DictService extends BaseService<DictResp, DictResp, DictQuery, DictReq>, IService<DictDO> {
}
