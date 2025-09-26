package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.DeptDO;
import cn.onesorigin.lemon.console.system.model.query.DeptQuery;
import cn.onesorigin.lemon.console.system.model.req.DeptReq;
import cn.onesorigin.lemon.console.system.model.resp.DeptResp;
import top.continew.starter.data.service.IService;

import java.util.List;

/**
 * 部门 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-19 10:43
 */
public interface DeptService extends BaseService<DeptResp, DeptResp, DeptQuery, DeptReq>, IService<DeptDO> {
    /**
     * 根据部门ID查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<DeptDO> findChildren(Long deptId);
}
