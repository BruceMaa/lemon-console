package cn.onesorigin.lemon.console.system.model.resp;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 参数响应参数
 *
 * @author BruceMaa
 * @since 2025-09-24 13:58
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "参数响应参数")
public class OptionResp {

    /**
     * ID
     */
    @Schema(description = "ID")
    Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    String name;

    /**
     * 键
     */
    @Schema(description = "键")
    String code;

    /**
     * 值
     */
    @Schema(description = "值")
    String value;

    /**
     * 默认值
     */
    @Schema(description = "默认值")
    @JsonIgnore
    String defaultValue;

    /**
     * 描述
     */
    @Schema(description = "描述")
    String description;

    public String getValue() {
        return StrUtil.nullToDefault(value, defaultValue);
    }
}
