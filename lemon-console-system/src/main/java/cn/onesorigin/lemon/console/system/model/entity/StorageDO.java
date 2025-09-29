package cn.onesorigin.lemon.console.system.model.entity;

import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.StorageTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.security.crypto.annotation.FieldEncrypt;

import java.net.URL;

/**
 * 存储 实体
 *
 * @author BruceMaa
 * @since 2025-09-29 09:20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_storage")
public class StorageDO extends BaseDO {

    /**
     * 名称
     */
    String name;

    /**
     * 编码
     */
    String code;

    /**
     * 类型
     */
    StorageTypeEnum type;

    /**
     * Access Key
     */
    @FieldEncrypt
    String accessKey;

    /**
     * Secret Key
     */
    @FieldEncrypt
    String secretKey;

    /**
     * Endpoint
     */
    String endpoint;

    /**
     * Bucket
     */
    String bucketName;

    /**
     * 域名
     */
    String domain;

    /**
     * 描述
     */
    String description;

    /**
     * 是否为默认存储
     */
    Boolean isDefault;

    /**
     * 排序
     */
    Integer sort;

    /**
     * 状态
     */
    DisEnableStatusEnum status;

    /**
     * 获取 URL 前缀
     * <p>
     * LOCAL：{@link #domain}/ <br />
     * OSS：域名不为空：{@link #domain}/；Endpoint 不是
     * IP：http(s)://{@link #bucketName}.{@link #endpoint}/；否则：{@link #endpoint}/{@link #bucketName}/
     * </p>
     *
     * @return URL 前缀
     */
    public String getUrlPrefix() {
        if (StrUtil.isNotBlank(this.domain) || StorageTypeEnum.LOCAL.equals(this.type)) {
            return StrUtil.appendIfMissing(this.domain, StringConstants.SLASH);
        }
        URL url = URLUtil.url(this.endpoint);
        String host = url.getHost();
        // IP（MinIO） 则拼接 BucketName
        if (ReUtil.isMatch(RegexPool.IPV4, host) || ReUtil.isMatch(RegexPool.IPV6, host)) {
            return StrUtil
                    .appendIfMissing(this.endpoint, StringConstants.SLASH) + this.bucketName + StringConstants.SLASH;
        }
        return "%s://%s.%s/".formatted(url.getProtocol(), this.bucketName, host);
    }
}
