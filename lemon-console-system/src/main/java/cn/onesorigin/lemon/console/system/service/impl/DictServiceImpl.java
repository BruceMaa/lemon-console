package cn.onesorigin.lemon.console.system.service.impl;

import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.system.mapper.DictMapper;
import cn.onesorigin.lemon.console.system.model.entity.DictDO;
import cn.onesorigin.lemon.console.system.model.query.DictQuery;
import cn.onesorigin.lemon.console.system.model.req.DictReq;
import cn.onesorigin.lemon.console.system.model.resp.DictResp;
import cn.onesorigin.lemon.console.system.service.DictItemService;
import cn.onesorigin.lemon.console.system.service.DictService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.extension.crud.model.resp.LabelValueResp;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 字典 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-18 10:49
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, DictDO, DictResp, DictResp, DictQuery, DictReq> implements DictService {

    private final DictItemService dictItemService;

    @Override
    protected void beforeCreate(DictReq req) {
        this.checkNameRepeat(req.getName(), null);
        this.checkCodeRepeat(req.getCode(), null);
    }

    @Override
    protected void beforeUpdate(DictReq req, Long id) {
        this.checkNameRepeat(req.getName(), id);
        DictDO oldDict = super.getById(id);
        CheckUtils.throwIfNotEqual(req.getCode(), oldDict.getCode(), "不允许修改字典编码");
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        List<DictDO> list = baseMapper.lambdaQuery()
                .select(DictDO::getName, DictDO::getIsSystem)
                .in(DictDO::getId, ids)
                .list();
        Optional<DictDO> isSystemData = list.stream().filter(DictDO::getIsSystem).findFirst();
        CheckUtils.throwIf(isSystemData::isPresent, "所选字典 【{}】 是系统内置字典，不允许删除", isSystemData.orElseGet(DictDO::new)
                .getName());
        dictItemService.deleteByDictIds(ids);
    }

    @Override
    public List<LabelValueResp<String>> findEnumDicts() {
        List<String> enumDictNames = dictItemService.findEnumDictNames();
        return CollUtils.mapToList(enumDictNames, name -> new LabelValueResp<>(name, name));
    }

    /**
     * 检查名称是否重复
     *
     * @param name 名称
     * @param id   ID
     */
    private void checkNameRepeat(String name, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(DictDO::getName, name)
                .ne(id != null, DictDO::getId, id)
                .exists(), "名称为 【{}】 的字典已存在", name);
    }

    /**
     * 检查编码是否重复
     *
     * @param code 编码
     * @param id   ID
     */
    private void checkCodeRepeat(String code, Long id) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(DictDO::getCode, code)
                .ne(id != null, DictDO::getId, id)
                .exists(), "编码为 【{}】 的字典已存在", code);
    }
}
