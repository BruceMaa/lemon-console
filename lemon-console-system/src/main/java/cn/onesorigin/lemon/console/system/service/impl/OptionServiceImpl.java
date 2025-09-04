package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.mapper.OptionMapper;
import cn.onesorigin.lemon.console.system.model.entity.OptionDO;
import cn.onesorigin.lemon.console.system.service.OptionService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.util.validation.CheckUtils;

import java.util.function.Function;

/**
 * 参数业务实现
 *
 * @author BruceMaa
 * @since 2025-09-03 15:23
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, OptionDO> implements OptionService {
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
}
