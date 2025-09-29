package cn.onesorigin.lemon.console.system.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.onesorigin.lemon.console.common.base.service.impl.BaseServiceImpl;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.model.req.CommonStatusUpdateReq;
import cn.onesorigin.lemon.console.common.util.SecureUtils;
import cn.onesorigin.lemon.console.system.convert.StorageConvert;
import cn.onesorigin.lemon.console.system.enums.StorageTypeEnum;
import cn.onesorigin.lemon.console.system.mapper.StorageMapper;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import cn.onesorigin.lemon.console.system.model.query.StorageQuery;
import cn.onesorigin.lemon.console.system.model.req.StorageReq;
import cn.onesorigin.lemon.console.system.model.resp.StorageResp;
import cn.onesorigin.lemon.console.system.service.FileService;
import cn.onesorigin.lemon.console.system.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.FileStorageServiceBuilder;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.starter.core.util.ExceptionUtils;
import top.continew.starter.core.util.SpringWebUtils;
import top.continew.starter.core.util.validation.CheckUtils;
import top.continew.starter.core.util.validation.ValidationUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 存储 业务实现
 *
 * @author BruceMaa
 * @since 2025-09-29 09:23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StorageServiceImpl extends BaseServiceImpl<StorageMapper, StorageDO, StorageResp, StorageResp, StorageQuery, StorageReq> implements StorageService {

    private final FileStorageService fileStorageService;
    private final FileService fileService;

    @Override
    protected void beforeCreate(StorageReq req) {
        // 解密密钥
        if (StorageTypeEnum.OSS == req.getType()) {
            ValidationUtils.throwIfBlank(req.getSecretKey(), "Secret Key不能为空");
            req.setSecretKey(this.decryptSecretKey(req.getSecretKey(), null));
        }
        // 指定配置参数校验及预处理
        StorageTypeEnum storageType = req.getType();
        storageType.validate(req);
        storageType.pretreatment(req);
        // 校验存储编码
        this.checkCodeRepeat(req.getCode());
        // 需要独立操作来指定默认存储
        req.setIsDefault(false);
        // 加载存储引擎
        if (DisEnableStatusEnum.ENABLE == req.getStatus()) {
            this.load(StorageConvert.INSTANCE.toDO(req));
        }
    }

    @Override
    protected void beforeUpdate(StorageReq req, Long id) {
        // 解密密钥
        StorageDO oldStorage = super.getById(id);
        if (StorageTypeEnum.OSS == req.getType()) {
            req.setSecretKey(this.decryptSecretKey(req.getSecretKey(), oldStorage));
        }
        // 校验存储编码、存储类型、状态
        CheckUtils.throwIfNotEqual(req.getType(), oldStorage.getType(), "不允许修改存储类型");
        CheckUtils.throwIfNotEqual(req.getCode(), oldStorage.getCode(), "不允许修改存储编码");
        DisEnableStatusEnum newStatus = req.getStatus();
        CheckUtils.throwIf(Objects.equals(Boolean.TRUE, oldStorage.getIsDefault()) && DisEnableStatusEnum.DISABLE
                == newStatus, "【{}】 是默认存储，不允许禁用", oldStorage.getName());
        // 指定配置参数校验及预处理
        StorageTypeEnum storageType = req.getType();
        storageType.validate(req);
        storageType.pretreatment(req);
        // 卸载存储引擎
        this.unload(oldStorage);
        // 加载存储引擎
        if (DisEnableStatusEnum.ENABLE == newStatus) {
            oldStorage = StorageConvert.INSTANCE.toDO(req);
            this.load(oldStorage);
        }
    }

    @Override
    protected void beforeDelete(List<Long> ids) {
        CheckUtils.throwIf(fileService.countByStorageIds(ids) > 0, "所选存储存在文件或文件夹关联，请删除后重试");
        List<StorageDO> storageList = baseMapper.lambdaQuery().in(StorageDO::getId, ids).list();
        storageList.forEach(storage -> {
            CheckUtils.throwIfEqual(Boolean.TRUE, storage.getIsDefault(), "【{}】 是默认存储，不允许删除", storage.getName());
            // 卸载存储引擎
            this.unload(storage);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(CommonStatusUpdateReq req, Long id) {
        var storage = super.getById(id);
        var newStatus = req.getStatus();
        // 状态未变
        if (storage.getStatus() == newStatus) {
            return;
        }
        // 修改状态
        baseMapper.lambdaUpdate().eq(StorageDO::getId, id).set(StorageDO::getStatus, newStatus).update();
        switch (newStatus) {
            case ENABLE -> this.load(storage);
            case DISABLE -> {
                CheckUtils.throwIfEqual(Boolean.TRUE, storage.getIsDefault(), "[{}] 是默认存储，不允许禁用", storage.getName());
                this.unload(storage);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setDefaultStorage(Long id) {
        var storage = super.getById(id);
        if (Objects.equals(storage.getIsDefault(), Boolean.TRUE)) {
            return;
        }
        // 启用状态才能设为默认存储
        CheckUtils.throwIfEqual(DisEnableStatusEnum.DISABLE, storage.getStatus(), "请先启用所选存储");
        baseMapper.lambdaUpdate().eq(StorageDO::getIsDefault, true).set(StorageDO::getIsDefault, false).update();
        baseMapper.lambdaUpdate().eq(StorageDO::getId, id).set(StorageDO::getIsDefault, true).update();
    }

    @Override
    public StorageDO getDefaultStorage() {
        StorageDO storage = baseMapper.lambdaQuery().eq(StorageDO::getIsDefault, true).one();
        CheckUtils.throwIfNull(storage, "请先指定默认存储");
        return storage;
    }

    @Override
    public StorageDO getByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return this.getDefaultStorage();
        }
        StorageDO storage = baseMapper.lambdaQuery().eq(StorageDO::getCode, code).one();
        CheckUtils.throwIfNotExists(storage, "存储", "code", code);
        return storage;
    }

    @Override
    public void load(StorageDO storage) {
        CopyOnWriteArrayList<FileStorage> fileStorageList = fileStorageService.getFileStorageList();
        switch (storage.getType()) {
            case LOCAL -> {
                FileStorageProperties.LocalPlusConfig config = new FileStorageProperties.LocalPlusConfig();
                config.setPlatform(storage.getCode());
                config.setStoragePath(storage.getBucketName());
                fileStorageList.addAll(FileStorageServiceBuilder.buildLocalPlusFileStorage(Collections
                        .singletonList(config)));
                // 注册资源映射
                SpringWebUtils.registerResourceHandler(MapUtil.of(URLUtil.url(storage.getDomain()).getPath(), storage
                        .getBucketName()));
            }
            case OSS -> {
                FileStorageProperties.AmazonS3Config config = new FileStorageProperties.AmazonS3Config();
                config.setPlatform(storage.getCode());
                config.setAccessKey(storage.getAccessKey());
                config.setSecretKey(storage.getSecretKey());
                config.setEndPoint(storage.getEndpoint());
                config.setBucketName(storage.getBucketName());
                fileStorageList.addAll(FileStorageServiceBuilder.buildAmazonS3FileStorage(Collections
                        .singletonList(config), null));
            }
            default -> throw new IllegalArgumentException("不支持的存储类型：%s".formatted(storage.getType()));
        }
    }

    @Override
    public void unload(StorageDO storage) {
        FileStorage fileStorage = fileStorageService.getFileStorage(storage.getCode());
        if (fileStorage == null) {
            return;
        }
        CopyOnWriteArrayList<FileStorage> fileStorageList = fileStorageService.getFileStorageList();
        fileStorageList.remove(fileStorage);
        fileStorage.close();
        // 本地存储引擎需要移除资源映射
        if (StorageTypeEnum.LOCAL == storage.getType()) {
            SpringWebUtils.deRegisterResourceHandler(MapUtil.of(URLUtil.url(storage.getDomain()).getPath(), storage
                    .getBucketName()));
        }
    }

    /**
     * 解密 SecretKey
     *
     * @param encryptSecretKey 加密的 SecretKey
     * @param oldStorage       旧存储配置
     * @return 解密后的 SecretKey
     */
    private String decryptSecretKey(String encryptSecretKey, StorageDO oldStorage) {
        // 修改时，SecretKey 为空将不更改
        if (oldStorage != null && StrUtil.isBlank(encryptSecretKey)) {
            return oldStorage.getSecretKey();
        }
        // 解密
        String secretKey = ExceptionUtils.exToNull(() -> SecureUtils.decryptByRsaPrivateKey(encryptSecretKey));
        ValidationUtils.throwIfNull(secretKey, "私有密钥解密失败");
        ValidationUtils.throwIf(secretKey.length() > 255, "私有密钥长度不能超过 255 个字符");
        return secretKey;
    }

    /**
     * 检查编码是否重复
     *
     * @param code 编码
     */
    private void checkCodeRepeat(String code) {
        CheckUtils.throwIf(baseMapper.lambdaQuery()
                .eq(StorageDO::getCode, code)
                .exists(), "编码为 【{}】 的存储配置已存在", code);
    }
}
