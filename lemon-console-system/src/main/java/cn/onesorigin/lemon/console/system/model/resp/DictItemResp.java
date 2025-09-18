package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 字典项响应参数
 *
 * @author BruceMaa
 * @since 2025-09-18 15:31
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "字典项响应参数")
public class DictItemResp extends BaseDetailResp {

    /**
     * 字典 ID
     */
    @Schema(description = "字典 ID", example = "1")
    Long dictId;

    /**
     * 标签
     */
    @Schema(description = "标签", example = "通知")
    String label;

    /**
     * 值
     */
    @Schema(description = "值", example = "1")
    String value;

    /**
     * 标签颜色
     */
    @Schema(description = "标签颜色", example = "blue")
    String color;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    Integer sort;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "通知描述信息")
    String description;
}
