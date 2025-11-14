package cn.onesorigin.lemon.console.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.onesorigin.lemon.console.common.constant.CacheConstants;
import cn.onesorigin.lemon.console.system.enums.OptionCategoryEnum;
import cn.onesorigin.lemon.console.system.model.query.OptionQuery;
import cn.onesorigin.lemon.console.system.service.DictItemService;
import cn.onesorigin.lemon.console.system.service.OptionService;
import com.alicp.jetcache.anno.Cached;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;
import top.continew.starter.log.annotation.Log;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公共 控制器
 *
 * @author BruceMaa
 * @since 2025-09-24 13:46
 */
@Tag(name = "公共接口")
@Slf4j
@RequiredArgsConstructor
@Log(ignore = true)
@RestController("systemCommonController")
@RequestMapping(path = "/system/common")
public class CommonController {

    private final OptionService optionService;
    private final DictItemService dictItemService;

    @Operation(summary = "查询系统配置参数", description = "查询系统配置参数")
    @SaIgnore
    @Cached(key = "'SITE'", name = CacheConstants.OPTION_KEY_PREFIX)
    @GetMapping(path = "/options/site-dict")
    public List<LabelValueResp<String>> findOptionsSiteDict() {
        OptionQuery query = new OptionQuery();
        query.setCategory(OptionCategoryEnum.SITE.name());
        return optionService.list(query)
                .stream()
                .map(option -> new LabelValueResp<>(option.getCode(), option.getValue())
                )
                .collect(Collectors.toList());
    }

    @Operation(summary = "查询字典", description = "查询字典列表")
    @Parameter(name = "code", description = "字典编码", in = ParameterIn.PATH)
    @GetMapping(path = "/dicts/{code}")
    public List<LabelValueResp<Serializable>> findDictItemsByCode(@PathVariable String code) {
        return dictItemService.findByDictCode(code);
    }
}
