package cn.onesorigin.lemon.console.config.log;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import cn.onesorigin.lemon.console.auth.constant.AuthConstants;
import cn.onesorigin.lemon.console.auth.model.req.AccountLoginReq;
import cn.onesorigin.lemon.console.system.enums.LogStatusEnum;
import cn.onesorigin.lemon.console.system.mapper.LogMapper;
import cn.onesorigin.lemon.console.system.model.entity.LogDO;
import cn.onesorigin.lemon.console.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.ExceptionUtils;
import top.continew.starter.core.util.StrUtils;
import top.continew.starter.log.dao.LogDao;
import top.continew.starter.log.model.LogRecord;
import top.continew.starter.log.model.LogRequest;
import top.continew.starter.log.model.LogResponse;
import top.continew.starter.trace.autoconfigure.TraceProperties;
import top.continew.starter.web.model.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

/**
 * 日志持久层接口本地实现类
 *
 * @author BruceMaa
 * @since 2025-11-07 14:20
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LogDaoLocalImpl implements LogDao {

    private final UserService userService;
    private final LogMapper logMapper;
    private final TraceProperties traceProperties;

    @Async
    @Override
    public void add(LogRecord logRecord) {
        LogDO logDO = new LogDO();
        // 设置请求信息
        LogRequest logRequest = logRecord.getRequest();
        this.setRequest(logDO, logRequest);
        // 设置响应信息
        LogResponse logResponse = logRecord.getResponse();
        this.setResponse(logDO, logResponse);
        // 设置基本信息
        logDO.setDescription(logRecord.getDescription());
        logDO.setModule(StrUtils.blankToDefault(logRecord.getModule(), null, m -> m
                .replace("API", StringConstants.EMPTY)
                .trim()));
        logDO.setTimeTaken(logRecord.getTimeTaken().toMillis());
        logDO.setCreatedAt(LocalDateTime.ofInstant(logRecord.getTimestamp(), ZoneId.systemDefault()));
        // 设置操作人
        this.setCreateUser(logDO, logRequest, logResponse);
        // 保存记录
        logMapper.insert(logDO);
    }

    /**
     * 设置请求信息
     *
     * @param logDO      日志信息
     * @param logRequest 请求信息
     */
    private void setRequest(LogDO logDO, LogRequest logRequest) {
        logDO.setRequestMethod(logRequest.getMethod());
        logDO.setRequestUrl(logRequest.getUrl().toString());
        logDO.setRequestHeaders(JSONUtil.toJsonStr(logRequest.getHeaders()));
        logDO.setRequestBody(logRequest.getBody());
        logDO.setIp(logRequest.getIp());
        logDO.setAddress(logRequest.getAddress());
        logDO.setBrowser(logRequest.getBrowser());
        logDO.setOs(StrUtil.subBefore(logRequest.getOs(), " or", false));
    }

    /**
     * 设置响应信息
     *
     * @param logDO       日志信息
     * @param logResponse 响应信息
     */
    private void setResponse(LogDO logDO, LogResponse logResponse) {
        Map<String, String> responseHeaders = logResponse.getHeaders();
        logDO.setResponseHeaders(JSONUtil.toJsonStr(responseHeaders));
        logDO.setTraceId(responseHeaders.get(traceProperties.getTraceIdName()));
        String responseBody = logResponse.getBody();
        logDO.setResponseBody(responseBody);
        // 状态
        Integer statusCode = logResponse.getStatus();
        logDO.setStatusCode(statusCode);
        logDO.setStatus(statusCode >= HttpStatus.HTTP_BAD_REQUEST ? LogStatusEnum.FAILURE : LogStatusEnum.SUCCESS);
        if (StrUtil.isNotBlank(responseBody)) {
            R result = JSONUtil.toBean(responseBody, R.class);
            if (!result.isSuccess()) {
                logDO.setStatus(LogStatusEnum.FAILURE);
                logDO.setErrorMsg(result.getMsg());
            }
        }
    }

    /**
     * 设置操作人
     *
     * @param logDO       日志信息
     * @param logRequest  请求信息
     * @param logResponse 响应信息
     */
    private void setCreateUser(LogDO logDO, LogRequest logRequest, LogResponse logResponse) {
        String requestUri = URLUtil.getPath(logDO.getRequestUrl());
        // 解析退出接口信息
        String responseBody = logResponse.getBody();
        if (requestUri.startsWith(AuthConstants.LOGOUT_URI) && StrUtil.isNotBlank(responseBody)) {
            R result = JSONUtil.toBean(responseBody, R.class);
            logDO.setCreatedBy(Convert.toLong(result.getData(), null));
            return;
        }
        // 解析登录接口信息
        if (requestUri.startsWith(AuthConstants.LOGIN_URI) && LogStatusEnum.SUCCESS.equals(logDO.getStatus())) {
            String requestBody = logRequest.getBody();
            AccountLoginReq authReq = JSONUtil.toBean(requestBody, AccountLoginReq.class);
            logDO.setDescription("账号密码登录");
            // 解析账号登录用户为操作人
            logDO.setCreatedBy(ExceptionUtils.exToNull(() -> userService.findByUsername(authReq.getUsername())
                    .getId()));
            return;
        }
        // 解析 Token 信息
        Map<String, String> requestHeaders = logRequest.getHeaders();
        String headerName = HttpHeaders.AUTHORIZATION;
        boolean isContainsAuthHeader = CollUtil.containsAny(requestHeaders.keySet(), Set.of(headerName, headerName
                .toLowerCase()));
        if (MapUtil.isNotEmpty(requestHeaders) && isContainsAuthHeader) {
            String authorization = requestHeaders.getOrDefault(headerName, requestHeaders.get(headerName
                    .toLowerCase()));
            String token = authorization.replace(SaManager.getConfig()
                    .getTokenPrefix() + StringConstants.SPACE, StringConstants.EMPTY);
            logDO.setCreatedBy(Convert.toLong(StpUtil.getLoginIdByToken(token)));
        }
    }
}
