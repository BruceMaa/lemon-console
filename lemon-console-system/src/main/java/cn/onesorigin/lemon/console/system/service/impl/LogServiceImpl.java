package cn.onesorigin.lemon.console.system.service.impl;

import cn.crane4j.annotation.AutoOperate;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.convert.LogConvert;
import cn.onesorigin.lemon.console.system.mapper.LogMapper;
import cn.onesorigin.lemon.console.system.model.entity.LogDO;
import cn.onesorigin.lemon.console.system.model.query.LogQuery;
import cn.onesorigin.lemon.console.system.model.resp.LogDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.LogResp;
import cn.onesorigin.lemon.console.system.model.resp.LoginLogExportResp;
import cn.onesorigin.lemon.console.system.model.resp.OperationLogExportResp;
import cn.onesorigin.lemon.console.system.service.LogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.data.util.QueryWrapperHelper;
import top.continew.starter.excel.util.ExcelUtils;
import top.continew.starter.extension.crud.model.query.PageQuery;
import top.continew.starter.extension.crud.model.query.SortQuery;
import top.continew.starter.extension.crud.model.resp.PageResp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志 业务实现
 *
 * @author BruceMaa
 * @since 2025-11-07 15:04
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogMapper baseMapper;

    @Override
    public PageResp<LogResp> page(LogQuery query, PageQuery pageQuery) {
        QueryWrapper<LogDO> queryWrapper = this.buildQueryWrapper(query);
        QueryWrapperHelper.sort(queryWrapper, pageQuery.getSort());
        IPage<LogResp> page = baseMapper.findPage(new Page<>(pageQuery.getPage(), pageQuery
                .getSize()), queryWrapper);
        return PageResp.build(page);
    }

    @AutoOperate(type = LogDetailResp.class)
    @Override
    public LogDetailResp get(Long id) {
        LogDO logDO = baseMapper.selectById(id);
        CheckUtils.throwIfNotExists(logDO, "LogDO", "ID", id);
        return LogConvert.INSTANCE.toDetailResp(logDO);
    }

    @Override
    public void exportLoginLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response) {
        var list = LogConvert.INSTANCE.toLoginLogExportRespList(this.list(query, sortQuery));
        ExcelUtils.export(list, "导出登录日志数据", LoginLogExportResp.class, response);
    }

    @Override
    public void exportOperationLog(LogQuery query, SortQuery sortQuery, HttpServletResponse response) {
        var list = LogConvert.INSTANCE.toOperationLogExportRespList(this.list(query, sortQuery));
        ExcelUtils.export(list, "导出操作日志数据", OperationLogExportResp.class, response);
    }

    /**
     * 查询列表
     *
     * @param query     查询条件
     * @param sortQuery 排序查询条件
     * @return 列表信息
     */
    private List<LogResp> list(LogQuery query, SortQuery sortQuery) {
        QueryWrapper<LogDO> queryWrapper = this.buildQueryWrapper(query);
        QueryWrapperHelper.sort(queryWrapper, sortQuery.getSort());
        return baseMapper.findList(queryWrapper);
    }

    /**
     * 构建 QueryWrapper
     *
     * @param query 查询条件
     * @return QueryWrapper
     */
    private QueryWrapper<LogDO> buildQueryWrapper(LogQuery query) {
        String description = query.getDescription();
        String module = query.getModule();
        String ip = query.getIp();
        String createdUsername = query.getCreatedUsername();
        DisEnableStatusEnum status = query.getStatus();
        List<LocalDateTime> createTimeList = query.getCreatedAt();
        return new QueryWrapper<LogDO>().and(StrUtil.isNotBlank(description), q -> q.like("t1.description", description)
                        .or()
                        .like("t1.module", description))
                .eq(StrUtil.isNotBlank(module), "t1.module", module)
                .and(StrUtil.isNotBlank(ip), q -> q.like("t1.ip", ip).or().like("t1.address", ip))
                .and(StrUtil.isNotBlank(createdUsername), q -> q.like("t2.username", createdUsername)
                        .or()
                        .like("t2.nickname", createdUsername))
                .eq(status != null, "t1.status", status)
                .between(CollUtil.isNotEmpty(createTimeList), "t1.created_at", CollUtil.getFirst(createTimeList), CollUtil
                        .getLast(createTimeList));
    }
}
