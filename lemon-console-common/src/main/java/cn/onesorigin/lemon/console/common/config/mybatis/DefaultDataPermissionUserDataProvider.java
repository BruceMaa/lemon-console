package cn.onesorigin.lemon.console.common.config.mybatis;

import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.extension.datapermission.enums.DataScope;
import top.continew.starter.extension.datapermission.model.RoleData;
import top.continew.starter.extension.datapermission.model.UserData;
import top.continew.starter.extension.datapermission.provider.DataPermissionUserDataProvider;

/**
 * 数据权限用户数据提供者
 *
 * @author BruceMaa
 * @since 2025-09-02 14:16
 */
public class DefaultDataPermissionUserDataProvider implements DataPermissionUserDataProvider {

    @Override
    public boolean isFilter() {
        return !UserContextHolder.isSuperAdminUser();
    }

    @Override
    public UserData getUserData() {
        var userContext = UserContextHolder.getContext();
        var userData = new UserData();
        userData.setUserId(userContext.getId());
        userData.setDeptId(userContext.getDeptId());
        userData.setRoles(CollUtils.mapToSet(userContext.getRoles(),
                r -> new RoleData(
                        r.getId(),
                        DataScope.valueOf(r.getDataScope().name())
                )
        ));
        return userData;
    }
}
