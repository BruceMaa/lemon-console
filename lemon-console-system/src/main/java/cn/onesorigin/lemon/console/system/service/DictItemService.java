package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.DictItemDO;
import cn.onesorigin.lemon.console.system.model.query.DictItemQuery;
import cn.onesorigin.lemon.console.system.model.req.DictItemReq;
import cn.onesorigin.lemon.console.system.model.resp.DictItemResp;
import top.continew.starter.data.service.IService;

/**
 * 字典项 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-18 15:21
 */
public interface DictItemService extends BaseService<DictItemResp, DictItemResp, DictItemQuery, DictItemReq>, IService<DictItemDO> {
}
