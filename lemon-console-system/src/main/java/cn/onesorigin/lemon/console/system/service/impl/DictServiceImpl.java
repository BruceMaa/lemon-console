package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.mapper.DictMapper;
import cn.onesorigin.lemon.console.system.model.entity.DictDO;
import cn.onesorigin.lemon.console.system.model.query.DictQuery;
import cn.onesorigin.lemon.console.system.model.req.DictReq;
import cn.onesorigin.lemon.console.system.model.resp.DictResp;
import cn.onesorigin.lemon.console.system.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-18 10:49
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, DictDO, DictResp, DictResp, DictQuery, DictReq> implements DictService {
}
