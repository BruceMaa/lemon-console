package cn.onesorigin.lemon.console.auth.service;

import cn.onesorigin.lemon.console.auth.model.entity.UserDO;
import cn.onesorigin.lemon.console.auth.model.query.UserQuery;
import cn.onesorigin.lemon.console.auth.model.req.UserReq;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserDetailResp;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserResp;
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
