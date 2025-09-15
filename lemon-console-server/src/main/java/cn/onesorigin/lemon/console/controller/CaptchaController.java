package cn.onesorigin.lemon.console.controller;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.onesorigin.lemon.console.auth.model.resp.CaptchaResp;
import cn.onesorigin.lemon.console.common.config.captcha.CaptchaProperties;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.GlobalConstants;
import cn.onesorigin.lemon.console.common.constant.OptionConstants;
import cn.onesorigin.lemon.console.common.enums.CaptchaTypeEnum;
import cn.onesorigin.lemon.console.system.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.cache.redisson.util.RedisUtils;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "验证码 API")
@Validated
@RestController
@RequestMapping(path = "/captcha")
public class CaptchaController {

    private final OptionService optionService;
    private final CaptchaProperties captchaProperties;
    private final Font captchaFont;
    private final CodeGenerator codeGenerator;

    @Operation(summary = "获取图片验证码", description = "获取图片验证码（Base64编码，带图片格式：data:image/gif;base64）")
    @GetMapping("/image")
    public CaptchaResp getImageCaptcha() {
        int loginCaptchaEnabled = optionService.getValueByCode2Int(OptionConstants.LOGIN_CAPTCHA_ENABLED);
        if (GlobalConstants.Boolean.NO == loginCaptchaEnabled) {
            return CaptchaResp.builder().isEnabled(false).build();
        }
        var captcha = getAbstractCaptcha();

        String captchaCode = captcha.getCode();
        String imageBase64Data = captcha.getImageBase64Data();

        String uuid = IdUtil.fastUUID();
        String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + uuid;
        long expireTime = LocalDateTimeUtil.toEpochMilli(LocalDateTime.now()
                .plusSeconds(captchaProperties.getExpireSeconds()));
        RedisUtils.set(captchaKey, captchaCode, Duration.ofSeconds(captchaProperties.getExpireSeconds()));
        return CaptchaResp.of(uuid, imageBase64Data, expireTime);
    }

    @NotNull
    private AbstractCaptcha getAbstractCaptcha() {
        var captchaType = captchaProperties.getType();
        int width = captchaProperties.getWidth();
        int height = captchaProperties.getHeight();
        int interfereCount = captchaProperties.getInterfereCount();
        int codeLength = captchaProperties.getCode().getLength();

        AbstractCaptcha captcha;
        if (CaptchaTypeEnum.CIRCLE == captchaType) {
            captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.GIF == captchaType) {
            captcha = CaptchaUtil.createGifCaptcha(width, height, codeLength);
        } else if (CaptchaTypeEnum.LINE == captchaType) {
            captcha = CaptchaUtil.createLineCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.SHEAR == captchaType) {
            captcha = CaptchaUtil.createShearCaptcha(width, height, codeLength, interfereCount);
        } else {
            throw new IllegalArgumentException("Invalid captcha type: " + captchaType);
        }
        captcha.setGenerator(codeGenerator);
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setFont(captchaFont);
        return captcha;
    }
}
