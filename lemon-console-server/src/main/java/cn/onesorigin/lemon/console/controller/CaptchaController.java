package cn.onesorigin.lemon.console.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.onesorigin.lemon.console.auth.model.resp.CaptchaResp;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.GlobalConstants;
import cn.onesorigin.lemon.console.common.constant.OptionConstants;
import cn.onesorigin.lemon.console.system.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.captcha.graphic.core.GraphicCaptchaService;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 验证码 控制器
 *
 * @author BruceMaa
 * @since 2025-09-18 11:17
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "验证码 API")
@SaIgnore
@Validated
@RestController
@RequestMapping(path = "/captcha")
public class CaptchaController {

    private final OptionService optionService;
    private final GraphicCaptchaService graphicCaptchaService;

    @Value("${lemon.console.captcha.expiration-in-seconds:120}")
    private long captchaExpirationInSeconds;

    @Operation(summary = "获取图片验证码", description = "获取图片验证码（Base64编码，带图片格式：data:image/gif;base64）")
    @GetMapping("/image")
    public CaptchaResp getImageCaptcha() {
        int loginCaptchaEnabled = optionService.getValueByCode2Int(OptionConstants.LOGIN_CAPTCHA_ENABLED);
        if (GlobalConstants.Boolean.NO == loginCaptchaEnabled) {
            return CaptchaResp.builder().isEnabled(false).build();
        }
        var captcha = graphicCaptchaService.getCaptcha();

        String captchaCode = captcha.text();
        String imageBase64Data = captcha.toBase64();

        String captchaKey = IdUtil.fastUUID();
        String captchaKeyCacheKey = CacheConstants.CAPTCHA_KEY_PREFIX + captchaKey;
        long expireTime = LocalDateTimeUtil.toEpochMilli(LocalDateTime.now()
                .plusSeconds(captchaExpirationInSeconds));
        RedisUtils.set(captchaKeyCacheKey, captchaCode, Duration.ofSeconds(captchaExpirationInSeconds));
        return CaptchaResp.of(captchaKey, imageBase64Data, expireTime);
    }

}
