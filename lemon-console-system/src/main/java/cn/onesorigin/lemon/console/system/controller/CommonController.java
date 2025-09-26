package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.model.query.OptionQuery;
import cn.onesorigin.lemon.console.system.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.util.List;

/**
 * 公共 控制器
 *
 * @author BruceMaa
 * @since 2025-09-24 13:46
 */
@Tag(name = "公共接口")
@Slf4j
@RequiredArgsConstructor
@RestController("systemCommonController")
@RequestMapping(path = "/system/common")
public class CommonController {

    private final OptionService optionService;

    @Operation(summary = "查询系统配置参数", description = "查询系统配置参数")
    @SaIgnore
    @GetMapping(path = "/options/site-dict")
    public List<LabelValueResp<String>> findOptionsSiteDict() {
        OptionQuery query = new OptionQuery();
        query.setCategory(OptionCategoryEnum.SITE.name());
        return optionService.list(query)
                .stream()
                .map(option -> new LabelValueResp<>(option.getCode(), option.getValue())
                )
                .toList();
    }
}
