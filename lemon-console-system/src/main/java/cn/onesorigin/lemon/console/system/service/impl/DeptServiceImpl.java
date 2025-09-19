package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.mapper.DeptMapper;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.query.DeptQuery;
import cn.onesorigin.lemon.console.system.model.req.DeptReq;
import cn.onesorigin.lemon.console.system.model.resp.DeptResp;
import cn.onesorigin.lemon.console.system.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 部门 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-19 10:44
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, DeptDO, DeptResp, DeptResp, DeptQuery, DeptReq> implements DeptService {
}
