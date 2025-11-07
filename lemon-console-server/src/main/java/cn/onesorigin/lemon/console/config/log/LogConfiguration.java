package cn.onesorigin.lemon.console.config.log;

import cn.onesorigin.lemon.console.system.mapper.LogMapper;
import cn.onesorigin.lemon.console.system.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.continew.starter.log.annotation.ConditionalOnEnabledLog;
import top.continew.starter.log.dao.LogDao;
import top.continew.starter.trace.autoconfigure.TraceProperties;

/**
 * 日志配置
 *
 * @author BruceMaa
 * @since 2025-11-07 14:50
 */
@Configuration
@ConditionalOnEnabledLog
public class LogConfiguration {
    /**
     * 日志持久层接口本地实现类
     */
    @Bean
    public LogDao logDao(UserService userService, LogMapper logMapper, TraceProperties traceProperties) {
        return new LogDaoLocalImpl(userService, logMapper, traceProperties);
    }
}
