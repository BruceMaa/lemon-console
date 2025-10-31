package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.model.query.UserQuery;
import cn.onesorigin.lemon.console.system.model.req.UserImportReq;
import cn.onesorigin.lemon.console.system.model.req.UserPasswordResetReq;
import cn.onesorigin.lemon.console.system.model.req.UserReq;
import cn.onesorigin.lemon.console.system.model.req.UserRoleUpdateReq;
import cn.onesorigin.lemon.console.system.model.resp.UserDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.UserImportParseResp;
import cn.onesorigin.lemon.console.system.model.resp.UserImportResp;
import cn.onesorigin.lemon.console.system.model.resp.UserResp;
import cn.onesorigin.lemon.console.system.service.UserService;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

import java.io.IOException;

/**
 * 用户管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-17 18:30
 */
@Tag(name = "用户管理")
@RequiredArgsConstructor
@Validated
@RestController
@CrudRequestMapping(value = "/system/users", api = {
        Api.PAGE,
        Api.LIST,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE,
        Api.EXPORT,
        Api.DICT
})
public class UserController extends BaseController<UserService, UserResp, UserDetailResp, UserQuery, UserReq> {

    @Operation(summary = "下载导入模板", description = "下载导入模板")
    @SaCheckPermission("system:users:import")
    @GetMapping(value = "/import-template", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        baseService.downloadImportTemplate(response);
    }

    @Operation(summary = "解析导入数据", description = "解析导入数据")
    @SaCheckPermission("system:users:import")
    @PostMapping("/import/parse")
    public UserImportParseResp parseImport(@RequestPart @NotNull(message = "文件不能为空") MultipartFile file) {
        ValidationUtils.throwIf(file::isEmpty, "文件不能为空");
        return baseService.parseImport(file);
    }

    @Operation(summary = "导入数据", description = "导入数据")
    @SaCheckPermission("system:users:import")
    @PostMapping(value = "/import")
    public UserImportResp importUser(@RequestBody @Valid UserImportReq req) {
        return baseService.importUser(req);
    }

    @Operation(summary = "重置密码", description = "重置用户登录密码")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:users:resetPwd")
    @PatchMapping("/{id}/password")
    public void resetPassword(@RequestBody @Valid UserPasswordResetReq req, @PathVariable Long id) {
        String newPassword = SecureUtils.decryptPasswordByRsaPrivateKey(req.getNewPassword(), "新密码解密失败", true);
        req.setNewPassword(newPassword);
        baseService.resetPassword(req, id);
    }

    @Operation(summary = "分配角色", description = "为用户新增或移除角色")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:users:updateRole")
    @PatchMapping("/{id}/roles")
    public void updateRoles(@RequestBody @Valid UserRoleUpdateReq updateReq, @PathVariable Long id) {
        baseService.updateRole(updateReq, id);
    }
}
