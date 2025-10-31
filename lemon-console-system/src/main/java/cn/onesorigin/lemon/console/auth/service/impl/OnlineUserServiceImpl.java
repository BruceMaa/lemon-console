package cn.onesorigin.lemon.console.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.onesorigin.lemon.console.auth.service.OnlineUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 在线用户 业务实现
 *
 * @author BruceMaa
 * @since 2025-10-31 15:00
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineUserServiceImpl implements OnlineUserService {
    @Override
    public void kickOut(Long userId) {
        if (!StpUtil.isLogin(userId)) {
            return;
        }
        StpUtil.kickout(userId);
    }
}
