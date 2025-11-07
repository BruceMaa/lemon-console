package cn.onesorigin.lemon.console.auth.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.auth.convert.UserInfoConvert;
import cn.onesorigin.lemon.console.auth.model.query.OnlineUserQuery;
import cn.onesorigin.lemon.console.auth.model.resp.OnlineUserResp;
import cn.onesorigin.lemon.console.auth.service.OnlineUserService;
import cn.onesorigin.lemon.console.common.context.UserContext;
import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 在线用户 业务实现
 *
 * @author BruceMaa
 * @since 2025-10-31 15:00
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OnlineUserServiceImpl implements OnlineUserService {
    @Override
    public void kickOut(Long userId) {
        if (!StpUtil.isLogin(userId)) {
            return;
        }
        StpUtil.kickout(userId);
    }

    @AutoOperate(type = OnlineUserResp.class, on = "list")
    @Override
    public PageResp<OnlineUserResp> page(OnlineUserQuery query, PageQuery pageQuery) {
        var list = this.list(query);
        return PageResp.build(pageQuery.getPage(), pageQuery.getSize(), list);
    }

    @Override
    public List<OnlineUserResp> list(OnlineUserQuery query) {
        List<OnlineUserResp> list = new ArrayList<>();
        // 查询所有在线 token
        var tokenKeys = StpUtil.searchTokenValue(StringConstants.EMPTY, 0, -1, false);
        var tokenMap = tokenKeys.stream()
                .map(tokenKey -> StrUtil.subAfter(tokenKey, StringConstants.COLON, true))
                .map(token -> {
                    var loginIdObj = StpUtil.getLoginIdByToken(token);
                    var tokenTimeout = StpUtil.getStpLogic().getTokenActiveTimeoutByToken(token);
                    // 将相关信息打包成对象活简单的Entry，便于后续过滤与归类
                    return new AbstractMap.SimpleEntry<>(token, new AbstractMap.SimpleEntry<>(loginIdObj, tokenTimeout));
                })
                .filter(entry -> {
                    var loginIdObj = entry.getValue().getKey();
                    var tokenTimeout = entry.getValue().getValue();
                    return loginIdObj != null && tokenTimeout >= SaTokenDao.NOT_VALUE_EXPIRE;
                })
                // 此时数据都有效，进行收集
                .collect(Collectors.groupingBy(entry -> Convert.toLong(entry.getValue().getKey()),
                        Collectors.mapping(AbstractMap.SimpleEntry::getKey, Collectors.toList())));
        // 筛选数据
        tokenMap.forEach((loginId, tokenList) -> {
            var userContext = UserContextHolder.getContext(loginId);
            if (userContext == null
                    || !this.isMatchNickname(query.getNickname(), userContext)
                    || !this.isMatchClientId(query.getClientId(), userContext)) {
                return;
            }
            var loginTimeList = query.getLoginTime();
            tokenList.parallelStream().forEach(token -> {
                var extraContext = UserContextHolder.getExtraContext(token);
                if (!this.isMatchLoginTime(loginTimeList, extraContext.getLoginTime())) {
                    return;
                }
                list.add(UserInfoConvert.INSTANCE.toOnlineUserResp(userContext, extraContext, token));
            });
        });
        // 设置排序
        CollUtil.sort(list, Comparator.comparing(OnlineUserResp::getLoginTime).reversed());
        return list;
    }

    @Override
    public LocalDateTime getLastActiveTime(String token) {
        var lastActiveTime = StpUtil.getStpLogic().getTokenLastActiveTime(token);
        return lastActiveTime == SaTokenDao.NOT_VALUE_EXPIRE ? null : DateUtil.date(lastActiveTime).toLocalDateTime();
    }

    /**
     * 是否匹配昵称
     *
     * @param nickname    昵称
     * @param userContext 用户上下文信息
     * @return 是否匹配昵称
     */
    private boolean isMatchNickname(String nickname, UserContext userContext) {
        if (StrUtil.isBlank(nickname)) {
            return true;
        }
        return StrUtil.contains(userContext.getUsername(), nickname) || StrUtil.contains(UserContextHolder
                .getNickname(userContext.getId()), nickname);
    }

    /**
     * 是否匹配客户端 ID
     *
     * @param clientId    客户端 ID
     * @param userContext 用户上下文信息
     * @return 是否匹配客户端 ID
     */
    private boolean isMatchClientId(String clientId, UserContext userContext) {
        if (StrUtil.isBlank(clientId)) {
            return true;
        }
        return Objects.equals(userContext.getClientId(), clientId);
    }

    /**
     * 是否匹配登录时间
     *
     * @param loginTimeList 登录时间列表
     * @param loginTime     登录时间
     * @return 是否匹配登录时间
     */
    private boolean isMatchLoginTime(List<LocalDateTime> loginTimeList, LocalDateTime loginTime) {
        if (CollUtil.isEmpty(loginTimeList)) {
            return true;
        }
        return loginTime.isAfter(loginTimeList.get(0)) && loginTime.isBefore(loginTimeList.get(1));
    }
}
