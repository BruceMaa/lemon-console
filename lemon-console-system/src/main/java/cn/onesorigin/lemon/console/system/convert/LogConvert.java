package cn.onesorigin.lemon.console.system.convert;

import cn.onesorigin.lemon.console.system.model.entity.LogDO;
import cn.onesorigin.lemon.console.system.model.resp.LogDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.LogResp;
import cn.onesorigin.lemon.console.system.model.resp.LoginLogExportResp;
import cn.onesorigin.lemon.console.system.model.resp.OperationLogExportResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统日志 转换器
 *
 * @author BruceMaa
 * @since 2025-11-07 15:10
 */
@Mapper
public interface LogConvert {

    LogConvert INSTANCE = Mappers.getMapper(LogConvert.class);

    @Mapping(target = "createdUsername", ignore = true)
    LogDetailResp toDetailResp(LogDO logDO);

    LoginLogExportResp toLoginLogExportResp(LogResp logResp);

    List<LoginLogExportResp> toLoginLogExportRespList(List<LogResp> logRespList);

    OperationLogExportResp toOperationLogExportResp(LogResp logResp);

    List<OperationLogExportResp> toOperationLogExportRespList(List<LogResp> logRespList);
}
