package cn.onesorigin.lemon.console.system.service;


import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.model.entity.OptionDO;
import cn.onesorigin.lemon.console.system.model.query.OptionQuery;
import cn.onesorigin.lemon.console.system.model.req.OptionReq;
import cn.onesorigin.lemon.console.system.model.req.OptionValueResetReq;
import cn.onesorigin.lemon.console.system.model.resp.OptionResp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 参数业务接口
 *
 * @author BruceMaa
 * @since 2025-09-02 14:30
 */
public interface OptionService extends IService<OptionDO> {

    List<OptionResp> list(OptionQuery query);

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

    /**
     * 根据类别查询参数值
     *
     * @param optionCategory 类别
     * @return 参数信息
     */
    Map<String, String> getByCategory(OptionCategoryEnum optionCategory);

    /**
     * 修改参数信息
     *
     * @param options 参数信息集合
     */
    void updateBatch(List<OptionReq> options);

    /**
     * 重置参数
     *
     * @param req 重置的参数信息
     */
    void resetValue(OptionValueResetReq req);
}
