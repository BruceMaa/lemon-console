package cn.onesorigin.lemon.console.system.enums;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.system.model.req.StorageReq;
import cn.onesorigin.lemon.console.system.validation.ValidationGroup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.enums.BaseEnum;
import top.continew.starter.core.util.URLUtils;
import top.continew.starter.core.util.validation.ValidationUtils;

/**
 * 存储类型 枚举
 *
 * @author BruceMaa
 * @since 2025-09-29 09:05
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum StorageTypeEnum implements BaseEnum<Integer> {

    /**
     * 本地存储
     */
    LOCAL(1, "本地存储") {
        @Override
        public void validate(StorageReq req) {
            ValidationUtils.validate(req, ValidationGroup.Storage.Local.class);
            ValidationUtils.throwIf(StrUtil.isNotBlank(req.getDomain()) && !URLUtils.isHttpUrl(req
                    .getDomain()), "访问路径格式不正确");
        }

        @Override
        public void pretreatment(StorageReq req) {
            super.pretreatment(req);
            // 本地存储路径需要以 “/” 结尾
            req.setBucketName(StrUtil.appendIfMissing(req.getBucketName(), StringConstants.SLASH));
        }
    },

    /**
     * 对象存储
     */
    OSS(2, "对象存储") {
        @Override
        public void validate(StorageReq req) {
            ValidationUtils.validate(req, ValidationGroup.Storage.OSS.class);
            ValidationUtils.throwIf(StrUtil.isNotBlank(req.getDomain()) && !ReUtil
                    .isMatch(RegexConstants.URL_HTTP_NOT_IP, req.getDomain()), "域名格式不正确");
        }
    };

    Integer value;
    String description;

    /**
     * 校验
     *
     * @param req 请求参数
     */
    public abstract void validate(StorageReq req);

    /**
     * 处理参数
     *
     * @param req 请求参数
     */
    public void pretreatment(StorageReq req) {
        // 域名需要以 “/” 结尾（x-file-storage 在拼接路径时都是直接 + 拼接，所以规范要求每一级都要以 “/” 结尾，且后面路径不能以 “/” 开头）
        if (StrUtil.isNotBlank(req.getDomain())) {
            req.setDomain(StrUtil.appendIfMissing(req.getDomain(), StringConstants.SLASH));
        }
    }
}
