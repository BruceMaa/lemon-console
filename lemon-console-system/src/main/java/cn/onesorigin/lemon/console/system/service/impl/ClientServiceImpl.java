package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.convert.ClientConvert;
import cn.onesorigin.lemon.console.system.mapper.ClientMapper;
import cn.onesorigin.lemon.console.system.model.entity.ClientDO;
import cn.onesorigin.lemon.console.system.model.query.ClientQuery;
import cn.onesorigin.lemon.console.system.model.req.ClientReq;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import cn.onesorigin.lemon.console.system.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * 客户端业务实现
 *
 * @author BruceMaa
 * @since 2025-09-04 18:02
 */
@Service
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, ClientDO, ClientResp, ClientResp, ClientQuery, ClientReq> implements ClientService {
    @Override
    public ClientResp getByClientId(String clientId) {
        return baseMapper.lambdaQuery()
                .eq(ClientDO::getClientId, clientId)
                .oneOpt()
                .map(ClientConvert.INSTANCE::toResp)
                .orElse(null);
    }
}
