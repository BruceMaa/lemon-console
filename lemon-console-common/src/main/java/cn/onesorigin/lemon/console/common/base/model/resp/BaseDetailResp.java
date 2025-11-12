package cn.onesorigin.lemon.console.common.base.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.Mapping;
import cn.crane4j.annotation.condition.ConditionOnPropertyNotNull;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 详情响应参数基类
 *
 * @author BruceMaa
 * @since 2025-09-04 17:18
 */
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseDetailResp extends BaseResp {

    /**
     * 修改人
     */
    @JsonIgnore
    @ConditionOnPropertyNotNull
    @Assemble(container = ContainerConstants.USER_NICKNAME, props = @Mapping(ref = "modifiedUsername"))
    Long modifiedBy;

    /**
     * 修改人用户名
     */
    @Schema(description = "修改人用户名")
    @ExcelProperty(value = "修改人", order = Integer.MAX_VALUE - 2)
    String modifiedUsername;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty(value = "修改时间", order = Integer.MAX_VALUE - 1)
    LocalDateTime modifiedAt;
}
