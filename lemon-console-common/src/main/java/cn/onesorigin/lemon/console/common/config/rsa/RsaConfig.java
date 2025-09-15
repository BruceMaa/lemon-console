package cn.onesorigin.lemon.console.common.config.rsa;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.continew.starter.core.constant.PropertiesConstants;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.security.crypto.autoconfigure.CryptoProperties;

/**
 * RSA 配置
 *
 * @author BruceMaa
 * @since 2025-09-17 17:11
 */
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = PropertiesConstants.SECURITY_CRYPTO, name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
@Configuration
public class RsaConfig {

    private final CryptoProperties cryptoProperties;

    @Bean
    @ConditionalOnMissingBean
    public RSA rsa() {
        ValidationUtils.throwIfBlank(cryptoProperties.getPublicKey(), "请配置 RSA 公钥");
        ValidationUtils.throwIfBlank(cryptoProperties.getPrivateKey(), "请配置 RSA 公钥");
        return SecureUtil.rsa(cryptoProperties.getPrivateKey(), cryptoProperties.getPublicKey());
    }
}
