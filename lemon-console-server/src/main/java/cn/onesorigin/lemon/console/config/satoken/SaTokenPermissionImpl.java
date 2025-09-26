package cn.onesorigin.lemon.console.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 权限认证实现
 *
 * @author BruceMaa
 * @since 2025-09-24 19:32
 */
public class SaTokenPermissionImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        var userContext = UserContextHolder.getContext();
        return new ArrayList<>(userContext.getPermissionCodes());
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        var userContext = UserContextHolder.getContext();
        return new ArrayList<>(userContext.getRoleCodes());
    }
}
