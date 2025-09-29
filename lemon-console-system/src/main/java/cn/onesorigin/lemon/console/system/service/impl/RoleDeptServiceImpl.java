package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.system.mapper.RoleDeptMapper;
import cn.onesorigin.lemon.console.system.model.entity.RoleDeptDO;
import cn.onesorigin.lemon.console.system.service.RoleDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RoleDeptServiceImpl implements RoleDeptService {

    private final RoleDeptMapper baseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDeptIds(List<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(RoleDeptDO::getDeptId, deptIds).remove();
    }
}
