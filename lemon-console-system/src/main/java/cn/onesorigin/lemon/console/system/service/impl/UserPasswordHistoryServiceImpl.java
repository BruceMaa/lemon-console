package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.system.mapper.UserPasswordHistoryMapper;
import cn.onesorigin.lemon.console.system.model.entity.UserPasswordHistoryDO;
import cn.onesorigin.lemon.console.system.service.UserPasswordHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.CollUtils;

import java.util.List;

/**
 * 用户历史密码 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-24 15:31
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserPasswordHistoryServiceImpl implements UserPasswordHistoryService {

    private final UserPasswordHistoryMapper baseMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(Long userId, String password, int count) {
        if (StrUtil.isBlank(password)) {
            return;
        }
        baseMapper.insert(new UserPasswordHistoryDO(userId, password));
        // 删除过期历史密码
    }

    @Override
    public void deleteByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(UserPasswordHistoryDO::getUserId, userIds).remove();
    }

    @Override
    public boolean isPasswordReused(Long userId, String password, int count) {
        // 查询最近N个历史密码
        var list = baseMapper.lambdaQuery()
                .select(UserPasswordHistoryDO::getPassword)
                .eq(UserPasswordHistoryDO::getUserId, userId)
                .orderByDesc(UserPasswordHistoryDO::getCreatedAt)
                .last("limit " + count)
                .list();
        if (CollUtil.isEmpty(list)) {
            return false;
        }
        // 校验是否重复使用历史密码
        var passwordList = CollUtils.mapToList(list, UserPasswordHistoryDO::getPassword);
        return passwordList.stream().anyMatch(p -> passwordEncoder.matches(password, p));
    }
}
