package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.constant.SystemConstants;
import cn.onesorigin.lemon.console.system.convert.MenuConvert;
import cn.onesorigin.lemon.console.system.mapper.MenuMapper;
import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    @Override
    public Set<String> findPermissionsByUserId(Long userId) {
        return baseMapper.findPermissionByUserId(userId);
    }

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
}
