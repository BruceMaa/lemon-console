package cn.onesorigin.lemon.console;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
//import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.continew.starter.core.autoconfigure.application.ApplicationProperties;
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
@EnableGlobalResponse
@RestController
@SpringBootApplication
public class ConsoleApplication implements ApplicationRunner {

    private final ApplicationProperties applicationProperties;
    private final ServerProperties serverProperties;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @GetMapping("/")
    public R index() {
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
        //Knife4jProperties knife4jProperties = SpringUtil.getBean(Knife4jProperties.class);
        //if (!knife4jProperties.isProduction()) {
        //    log.info("接口文档: {}/doc.html", baseUrl);
        //}
        //log.info("常见问题: https://continew.top/admin/faq.html");
        //log.info("更新日志: https://continew.top/admin/changelog/");
        //log.info("ContiNew Admin: 持续迭代优化的，高质量多租户中后台管理系统框架");
        log.info("--------------------------------------------------------");
    }

}
