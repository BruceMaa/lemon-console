package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.convert.OptionConvert;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.mapper.OptionMapper;
import cn.onesorigin.lemon.console.system.model.entity.OptionDO;
import cn.onesorigin.lemon.console.system.model.query.OptionQuery;
import cn.onesorigin.lemon.console.system.model.resp.OptionResp;
import cn.onesorigin.lemon.console.system.service.OptionService;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.data.util.QueryWrapperHelper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 参数业务实现
 *
 * @author BruceMaa
 * @since 2025-09-03 15:23
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, OptionDO> implements OptionService {
    @Override
    public List<OptionResp> list(OptionQuery query) {
        return OptionConvert.INSTANCE.convert(baseMapper.selectList(QueryWrapperHelper.build(query)));
    }

    @Override
    public int getValueByCode2Int(String code) {
        return this.getValueByCode(code, Integer::parseInt);
    }

    @Override
    public <T> T getValueByCode(String code, Function<String, T> mapper) {
        String value = RedisUtils.get(CacheConstants.OPTION_KEY_PREFIX + code);
        if (StrUtil.isNotBlank(value)) {
            return mapper.apply(value);
        }
        var option = this.getOne(Wrappers.<OptionDO>lambdaQuery()
                .select(OptionDO::getValue, OptionDO::getDefaultValue)
                .eq(OptionDO::getCode, code), false);

        CheckUtils.throwIfNull(option, "参数【{}】不存在", code);
        value = StrUtil.nullToDefault(option.getValue(), option.getDefaultValue());
        CheckUtils.throwIfBlank(value, "参数【{}】值不能为空", code);
        RedisUtils.set(CacheConstants.OPTION_KEY_PREFIX + code, value);
        return mapper.apply(value);
    }

    @Cached(key = "#category", name = CacheConstants.OPTION_KEY_PREFIX + "MAP:")
    @Override
    public Map<String, String> getByCategory(OptionCategoryEnum optionCategory) {
        return baseMapper.lambdaQuery().select(OptionDO::getCode, OptionDO::getValue, OptionDO::getDefaultValue)
                .eq(OptionDO::getCategory, optionCategory.name())
                .list()
                .stream()
                .collect(Collectors.toMap(OptionDO::getCode, o -> StrUtil.emptyIfNull(ObjectUtil.defaultIfNull(o
                        .getValue(), o.getDefaultValue())), (oldVal, newVal) -> oldVal));
    }
}
