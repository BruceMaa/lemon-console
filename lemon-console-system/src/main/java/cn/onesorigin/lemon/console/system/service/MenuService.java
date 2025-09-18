package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.MenuDO;
import cn.onesorigin.lemon.console.system.model.query.MenuQuery;
import cn.onesorigin.lemon.console.system.model.req.MenuReq;
import cn.onesorigin.lemon.console.system.model.resp.MenuResp;
import top.continew.starter.data.service.IService;

/**
 * 菜单 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-18 16:45
 */
public interface MenuService extends BaseService<MenuResp, MenuResp, MenuQuery, MenuReq>, IService<MenuDO> {
}
