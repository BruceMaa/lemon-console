package cn.onesorigin.lemon.console;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.core.autoconfigure.application.ApplicationProperties;
import top.continew.starter.extension.crud.annotation.EnableCrudApi;
import top.continew.starter.web.annotation.EnableGlobalResponse;
import top.continew.starter.web.model.R;

/**
 * 启动控制台应用
 *
 * @author maqiang
 * @since 2025/08/29
 */
@Slf4j
@RequiredArgsConstructor
@EnableCrudApi
@EnableGlobalResponse
@EnableFileStorage
@EnableMethodCache(basePackages = "cn.onesorigin.lemon.console")
@RestController
@SpringBootApplication
public class ConsoleApplication implements ApplicationRunner {

    private final ApplicationProperties applicationProperties;
    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Hidden
    @SaIgnore
    @GetMapping("/")
    public R<?> index() {
        return R.ok(applicationProperties);
    }

    @Override
    public void run(ApplicationArguments args) {
        String hostAddress = NetUtil.getLocalhostStr();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = URLUtil.normalize("%s:%s%s".formatted(hostAddress, port, contextPath));
        log.info("--------------------------------------------------------");
        log.info("{} server started successfully.", applicationProperties.getName());
        log.info("ContiNew Starter: v{} (Spring Boot: v{})", SpringUtil
                .getProperty("application.starter"), SpringBootVersion.getVersion());
        log.info("当前版本: v{} (Profile: {})", applicationProperties.getVersion(), SpringUtil
                .getProperty("spring.profiles.active"));
        log.info("服务地址: {}", baseUrl);
        Knife4jProperties knife4jProperties = SpringUtil.getBean(Knife4jProperties.class);
        if (!knife4jProperties.isProduction()) {
            log.info("接口文档: {}/doc.html", baseUrl);
        }
        log.info("Lemon Console: 柠檬系统控制台");
        log.info("--------------------------------------------------------");
    }

}
