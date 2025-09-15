package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.query.ClientQuery;
import cn.onesorigin.lemon.console.system.model.req.ClientReq;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import jakarta.validation.constraints.NotBlank;

/**
 * 客户端业务接口
 *
 * @author BruceMaa
 * @since 2025-09-04 17:08
 */
public interface ClientService extends BaseService<ClientResp, ClientResp, ClientQuery, ClientReq> {
    /**
     * 根据客户端ID查询
     *
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    ClientResp getByClientId(@NotBlank(message = "客户端ID不能为空") String clientId);
}
