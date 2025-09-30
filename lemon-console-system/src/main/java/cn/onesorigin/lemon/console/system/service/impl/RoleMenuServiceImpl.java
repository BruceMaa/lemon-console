package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.mapper.RoleMenuMapper;
import cn.onesorigin.lemon.console.system.model.entity.RoleMenuDO;
import cn.onesorigin.lemon.console.system.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.data.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-30 09:14
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDO> implements RoleMenuService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(List<Long> menuIds, Long roleId) {
        // 删除原有关联
        baseMapper.lambdaUpdate().eq(RoleMenuDO::getRoleId, roleId).remove();
        // 保存最新关联
        List<RoleMenuDO> roleMenuList = CollUtils.mapToList(menuIds, menuId -> new RoleMenuDO(roleId, menuId));
        return baseMapper.insertBatch(roleMenuList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(RoleMenuDO::getRoleId, roleIds).remove();
    }

    @Override
    public List<Long> findMenuIdsByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return List.of();
        }
        return baseMapper.lambdaQuery()
                .select(RoleMenuDO::getMenuId)
                .in(RoleMenuDO::getRoleId, roleIds)
                .list()
                .stream()
                .map(RoleMenuDO::getMenuId)
                .collect(Collectors.toList());
    }
}
