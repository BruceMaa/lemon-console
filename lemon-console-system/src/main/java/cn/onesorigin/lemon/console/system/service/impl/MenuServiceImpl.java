package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.convert.MenuConvert;
import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import cn.onesorigin.lemon.console.system.mapper.MenuMapper;
import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import com.alicp.jetcache.anno.Cached;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.validation.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 菜单 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-18 16:46
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, MenuDO, MenuResp, MenuResp, MenuQuery, MenuReq> implements MenuService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long create(MenuReq req) {
        this.checkTitleRepeat(req.getTitle(), req.getParentId(), null);
        // 目录和菜单的组件名称不能重复
        if (!MenuTypeEnum.BUTTON.equals(req.getType())) {
            this.checkNameRepeat(req.getName(), null);
        }
        // 目录类型菜单，默认为 Layout
        if (MenuTypeEnum.DIR.equals(req.getType())) {
            req.setComponent(StrUtil.blankToDefault(req.getComponent(), "Layout"));
        }
        RedisUtils.deleteByPattern(CacheConstants.ROLE_MENU_KEY_PREFIX + StringConstants.ASTERISK);
        return super.create(req);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(MenuReq req, Long id) {
        this.checkTitleRepeat(req.getTitle(), req.getParentId(), id);
        // 目录和菜单的组件名称不能重复
        if (!MenuTypeEnum.BUTTON.equals(req.getType())) {
            this.checkNameRepeat(req.getName(), id);
        }
        MenuDO oldMenu = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getType(), oldMenu.getType(), "不允许修改菜单类型");
        super.update(req, id);
        RedisUtils.deleteByPattern(CacheConstants.ROLE_MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Long> ids) {
        // 级联删除菜单（包含子菜单）
        List<Long> allDeleteIdList = this.listCascadingDeleteMenuIds(ids);
        baseMapper.deleteByIds(allDeleteIdList);
        RedisUtils.deleteByPattern(CacheConstants.ROLE_MENU_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public Set<String> findPermissionsByUserId(Long userId) {
        return baseMapper.findPermissionByUserId(userId);
    }

    @Cached(key = "#roleId", name = CacheConstants.ROLE_MENU_KEY_PREFIX)
    @Override
    public List<MenuResp> findByRoleId(Long roleId) {
        if (SystemConstants.SUPER_ADMIN_ROLE_ID.equals(roleId)) {
            return super.list(new MenuQuery(DisEnableStatusEnum.ENABLE), null);
        }
        List<MenuDO> menuList = baseMapper.findByRoleId(roleId);
        List<MenuResp> list = MenuConvert.INSTANCE.toResp(menuList);
        list.forEach(super::fill);
        return list;
    }

    /**
     * 检查标题是否重复
     *
     * @param title    标题
     * @param parentId 上级 ID
     * @param id       ID
     */
    private void checkTitleRepeat(String title, Long parentId, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(MenuDO::getTitle, title)
                .eq(MenuDO::getParentId, parentId)
                .ne(id != null, MenuDO::getId, id)
                .exists(), "标题为 【{}】 的菜单已存在", title);
    }

    /**
     * 检查组件名称是否重复
     *
     * @param name 组件名称
     * @param id   ID
     */
    private void checkNameRepeat(String name, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(MenuDO::getName, name)
                .ne(MenuDO::getType, MenuTypeEnum.BUTTON)
                .ne(id != null, MenuDO::getId, id)
                .exists(), "组件名称为 【{}】 的菜单已存在", name);
    }

    /**
     * 级联获取所有待删除菜单 ID 列表（包含自身及所有子菜单）
     *
     * @param ids ID 列表
     * @return 待删除菜单 ID 列表（包含自身及所有子菜单）
     */
    private List<Long> listCascadingDeleteMenuIds(List<Long> ids) {
        List<Long> menuIds = new ArrayList<>(ids);
        List<Long> childIdList = baseMapper.lambdaQuery()
                .select(MenuDO::getId)
                .in(MenuDO::getParentId, menuIds)
                .list()
                .stream()
                .map(MenuDO::getId)
                .toList();
        if (childIdList.isEmpty()) {
            return menuIds;
        }
        menuIds.addAll(this.listCascadingDeleteMenuIds(childIdList));
        return menuIds;
    }
}
