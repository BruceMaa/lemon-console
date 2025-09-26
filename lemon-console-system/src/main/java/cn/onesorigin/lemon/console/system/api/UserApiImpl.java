package cn.onesorigin.lemon.console.system.api;

import cn.onesorigin.lemon.console.common.api.system.UserApi;
import cn.onesorigin.lemon.console.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author BruceMaa
 * @since 2025-09-25 14:27
 */
@Component
@RequiredArgsConstructor
public class UserApiImpl implements UserApi {

    private final UserService userService;

    @Override
    public String getNicknameById(Long id) {
        return userService.findNicknameById(id);
    }
}
