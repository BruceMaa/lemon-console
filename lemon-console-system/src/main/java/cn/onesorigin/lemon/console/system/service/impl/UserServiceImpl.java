package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.system.mapper.UserMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.UserReq;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import cn.onesorigin.lemon.console.system.service.UserService;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现
 *
 * @author BruceMaa
 * @since 2025-09-17 18:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO, UserResp, UserDetailResp, UserQuery, UserReq> implements UserService {
}
