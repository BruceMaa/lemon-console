package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.model.query.RoleQuery;
import cn.onesorigin.lemon.console.system.model.query.RoleUserQuery;
import cn.onesorigin.lemon.console.system.model.req.RolePermissionUpdateReq;
import cn.onesorigin.lemon.console.system.model.req.RoleReq;
import cn.onesorigin.lemon.console.system.model.resp.RoleDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.RolePermissionResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleResp;
import cn.onesorigin.lemon.console.system.model.resp.RoleUserResp;
import cn.onesorigin.lemon.console.system.service.MenuService;
import cn.onesorigin.lemon.console.system.service.RoleService;
import cn.onesorigin.lemon.console.system.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.util.List;

/**
 * 角色管理 控制器
 *
 * @author BruceMaa
 * @since 2025-09-19 13:24
 */
@Tag(name = "角色管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/roles", api = {
        Api.LIST,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE,
        Api.DICT
})
public class RoleController extends BaseController<RoleService, RoleResp, RoleDetailResp, RoleQuery, RoleReq> {

    private final UserRoleService userRoleService;
    private final MenuService menuService;

    @Operation(summary = "查询角色权限树列表", description = "查询角色权限树列表")
    @SaCheckPermission("system:roles:listPermission")
    @GetMapping("/permission/tree")
    public List<RolePermissionResp> listPermissionTree() {
        List<Tree<Long>> treeList = menuService.tree(null, null, false);
        return BeanUtil.copyToList(treeList, RolePermissionResp.class);
    }

    @Operation(summary = "修改权限", description = "修改角色的功能权限")
    @SaCheckPermission("system:roles:updatePermission")
    @PatchMapping("/{id}/permissions")
    public void updatePermission(@PathVariable("id") Long id, @RequestBody @Valid RolePermissionUpdateReq req) {
        baseService.updatePermission(id, req);
    }

    @Operation(summary = "分页查询关联用户", description = "分页查询角色关联的用户列表")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:roles:list")
    @GetMapping("/{id}/users")
    public PageResp<RoleUserResp> pageUser(@PathVariable("id") Long id,
                                           @Valid RoleUserQuery query,
                                           @Valid PageQuery pageQuery) {
        query.setRoleId(id);
        return userRoleService.pageUser(query, pageQuery);
    }

    @Operation(summary = "分配用户", description = "批量分配角色给用户")
    @SaCheckPermission("system:roles:assign")
    @PatchMapping("/{id}/users")
    public void assignToUsers(@PathVariable("id") Long id,
                              @RequestBody @NotEmpty(message = "用户ID列表不能为空") List<Long> userIds) {
        baseService.assignToUsers(id, userIds);
    }

    @Operation(summary = "取消分配用户", description = "批量取消分配角色给用户")
    @SaCheckPermission("system:roles:unassign")
    @DeleteMapping("/user")
    public void unassignFromUsers(@RequestBody @NotEmpty(message = "用户列表不能为空") List<Long> userRoleIds) {
        userRoleService.deleteByIds(userRoleIds);
    }

    @Operation(summary = "查询关联用户ID", description = "查询角色关联的用户ID列表")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @SaCheckPermission("system:roles:list")
    @GetMapping("/{id}/user-id")
    public List<Long> listUserId(@PathVariable("id") Long id) {
        return userRoleService.findUserIdsByRoleId(id);
    }
}
