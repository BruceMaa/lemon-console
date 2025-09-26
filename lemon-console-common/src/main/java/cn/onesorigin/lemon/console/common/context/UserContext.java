package cn.onesorigin.lemon.console.common.context;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.common.enums.RoleCodeEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.util.CollUtils;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户上下文
 *
 * @author BruceMaa
 * @since 2025-09-02 09:59
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserContext {

    /**
     * 用户ID
     */
    Long id;

    /**
     * 用户名
     */
    String username;

    /**
     * 部门ID
     */
    Long deptId;

    /**
     * 最后一次修改密码时间
     */
    LocalDateTime pwdResetTime;

    /**
     * 登录时系统设置的密码过期天数
     */
    Integer passwordExpirationDays;

    /**
     * 权限码集合
     */
    Set<String> permissionCodes;

    /**
     * 角色码集合
     */
    Set<String> roleCodes;

    /**
     * 角色集合
     */
    Set<RoleContext> roles;

    /**
     * 客户端类型
     */
    String clientType;

    /**
     * 客户端ID
     */
    String clientId;

    public UserContext(Set<String> permissionCodes, Set<RoleContext> roles, Integer passwordExpirationDays) {
        this.permissionCodes = permissionCodes;
        this.setRoles(roles);
        this.passwordExpirationDays = passwordExpirationDays;
    }

    /**
     * 设置角色
     *
     * @param roles 角色集合
     */
    public void setRoles(Set<RoleContext> roles) {
        this.roles = roles;
        this.roleCodes = CollUtils.mapToSet(roles, RoleContext::getCode);
    }

    /**
     * 密码是否已过期
     * @return 是否过期
     */
    public boolean isPasswordExpired() {
        // 永久有效
        if (this.getPasswordExpirationDays() == null || this.getPasswordExpirationDays() <= 0) {
            return false;
        }
        if (this.getPwdResetTime() == null) {
            return false;
        }
        return this.getPwdResetTime().plusDays(this.getPasswordExpirationDays()).isBefore(LocalDateTime.now());
    }

    public boolean isSuperAdminUser() {
        if (CollUtil.isEmpty(this.getRoleCodes())) {
            return false;
        }
        return this.getRoleCodes().contains(RoleCodeEnum.SUPER_ADMIN.getValue());
    }
}
