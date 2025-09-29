package cn.onesorigin.lemon.console.common.base.model.resp;

import cn.crane4j.annotation.Assemble;
import cn.crane4j.annotation.Mapping;
import cn.idev.excel.annotation.ExcelProperty;
import cn.onesorigin.lemon.console.common.constant.ContainerConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * 响应参数基类
 *
 * @author BruceMaa
 * @since 2025-09-04 17:09
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResp {

    /**
     * ID
     */
    @Schema(description = "ID")
    @ExcelProperty(value = "ID", order = 1)
    Long id;

    /**
     * 创建人
     */
    @JsonIgnore
    @Assemble(container = ContainerConstants.USER_NICKNAME, props = @Mapping(ref = "createdUsername"))
    Long createdBy;

    /**
     * 创建人用户名
     */
    @Schema(description = "创建人用户名")
    @ExcelProperty(value = "创建人", order = Integer.MAX_VALUE - 4)
    String createdUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间", order = Integer.MAX_VALUE - 3)
    LocalDateTime createdAt;

    /**
     * 是否禁用修改
     */
    @Schema(description = "是否禁用修改", example = "true")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean disabled;
}
