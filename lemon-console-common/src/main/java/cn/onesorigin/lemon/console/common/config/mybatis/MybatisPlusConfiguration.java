package cn.onesorigin.lemon.console.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;
import com.baomidou.mybatisplus.extension.parser.cache.JdkSerialCaffeineJsqlParseCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.continew.starter.extension.datapermission.provider.DataPermissionUserDataProvider;

import java.util.concurrent.TimeUnit;

/**
 * MyBatis Plus 配置
 *
 * @author BruceMaa
 * @since 2025-09-02 14:09
 */
@Configuration
public class MybatisPlusConfiguration {

    /*
     * SQL解析本地缓存
     */
    static {
        JsqlParserGlobal.setJsqlParseCache(new JdkSerialCaffeineJsqlParseCache(cache -> cache.maximumSize(1024)
                .expireAfterWrite(5, TimeUnit.SECONDS)));
    }

    /**
     * @return 元数据处理器配置
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MyBatisPlusMetaObjectHandler();
    }

    /**
     * @return 数据权限用户数据提供者
     */
    @Bean
    public DataPermissionUserDataProvider dataPermissionUserDataProvider() {
        return new DefaultDataPermissionUserDataProvider();
    }
}
