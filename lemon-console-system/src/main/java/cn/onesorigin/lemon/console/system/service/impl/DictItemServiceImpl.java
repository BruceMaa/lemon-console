package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.mapper.DictItemMapper;
import cn.onesorigin.lemon.console.system.model.entity.DictItemDO;
import cn.onesorigin.lemon.console.system.model.query.DictItemQuery;
import cn.onesorigin.lemon.console.system.model.req.DictItemReq;
import cn.onesorigin.lemon.console.system.model.resp.DictItemResp;
import cn.onesorigin.lemon.console.system.service.DictItemService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.autoconfigure.application.ApplicationProperties;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.enums.BaseEnum;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典项 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-18 15:23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItemDO, DictItemResp, DictItemResp, DictItemQuery, DictItemReq> implements DictItemService {

    private final ApplicationProperties applicationProperties;
    private static final Map<String, List<LabelValueResp<Serializable>>> ENUM_DICT_CACHE = new ConcurrentHashMap<>();

    @Override
    protected void beforeCreate(DictItemReq req) {
        this.checkValueRepeat(req.getValue(), null, req.getDictId());
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    protected void beforeUpdate(DictItemReq req, Long id) {
        this.checkValueRepeat(req.getValue(), id, req.getDictId());
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public List<LabelValueResp<Serializable>> findByDictCode(String dictCode) {
        return Optional.ofNullable(ENUM_DICT_CACHE.get(dictCode.toLowerCase()))
                .orElseGet(() -> baseMapper.findByDictCode(dictCode));
    }

    @Override
    public void deleteByDictIds(List<Long> dictIds) {
        if (CollUtil.isEmpty(dictIds)) {
            return;
        }
        baseMapper.lambdaUpdate().in(DictItemDO::getDictId, dictIds).remove();
        RedisUtils.deleteByPattern(CacheConstants.DICT_KEY_PREFIX + StringConstants.ASTERISK);
    }

    @Override
    public List<String> findEnumDictNames() {
        return ENUM_DICT_CACHE.keySet().stream().toList();
    }

    /**
     * 检查字典值是否重复
     *
     * @param value  字典值
     * @param id     ID
     * @param dictId 字典 ID
     */
    private void checkValueRepeat(String value, Long id, Long dictId) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(DictItemDO::getValue, value)
                .eq(DictItemDO::getDictId, dictId)
                .ne(id != null, DictItemDO::getId, id)
                .exists(), "字典值为 【{}】 的字典项已存在", value);
    }

    /**
     * 将枚举转换为枚举字典
     *
     * @param enumClass 枚举类型
     * @return 枚举字典
     */
    private <T extends Serializable> List<LabelValueResp<T>> toEnumDict(Class<?> enumClass) {
        Object[] enumConstants = enumClass.getEnumConstants();
        if (ArrayUtil.isEmpty(enumConstants)) {
            return List.of();
        }
        return Arrays.stream(enumConstants).map(e -> {
            BaseEnum<T> baseEnum = (BaseEnum<T>) e;
            return new LabelValueResp<>(baseEnum.getDescription(), baseEnum.getValue(), baseEnum.getColor());
        }).toList();
    }

    /**
     * 缓存枚举字典
     */
    @PostConstruct
    public void init() {
        Set<Class<?>> classSet = ClassUtil.scanPackageBySuper(applicationProperties.getBasePackage(), BaseEnum.class);
        for (Class<?> cls : classSet) {
            List<LabelValueResp<Serializable>> value = this.toEnumDict(cls);
            if (CollUtil.isEmpty(value)) {
                continue;
            }
            String key = StrUtil.toUnderlineCase(cls.getSimpleName()).toLowerCase();
            ENUM_DICT_CACHE.put(key, value);
        }
        log.debug("枚举字典已缓存到内存：{}", ENUM_DICT_CACHE.keySet());
    }
}
