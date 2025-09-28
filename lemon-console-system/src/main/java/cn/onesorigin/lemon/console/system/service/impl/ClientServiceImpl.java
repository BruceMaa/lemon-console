package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.convert.ClientConvert;
import cn.onesorigin.lemon.console.system.mapper.ClientMapper;
import cn.onesorigin.lemon.console.system.model.entity.ClientDO;
import cn.onesorigin.lemon.console.system.model.query.ClientQuery;
import cn.onesorigin.lemon.console.system.model.req.ClientReq;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import cn.onesorigin.lemon.console.system.service.ClientService;
import org.springframework.stereotype.Service;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.validation.CheckUtils;

import java.util.List;

/**
 * 客户端业务实现
 *
 * @author BruceMaa
 * @since 2025-09-04 18:02
 */
@Service
public class ClientServiceImpl extends BaseServiceImpl<ClientMapper, ClientDO, ClientResp, ClientResp, ClientQuery, ClientReq> implements ClientService {

    @Override
    public void beforeCreate(ClientReq req) {
        req.setClientId(SecureUtil.md5(Base64.encode(IdUtil.fastSimpleUUID())
                .replace(StringConstants.SLASH, StringConstants.EMPTY)
                .replace(StringConstants.PLUS, StringConstants.EMPTY)));
    }

    @Override
    public void beforeDelete(List<Long> ids) {
        // TODO 如果还存在在线用户，则不能删除
        //OnlineUserQuery query = new OnlineUserQuery();
        //for (Long id : ids) {
        //    ClientDO client = this.getById(id);
        //    query.setClientId(client.getClientId());
        //    CheckUtils.throwIfNotEmpty(onlineUserService.list(query), "客户端 [{}] 还存在在线用户，不允许删除", client.getClientId());
        //}
    }

    @Override
    public ClientResp getByClientId(String clientId) {
        return baseMapper.lambdaQuery()
                .eq(ClientDO::getClientId, clientId)
                .oneOpt()
                .map(ClientConvert.INSTANCE::toResp)
                .orElse(null);
    }
}
