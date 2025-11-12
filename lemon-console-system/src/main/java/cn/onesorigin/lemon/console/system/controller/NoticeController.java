package cn.onesorigin.lemon.console.system.controller;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.system.enums.NoticeMethodEnum;
import cn.onesorigin.lemon.console.system.model.query.NoticeQuery;
import cn.onesorigin.lemon.console.system.model.req.NoticeReq;
import cn.onesorigin.lemon.console.system.model.resp.NoticeDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.NoticeResp;
import cn.onesorigin.lemon.console.system.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.core.util.validation.ValidationUtils;
import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 公告 控制器
 *
 * @author BruceMaa
 * @since 2025-11-11 09:46
 */
@Tag(name = "公告管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrudRequestMapping(value = "/system/notices", api = {
        Api.PAGE,
        Api.GET,
        Api.CREATE,
        Api.UPDATE,
        Api.BATCH_DELETE
})
public class NoticeController extends BaseController<NoticeService, NoticeResp, NoticeDetailResp, NoticeQuery, NoticeReq> {

    @Override
    public void preHandle(CrudApi crudApi, Object[] args, Method targetMethod, Class<?> targetClass) throws Exception {
        super.preHandle(crudApi, args, targetMethod, targetClass);
        Api api = crudApi.value();
        if (!(Api.CREATE.equals(api) || Api.UPDATE.equals(api))) {
            return;
        }
        NoticeReq req = (NoticeReq) args[0];
        // 校验通知方式
        List<Integer> noticeMethods = req.getNoticeMethods();
        if (CollUtil.isNotEmpty(noticeMethods)) {
            List<Integer> validMethods = Arrays.stream(NoticeMethodEnum.values())
                    .map(NoticeMethodEnum::getValue)
                    .toList();
            noticeMethods.forEach(method -> ValidationUtils.throwIf(!validMethods
                    .contains(method), "通知方式 【{}】 不正确", method));
        }
    }
}
