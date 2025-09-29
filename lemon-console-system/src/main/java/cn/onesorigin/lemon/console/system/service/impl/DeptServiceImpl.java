package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.mapper.DeptMapper;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.query.DeptQuery;
import cn.onesorigin.lemon.console.system.model.req.DeptReq;
import cn.onesorigin.lemon.console.system.model.resp.DeptResp;
import cn.onesorigin.lemon.console.system.service.DeptService;
import cn.onesorigin.lemon.console.system.service.RoleDeptService;
import cn.onesorigin.lemon.console.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.validation.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 部门 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-19 10:44
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, DeptDO, DeptResp, DeptResp, DeptQuery, DeptReq> implements DeptService {

    @Lazy
    @Resource
    private UserService userService;
    private final RoleDeptService roleDeptService;

    @Override
    protected void beforeCreate(DeptReq req) {
        this.checkNameRepeat(req.getName(), req.getParentId(), null);
        req.setAncestors(this.getAncestors(req.getParentId()));
    }

    @Override
    protected void beforeUpdate(DeptReq req, Long id) {
        this.checkNameRepeat(req.getName(), req.getParentId(), id);
        DeptDO oldDept = super.getById(id);
        String oldName = oldDept.getName();
        DisEnableStatusEnum newStatus = req.getStatus();
        Long oldParentId = oldDept.getParentId();
        if (Objects.equals(Boolean.TRUE, oldDept.getIsSystem())) {
            CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, newStatus, "【{}】 是系统内置部门，不允许禁用", oldName);
            CheckUtils.throwIfNotEqual(req.getParentId(), oldParentId, "【{}】 是系统内置部门，不允许变更上级部门", oldName);
        }
        // 启用/禁用部门
        if (ObjectUtil.notEqual(newStatus, oldDept.getStatus())) {
            List<DeptDO> children = this.findChildren(id);
            long enabledChildrenCount = children.stream()
                    .filter(d -> DisEnableStatusEnum.ENABLE.equals(d.getStatus()))
                    .count();
            CheckUtils.throwIf(DisEnableStatusEnum.DISABLE
                    .equals(newStatus) && enabledChildrenCount > 0, "禁用 【{}】 前，请先禁用其所有下级部门", oldName);
            DeptDO oldParentDept = this.getByParentId(oldParentId);
            CheckUtils.throwIf(DisEnableStatusEnum.ENABLE.equals(newStatus) && DisEnableStatusEnum.DISABLE
                    .equals(oldParentDept.getStatus()), "启用 【{}】 前，请先启用其所有上级部门", oldName);
        }
        // 变更上级部门
        if (ObjectUtil.notEqual(req.getParentId(), oldParentId)) {
            // 更新祖级列表
            String newAncestors = this.getAncestors(req.getParentId());
            req.setAncestors(newAncestors);
            // 更新子级的祖级列表
            this.updateChildrenAncestors(newAncestors, oldDept.getAncestors(), id);
        }
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<DeptDO> list = baseMapper.lambdaQuery()
                .select(DeptDO::getName, DeptDO::getIsSystem)
                .in(DeptDO::getId, ids)
                .list();
        Optional<DeptDO> isSystemData = list.stream().filter(DeptDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选部门 【{}】 是系统内置部门，不允许删除", isSystemData.orElseGet(DeptDO::new)
                .getName());
        CheckUtils.throwIf(this.countChildren(ids) > 0, "所选部门存在下级部门，不允许删除");
        CheckUtils.throwIf(userService.countByDeptIds(ids) > 0, "所选部门存在用户关联，请解除关联后重试");
        // 删除角色和部门关联
        roleDeptService.deleteByDeptIds(ids);
    }

    @Override
    public List<DeptDO> findChildren(Long deptId) {
        // TODO 之后部门信息中会添加deptCode字段，然后like查询所有子部门信息
        return baseMapper.lambdaQuery().apply("(select position(',%s,' in ','||ancestors||',')) <> 0".formatted(deptId)).list();
    }

    /**
     * 检查名称是否重复
     *
     * @param name     名称
     * @param parentId 上级 ID
     * @param id       ID
     */
    private void checkNameRepeat(String name, Long parentId, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(DeptDO::getName, name)
                .eq(DeptDO::getParentId, parentId)
                .ne(id != null, DeptDO::getId, id)
                .exists(), "名称为 【{}】 的部门已存在", name);
    }

    /**
     * 获取祖级列表
     *
     * @param parentId 上级部门
     * @return 祖级列表
     */
    private String getAncestors(Long parentId) {
        DeptDO parentDept = this.getByParentId(parentId);
        return "%s,%s".formatted(parentDept.getAncestors(), parentId);
    }

    /**
     * 根据上级部门 ID 查询
     *
     * @param parentId 上级部门 ID
     * @return 上级部门信息
     */
    private DeptDO getByParentId(Long parentId) {
        DeptDO parentDept = baseMapper.selectById(parentId);
        CheckUtils.throwIfNull(parentDept, "上级部门不存在");
        return parentDept;
    }

    /**
     * 更新子部门祖级列表
     *
     * @param newAncestors 新祖级列表
     * @param oldAncestors 原祖级列表
     * @param id           ID
     */
    private void updateChildrenAncestors(String newAncestors, String oldAncestors, Long id) {
        List<DeptDO> children = this.findChildren(id);
        if (CollUtil.isEmpty(children)) {
            return;
        }
        List<DeptDO> list = new ArrayList<>(children.size());
        for (DeptDO child : children) {
            DeptDO dept = new DeptDO();
            dept.setId(child.getId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(dept);
        }
        baseMapper.updateById(list);
    }

    /**
     * 查询子部门数量
     *
     * @param ids ID 列表
     * @return 子部门数量
     */
    private Long countChildren(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return 0L;
        }
        return ids.stream()
                .mapToLong(id -> baseMapper.lambdaQuery().apply("(select position(',%s,' in ','||ancestors||',')) <> 0".formatted(id)).count())
                .sum();
    }
}
