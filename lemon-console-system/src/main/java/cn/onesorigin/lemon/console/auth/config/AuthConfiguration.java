package cn.onesorigin.lemon.console.auth.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统认证配置
 *
 * @author BruceMaa
 * @since 2025-09-04 16:02
 */
@Configuration
public class AuthConfiguration {

    /**
     * API 文档分组配置
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .displayName("系统认证")
                .pathsToMatch("/auth/**", "monitor/online/**")
                .build();
    }
}
