package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.system.model.query.LogQuery;
import cn.onesorigin.lemon.console.system.model.resp.LogDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.LogResp;
import jakarta.servlet.http.HttpServletResponse;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.query.SortQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

/**
 * 系统日志 业务接口
 *
 * @author BruceMaa
 * @since 2025-11-07 15:03
 */
public interface LogService {

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页列表信息
     */
    PageResp<LogResp> page(LogQuery query, PageQuery pageQuery);

    /**
     * 查询详情
     *
     * @param id ID
     * @return 详情信息
     */
    LogDetailResp get(Long id);

    /**
     * 导出登录日志
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param response  响应对象
     */
    void exportLoginLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response);

    /**
     * 导出操作日志
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @param response  响应对象
     */
    void exportOperationLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response);
}
