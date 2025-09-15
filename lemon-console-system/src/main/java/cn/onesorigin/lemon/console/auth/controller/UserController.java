package cn.onesorigin.lemon.console.auth.controller;

import cn.onesorigin.lemon.console.auth.model.query.UserQuery;
import cn.onesorigin.lemon.console.auth.model.req.UserReq;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserDetailResp;
import cn.onesorigin.lemon.console.auth.model.resp.user.UserResp;
import cn.onesorigin.lemon.console.auth.service.UserService;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

/**
 * 用户管理 API
 *
 * @author BruceMaa
 * @since 2025-09-17 18:30
 */
@Tag(name = "用户管理")
@RequiredArgsConstructor
@Validated
@RestController
@CrudRequestMapping(value = "/system/user", api = {
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
}
