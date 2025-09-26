package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.system.mapper.UserRoleMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关联 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-24 16:35
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleMapper baseMapper;

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return baseMapper.lambdaQuery()
                .select(UserRoleDO::getRoleId)
                .eq(UserRoleDO::getUserId, userId)
                .list()
                .stream()
                .map(UserRoleDO::getRoleId)
                .toList();
    }

    @ContainerMethod(namespace = ContainerConstants.USER_ROLE_ID_LIST, resultKey = "userId", resultType = UserRoleDO.class, type = MappingType.ONE_TO_MANY)
    @Override
    public List<UserRoleDO> findByUserIds(List<Long> userIds) {
        return baseMapper.lambdaQuery()
                .in(UserRoleDO::getUserId, userIds)
                .list();
    }

    @Override
    public List<Long> findUserIdsByRoleId(Long roleId) {
        return baseMapper.lambdaQuery()
                .select(UserRoleDO::getUserId)
                .eq(UserRoleDO::getRoleId, roleId)
                .list()
                .stream()
                .map(UserRoleDO::getUserId)
                .toList();
    }
}
