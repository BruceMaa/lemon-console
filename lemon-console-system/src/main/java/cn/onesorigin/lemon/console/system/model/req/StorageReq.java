package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.StorageTypeEnum;
import cn.onesorigin.lemon.console.system.validation.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 存储创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-29 09:07
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "存储创建或修改请求参数")
public class StorageReq {

    /**
     * 名称
     */
    @Schema(description = "名称", example = "S3对象存储")
    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过 {max} 个字符")
    String name;

    /**
     * 编码
     */
    @Schema(description = "编码", example = "s3_aliyun")
    @NotBlank(message = "编码不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_CODE, message = "编码长度为 2-30 个字符，支持大小写字母、数字、下划线，以字母开头")
    String code;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "2")
    @NotNull(message = "类型无效")
    StorageTypeEnum type;

    /**
     * Access Key
     */
    @Schema(description = "Access Key", example = "LBAI4Fp4dXYcZamU5EXTBdTa")
    @Size(max = 255, message = "Access Key长度不能超过 {max} 个字符")
    @NotBlank(message = "Access Key不能为空", groups = ValidationGroup.Storage.OSS.class)
    String accessKey;

    /**
     * Secret Key
     */
    @Schema(description = "Secret Key", example = "RSA 公钥加密的 Secret Key")
    String secretKey;

    /**
     * Endpoint
     */
    @Schema(description = "Endpoint", example = "http://oss-cn-shanghai.aliyuncs.com")
    @Size(max = 255, message = "Endpoint长度不能超过 {max} 个字符")
    @NotBlank(message = "Endpoint不能为空", groups = ValidationGroup.Storage.OSS.class)
    @Pattern(regexp = RegexConstants.URL_HTTP, message = "Endpoint格式不正确", groups = ValidationGroup.Storage.OSS.class)
    String endpoint;

    /**
     * Bucket/存储路径
     */
    @Schema(description = "Bucket/存储路径", example = "continew-admin")
    @Size(max = 255, message = "Bucket长度不能超过 {max} 个字符", groups = ValidationGroup.Storage.OSS.class)
    @Size(max = 255, message = "存储路径长度不能超过 {max} 个字符", groups = ValidationGroup.Storage.Local.class)
    @NotBlank(message = "Bucket不能为空", groups = ValidationGroup.Storage.OSS.class)
    @NotBlank(message = "存储路径不能为空", groups = ValidationGroup.Storage.Local.class)
    String bucketName;

    /**
     * 域名/访问路径
     */
    @Schema(description = "域名/访问路径", example = "https://continew-admin.file.continew.top/")
    @Size(max = 255, message = "域名长度不能超过 {max} 个字符", groups = ValidationGroup.Storage.OSS.class)
    @Size(max = 255, message = "访问路径长度不能超过 {max} 个字符", groups = ValidationGroup.Storage.Local.class)
    @NotBlank(message = "访问路径不能为空", groups = ValidationGroup.Storage.Local.class)
    String domain;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    Integer sort;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "存储描述")
    @Size(max = 200, message = "描述长度不能超过 {max} 个字符")
    String description;

    /**
     * 状态
     */
    @Schema(description = "状态", example = "1")
    DisEnableStatusEnum status;

    /**
     * 是否为默认存储
     */
    @JsonIgnore
    Boolean isDefault;
}
