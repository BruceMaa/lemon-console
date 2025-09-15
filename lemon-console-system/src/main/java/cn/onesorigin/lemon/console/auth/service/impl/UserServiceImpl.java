package cn.onesorigin.lemon.console.auth.service.impl;

import cn.onesorigin.lemon.console.auth.mapper.UserMapper;
import cn.onesorigin.lemon.console.auth.model.entity.UserDO;
import cn.onesorigin.lemon.console.auth.model.query.UserQuery;
import cn.onesorigin.lemon.console.auth.model.req.UserReq;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserDetailResp;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserResp;
import cn.onesorigin.lemon.console.auth.service.UserService;
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
