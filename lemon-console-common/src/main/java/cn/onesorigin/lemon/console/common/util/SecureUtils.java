package cn.onesorigin.lemon.console.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.continew.starter.core.util.ExceptionUtils;
import top.continew.starter.core.util.validation.ValidationUtils;

/**
 * @author BruceMaa
 * @since 2025-09-17 16:46
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecureUtils {

    /**
     * 公钥加密
     *
     * @param data 要加密的内容
     * @return 加密后的内容
     */
    public static String encryptByRsaPublicKey(String data) {
        var rsa = SpringUtil.getBean(RSA.class);
        return new String(rsa.encrypt(data, KeyType.PublicKey));
    }

    /**
     * 私钥解密
     *
     * @param data 要解密的内容（Base64 加密过）
     * @return 解密后的内容
     */
    public static String decryptByRsaPrivateKey(String data) {
        var rsa = SpringUtil.getBean(RSA.class);
        return new String(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey));
    }

    /**
     * 解密密码
     *
     * @param encryptedPasswordByRsaPublicKey 密码（已被 Rsa 公钥加密）
     * @param errorMsg                        错误信息
     * @return 解密后的密码
     */
    public static String decryptPasswordByRsaPrivateKey(String encryptedPasswordByRsaPublicKey, String errorMsg) {
        return decryptPasswordByRsaPrivateKey(encryptedPasswordByRsaPublicKey, errorMsg, false);
    }

    /**
     * 解密密码
     *
     * @param encryptedPasswordByRsaPublicKey 密码（已被 Rsa 公钥加密）
     * @param errorMsg                        错误信息
     * @param isVerifyPattern                 是否验证密码格式
     * @return 解密后的密码
     */
    public static String decryptPasswordByRsaPrivateKey(String encryptedPasswordByRsaPublicKey,
                                                        String errorMsg,
                                                        boolean isVerifyPattern) {
        var rawPassword = ExceptionUtils.exToNull(() -> decryptByRsaPrivateKey(encryptedPasswordByRsaPublicKey));
        ValidationUtils.throwIfBlank(rawPassword, errorMsg);
        if (isVerifyPattern) {
            ValidationUtils.throwIf(
                    !ReUtil.isMatch(RegexConstants.PASSWORD, rawPassword),
                    "密码长度为 8-32 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字"
            );
        }
        return rawPassword;
    }
}
