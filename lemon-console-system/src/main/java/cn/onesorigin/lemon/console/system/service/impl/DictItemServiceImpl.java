package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.mapper.DictItemMapper;
import cn.onesorigin.lemon.console.system.model.entity.DictItemDO;
import cn.onesorigin.lemon.console.system.model.query.DictItemQuery;
import cn.onesorigin.lemon.console.system.model.req.DictItemReq;
import cn.onesorigin.lemon.console.system.model.resp.DictItemResp;
import cn.onesorigin.lemon.console.system.service.DictItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典项 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-18 15:23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItemDO, DictItemResp, DictItemResp, DictItemQuery, DictItemReq> implements DictItemService {
}
