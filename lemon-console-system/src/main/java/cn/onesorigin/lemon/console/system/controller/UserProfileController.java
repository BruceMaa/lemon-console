package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.model.req.UserBasicInfoUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.UserEmailUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.UserPasswordUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.UserPhoneUpdateReq;
import cn.onesorigin.lemon.console.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人信息 控制器
 *
 * @author BruceMaa
 * @since 2025-09-26 15:02
 */
@Tag(name = "个人信息")
@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user/profile")
public class UserProfileController {

    private final UserService userService;

    private static final String DECRYPT_FAILED = "当前密码解密失败";

    @Operation(summary = "修改基础信息", description = "修改基础信息")
    @PatchMapping(path = "/basic/info")
    public void updateBasicInfo(@RequestBody @Valid UserBasicInfoUpdateReq updateReq) {
        userService.updateBasicInfo(updateReq, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改密码", description = "修改用户登录密码")
    @PatchMapping(path = "/password")
    public void updatePassword(@RequestBody @Valid UserPasswordUpdateReq updateReq) {
        String oldPassword = SecureUtils.decryptPasswordByRsaPrivateKey(updateReq.getOldPassword(), DECRYPT_FAILED);
        String newPassword = SecureUtils.decryptPasswordByRsaPrivateKey(updateReq.getNewPassword(), "新密码解密失败");
        userService.updatePassword(oldPassword, newPassword, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改手机号", description = "修改手机号")
    @PatchMapping("/phone")
    public void updatePhone(@RequestBody @Valid UserPhoneUpdateReq updateReq) {
        String oldPassword = SecureUtils.decryptPasswordByRsaPrivateKey(updateReq.getOldPassword(), DECRYPT_FAILED);
        userService.updatePhone(updateReq.getPhone(), oldPassword, UserContextHolder.getUserId());
    }

    @Operation(summary = "修改邮箱", description = "修改用户邮箱")
    @PatchMapping("/email")
    public void updateEmail(@RequestBody @Valid UserEmailUpdateReq updateReq) {
        String oldPassword = SecureUtils.decryptPasswordByRsaPrivateKey(updateReq.getOldPassword(), DECRYPT_FAILED);
        userService.updateEmail(updateReq.getEmail(), oldPassword, UserContextHolder.getUserId());
    }
}
