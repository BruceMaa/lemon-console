package cn.onesorigin.lemon.console.system.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统管理配置
 *
 * @author BruceMaa
 * @since 2025-11-10 10:37
 */
@Configuration
public class SystemConfiguration {

    /**
     * API 文档分组配置
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder().group("system").displayName("系统管理").pathsToMatch("/system/**").build();
    }
}
