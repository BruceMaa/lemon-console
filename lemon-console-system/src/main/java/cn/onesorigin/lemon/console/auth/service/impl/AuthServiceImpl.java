package cn.onesorigin.lemon.console.auth.service.impl;

import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.auth.model.resp.LoginResp;
import cn.onesorigin.lemon.console.auth.service.AuthService;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.common.constant.GlobalConstants;
import cn.onesorigin.lemon.console.common.constant.OptionConstants;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.model.resp.ClientResp;
import cn.onesorigin.lemon.console.system.service.ClientService;
import cn.onesorigin.lemon.console.system.service.OptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.cache.redisson.util.RedisUtils;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.core.util.validation.Validator;

/**
 * 认证业务实现
 *
 * @author BruceMaa
 * @since 2025-09-04 16:49
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final ClientService clientService;
    private final OptionService optionService;
    private static final String CLIENT_ID = "clientId";

    @Override
    public LoginResp login(AccountLoginReq req, HttpServletRequest request) {
        // 校验客户端
        var client = clientService.getByClientId(req.getClientId());
        ValidationUtils.throwIfNull(client, "客户端不存在");
        ValidationUtils.throwIf(client.getStatus() == DisEnableStatusEnum.DISABLE, "客户端已禁用");
        // 登录前置处理
        this.preLogin(req, client, request);
        // 登录
        LoginResp loginResp = this.login(req, client, request);
        // 登录后置处理
        this.postLogin(req, client, request);
        return loginResp;
    }

    private void preLogin(AccountLoginReq req, ClientResp client, HttpServletRequest request) {
        // 参数校验
        Validator.validate(req);

        // 校验验证码
        int loginCaptchaEnabled = optionService.getValueByCode2Int(OptionConstants.LOGIN_CAPTCHA_ENABLED);
        if (GlobalConstants.Boolean.YES == loginCaptchaEnabled) {
            ValidationUtils.throwIfBlank(req.getCaptchaCode(), "验证码不能为空");
            ValidationUtils.throwIfBlank(req.getCaptchaKey(), "验证码标识不能为空");
            String captchaKey = CacheConstants.CAPTCHA_KEY_PREFIX + req.getCaptchaKey();
            String captcha = RedisUtils.get(captchaKey);
            ValidationUtils.throwIfBlank(captcha, "验证码已失效");
            RedisUtils.delete(captchaKey);
            ValidationUtils.throwIfNotEqualIgnoreCase(req.getCaptchaCode(), captcha, "验证码不正确");
        }
    }

    private void postLogin(AccountLoginReq req, ClientResp client, HttpServletRequest request) {

    }

    private LoginResp login(AccountLoginReq req, ClientResp client, HttpServletRequest request) {
        // 解密密码
        String password = SecureUtils.decryptPasswordByRsaPrivateKey(req.getPassword(), "密码解密失败");
        // 验证用户名密码
        String username = req.getUsername();
        return null;
    }
}
