package cn.onesorigin.lemon.console.common.config.captcha;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * 验证码自动装配配置
 *
 * @author BruceMaa
 * @since 2025-09-02 17:19
 */
@RequiredArgsConstructor
@Configuration
public class CaptchaConfig {

    private final CaptchaProperties captchaProperties;

    /**
     * @return 验证码文字生成器
     */
    @Bean
    public CodeGenerator codeGenerator() {
        var codeType = captchaProperties.getCode().getType();
        var codeLength = captchaProperties.getCode().getLength();
        if ("math".equalsIgnoreCase(codeType)) {
            return new MathGenerator(codeLength);
        } else if ("random".equalsIgnoreCase(codeType)) {
            return new RandomGenerator(codeLength);
        } else {
            throw new IllegalArgumentException("Invalid captcha codegen type: " + codeType);
        }
    }

    /**
     * @return 验证码字体
     */
    @Bean
    public Font captchaFont() {
        var fontName = captchaProperties.getFont().getName();
        var fontStyle = captchaProperties.getFont().getStyle();
        var fontSize = captchaProperties.getFont().getSize();
        return new Font(fontName, fontStyle, fontSize);
    }
}
