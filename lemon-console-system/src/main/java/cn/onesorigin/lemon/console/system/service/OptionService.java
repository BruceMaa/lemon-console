package cn.onesorigin.lemon.console.system.service;


import cn.onesorigin.lemon.console.system.model.entity.OptionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.function.Function;

/**
 * 参数业务接口
 *
 * @author BruceMaa
 * @since 2025-09-02 14:30
 */
public interface OptionService extends IService<OptionDO> {

    /**
     * 根据编码查询参数值
     *
     * @param code 参数编码
     * @return 参数值
     */
    int getValueByCode2Int(String code);

    /**
     * 根据编码查询参数值
     *
     * @param code   编码
     * @param mapper 转换方法 e.g.：value -> Integer.parseInt(value)
     * @return 参数值
     */
    <T> T getValueByCode(String code, Function<String, T> mapper);
}
