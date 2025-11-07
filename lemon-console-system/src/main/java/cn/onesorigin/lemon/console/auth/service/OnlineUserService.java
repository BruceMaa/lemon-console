package cn.onesorigin.lemon.console.auth.service;

import cn.onesorigin.lemon.console.auth.model.query.OnlineUserQuery;
import cn.onesorigin.lemon.console.auth.model.resp.OnlineUserResp;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 在线用户 业务接口
 *
 * @author BruceMaa
 * @since 2025-10-31 14:58
 */
public interface OnlineUserService {
    /**
     * 踢出用户
     *
     * @param userId 用户ID
     */
    void kickOut(Long userId);

    /**
     * 分页查询列表
     *
     * @param query     查询条件
     * @param pageQuery 分页查询条件
     * @return 分页查询结果
     */
    PageResp<OnlineUserResp> page(OnlineUserQuery query, PageQuery pageQuery);

    /**
     * 查询在线用户列表
     *
     * @param query 查询条件
     * @return 在线用户列表
     */
    List<OnlineUserResp> list(OnlineUserQuery query);

    /**
     * 获取最后活跃时间
     *
     * @param token 令牌
     * @return 最后活跃时间
     */
    LocalDateTime getLastActiveTime(String token);
}
