package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.mapper.RoleDeptMapper;
import cn.onesorigin.lemon.console.system.model.entity.RoleDeptDO;
import cn.onesorigin.lemon.console.system.service.RoleDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.data.service.impl.ServiceImpl;

import java.util.List;

/**
 * 角色和部门关联 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-29 17:33
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDeptDO> implements RoleDeptService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDeptIds(List<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(RoleDeptDO::getDeptId, deptIds).remove();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(List<Long> deptIds, Long roleId) {
        // 删除原有关联
        baseMapper.lambdaUpdate().eq(RoleDeptDO::getRoleId, roleId).remove();
        // 保存最新关联
        List<RoleDeptDO> roleDeptList = CollUtils.mapToList(deptIds, deptId -> new RoleDeptDO(roleId, deptId));
        return baseMapper.insertBatch(roleDeptList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(RoleDeptDO::getRoleId, roleIds).remove();
    }
}
