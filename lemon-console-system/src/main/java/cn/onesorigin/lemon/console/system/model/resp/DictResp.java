package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 字典响应参数
 *
 * @author BruceMaa
 * @since 2025-09-18 10:46
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "字典响应参数")
public class DictResp extends BaseDetailResp {
    /**
     * 名称
     */
    @Schema(description = "名称", example = "公告类型")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "notice_type")
    String code;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "公告类型描述信息")
    String description;

    /**
     * 是否为系统内置数据
     */
    @Schema(description = "是否为系统内置数据", example = "true")
    Boolean isSystem;
}
