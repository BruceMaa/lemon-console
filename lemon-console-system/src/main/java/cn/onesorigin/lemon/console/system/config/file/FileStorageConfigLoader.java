package cn.onesorigin.lemon.console.system.config.file;

import cn.hutool.core.collection.CollUtil;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import cn.onesorigin.lemon.console.system.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件存储配置加载器
 *
 * @author BruceMaa
 * @since 2025-11-10 10:38
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageConfigLoader implements ApplicationRunner {

    private final StorageService storageService;

    @Override
    public void run(ApplicationArguments args) {
        List<StorageDO> list = storageService.lambdaQuery().eq(StorageDO::getStatus, DisEnableStatusEnum.ENABLE).list();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        list.forEach(storageService::load);
    }
}
