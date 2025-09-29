package cn.onesorigin.lemon.console.system.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.StorageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 存储响应参数
 *
 * @author BruceMaa
 * @since 2025-09-29 09:16
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "存储响应参数")
public class StorageResp extends BaseDetailResp {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "存储1")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "local")
    String code;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    StorageTypeEnum type;

    /**
     * Access Key
     */
    @Schema(description = "Access Key")
    String accessKey;

    /**
     * Endpoint
     */
    @Schema(description = "Endpoint")
    String endpoint;

    /**
     * Bucket/存储路径
     */
    @Schema(description = "Bucket/存储路径", example = "C:/lemon/data/file/")
    String bucketName;

    /**
     * 域名/访问路径
     */
    @Schema(description = "域名", example = "http://localhost:8000/file")
    String domain;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "存储描述")
    String description;

    /**
     * 是否为默认存储
     */
    @Schema(description = "是否为默认存储", example = "true")
    Boolean isDefault;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    Integer sort;

    @Override
    public Boolean getDisabled() {
        return this.getIsDefault();
    }
}
