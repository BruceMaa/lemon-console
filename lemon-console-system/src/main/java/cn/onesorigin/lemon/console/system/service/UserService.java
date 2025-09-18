package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.UserReq;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import cn.onesorigin.lemon.console.common.base.service.BaseService;
import top.continew.starter.data.service.IService;

/**
 * 用户业务接口
 *
 * @author BruceMaa
 * @since 2025-09-17 18:22
 */
public interface UserService extends BaseService<UserResp, UserDetailResp, UserQuery, UserReq>, IService<UserDO> {
}
