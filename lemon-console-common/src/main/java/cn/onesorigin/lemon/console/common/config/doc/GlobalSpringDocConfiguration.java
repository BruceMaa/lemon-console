package cn.onesorigin.lemon.console.common.config.doc;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局接口文档配置
 *
 * @author BruceMaa
 * @since 2025-09-26 15:47
 */
@Configuration
public class GlobalSpringDocConfiguration {

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .displayName("全部接口")
                .pathsToMatch("/**")
                .packagesToExclude("/error")
                .build();
    }

    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("common")
                .displayName("通用接口")
                .pathsToMatch("/captcha/**", "/dashboard/**")
                .build();
    }
}
