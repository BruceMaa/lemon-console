package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import cn.onesorigin.lemon.console.system.mapper.UserRoleMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserRoleDO;
import cn.onesorigin.lemon.console.system.model.query.RoleUserQuery;
import cn.onesorigin.lemon.console.system.model.resp.RoleUserResp;
import cn.onesorigin.lemon.console.system.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.data.util.QueryWrapperHelper;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

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

    @AutoOperate(type = RoleUserResp.class, on = "list")
    @Override
    public PageResp<RoleUserResp> pageUser(RoleUserQuery query, PageQuery pageQuery) {
        String description = query.getDescription();
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<UserRoleDO>().eq("t1.role_id", query.getRoleId())
                .and(StrUtil.isNotBlank(description), q -> q.like("t2.username", description)
                        .or()
                        .like("t2.nickname", description)
                        .or()
                        .like("t2.description", description));
        QueryWrapperHelper.sort(queryWrapper, pageQuery.getSort());
        IPage<RoleUserResp> page = baseMapper.selectUserPage(new Page<>(pageQuery.getPage(), pageQuery
                .getSize()), queryWrapper);
        return PageResp.build(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Long> ids) {
        baseMapper.deleteByIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignRoleToUsers(Long roleId, List<Long> userIds) {
        List<UserRoleDO> userRoleList = CollUtils.mapToList(userIds, userId -> new UserRoleDO(userId, roleId));
        baseMapper.insertBatch(userRoleList);
    }

    @Override
    public boolean isRoleIdExists(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        return baseMapper.lambdaQuery().in(UserRoleDO::getRoleId, roleIds).exists();
    }
}
