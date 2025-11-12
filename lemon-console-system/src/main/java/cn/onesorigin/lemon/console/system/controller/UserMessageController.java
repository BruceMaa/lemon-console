package cn.onesorigin.lemon.console.system.controller;

import cn.onesorigin.lemon.console.common.context.UserContextHolder;
import cn.onesorigin.lemon.console.system.enums.NoticeMethodEnum;
import cn.onesorigin.lemon.console.system.enums.NoticeScopeEnum;
import cn.onesorigin.lemon.console.system.model.query.MessageQuery;
import cn.onesorigin.lemon.console.system.model.query.NoticeQuery;
import cn.onesorigin.lemon.console.system.model.resp.*;
import cn.onesorigin.lemon.console.system.service.MessageService;
import cn.onesorigin.lemon.console.system.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.req.IdsReq;
import top.continew.starter.extension.crud.model.resp.BasePageResp;
import top.continew.starter.extension.crud.model.resp.PageResp;
import top.continew.starter.log.annotation.Log;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 个人消息 控制器
 *
 * @author BruceMaa
 * @since 2025-11-12 09:17
 */
@Tag(name = "个人消息")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user/messages")
public class UserMessageController {

    private final NoticeService noticeService;
    private final MessageService messageService;

    @Log(ignore = true)
    @Operation(summary = "查询未读消息数量", description = "查询当前用户的未读消息数量")
    @Parameter(name = "isDetail", description = "是否查询详情", example = "true", in = ParameterIn.QUERY)
    @GetMapping("/unread")
    public MessageUnreadResp countUnreadMessage(@RequestParam(required = false, defaultValue = "false") Boolean isDetail) {
        return messageService.countUnreadByUserId(UserContextHolder.getUserId(), isDetail);
    }

    @Operation(summary = "分页查询消息列表", description = "分页查询消息列表")
    @GetMapping
    public PageResp<MessageResp> page(@Valid MessageQuery query, @Valid PageQuery pageQuery) {
        query.setUserId(UserContextHolder.getUserId());
        return messageService.page(query, pageQuery);
    }

    @Operation(summary = "查询消息", description = "查询消息详情")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @GetMapping("/{id}")
    public MessageDetailResp detail(@PathVariable Long id) {
        MessageDetailResp detail = messageService.findDetailById(id);
        CheckUtils.throwIf(detail == null || (NoticeScopeEnum.USER.equals(detail.getScope()) && !detail.getUsers()
                .contains(UserContextHolder.getUserId().toString())), "消息不存在或无权限访问");
        messageService.readMessage(Collections.singletonList(id), UserContextHolder.getUserId());
        Objects.requireNonNull(detail).setIsRead(true);
        return detail;
    }

    @Operation(summary = "删除消息", description = "删除消息")
    @DeleteMapping
    public void delete(@RequestBody @Valid IdsReq req) {
        messageService.delete(req.getIds());
    }

    @Operation(summary = "消息标记为已读", description = "将消息标记为已读状态")
    @PatchMapping("/read")
    public void read(@RequestBody @Valid IdsReq req) {
        messageService.readMessage(req.getIds(), UserContextHolder.getUserId());
    }

    @Operation(summary = "消息全部已读", description = "将所有消息标记为已读状态")
    @PatchMapping("/readAll")
    public void readAll() {
        messageService.readMessage(null, UserContextHolder.getUserId());
    }

    @Log(ignore = true)
    @Operation(summary = "查询未读公告数量", description = "查询当前用户的未读公告数量")
    @GetMapping("/notices/unread")
    public NoticeUnreadCountResp countUnreadNotice() {
        List<Long> list = noticeService.findUnreadIdsByUserId(null, UserContextHolder.getUserId());
        return new NoticeUnreadCountResp(list.size());
    }

    @Log(ignore = true)
    @Operation(summary = "查询未读公告", description = "查询当前用户的未读公告")
    @Parameter(name = "method", description = "通知方式", example = "LOGIN_POPUP", in = ParameterIn.PATH)
    @GetMapping("/notices/unread/{method}")
    public List<Long> listUnreadNotice(@PathVariable String method) {
        return noticeService.findUnreadIdsByUserId(NoticeMethodEnum.valueOf(method), UserContextHolder.getUserId());
    }

    @Operation(summary = "分页查询公告列表", description = "分页查询公告列表")
    @GetMapping("/notices")
    public BasePageResp<NoticeResp> pageNotice(@Valid NoticeQuery query, @Valid PageQuery pageQuery) {
        query.setUserId(UserContextHolder.getUserId());
        return noticeService.page(query, pageQuery);
    }

    @Operation(summary = "查询公告", description = "查询公告详情")
    @Parameter(name = "id", description = "ID", example = "1", in = ParameterIn.PATH)
    @GetMapping("/notices/{id}")
    public NoticeDetailResp getNotice(@PathVariable Long id) {
        NoticeDetailResp detail = noticeService.get(id);
        CheckUtils.throwIf(detail == null || (NoticeScopeEnum.USER.equals(detail.getNoticeScope()) && !detail
                .getNoticeUsers()
                .contains(UserContextHolder.getUserId().toString())), "公告不存在或无权限访问");
        noticeService.readNotice(id, UserContextHolder.getUserId());
        return detail;
    }
}
