package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.OptionDO;
import cn.onesorigin.lemon.console.system.model.req.OptionReq;
import cn.onesorigin.lemon.console.system.model.resp.OptionResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 参数 转换器
 *
 * @author BruceMaa
 * @since 2025-09-24 14:22
 */
@Mapper
public interface OptionConvert {

    OptionConvert INSTANCE = Mappers.getMapper(OptionConvert.class);

    OptionResp toResp(OptionDO optionDO);

    List<OptionResp> toResp(List<OptionDO> optionDOs);

    List<OptionDO> toDO(List<OptionReq> optionReqs);
}
