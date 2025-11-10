package cn.onesorigin.lemon.console.system.config.file;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.onesorigin.lemon.console.system.convert.FileConvert;
import cn.onesorigin.lemon.console.system.mapper.FileMapper;
import cn.onesorigin.lemon.console.system.mapper.StorageMapper;
import cn.onesorigin.lemon.console.system.model.entity.FileDO;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Component;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.core.util.CollUtils;
import top.continew.starter.core.util.URLUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件记录实现类
 *
 * @author BruceMaa
 * @since 2025-11-10 10:44
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileRecorderImpl implements FileRecorder {

    private final FileMapper fileMapper;
    private final StorageMapper storageMapper;

    @Override
    public boolean save(FileInfo fileInfo) {
        // 保存文件信息
        FileDO file = FileConvert.INSTANCE.toDO(fileInfo);
        StorageDO storage = (StorageDO) fileInfo.getAttr().get(ClassUtil.getClassName(StorageDO.class, false));
        file.setStorageId(storage.getId());
        fileMapper.insert(file);
        // 方便文件上传完成后获取文件信息
        fileInfo.setId(String.valueOf(file.getId()));
        if (!URLUtils.isHttpUrl(fileInfo.getUrl())) {
            String prefix = storage.getUrlPrefix();
            String url = URLUtil.normalize(prefix + fileInfo.getUrl(), false, true);
            fileInfo.setUrl(url);
            if (StrUtil.isNotBlank(fileInfo.getThUrl())) {
                fileInfo.setThUrl(URLUtil.normalize(prefix + fileInfo.getThUrl(), false, true));
            }
        }
        return true;
    }

    @Override
    public FileInfo getByUrl(String url) {
        FileDO file = this.getFileByUrl(url);
        if (file == null) {
            return null;
        }
        StorageDO storageDO = storageMapper.lambdaQuery().eq(StorageDO::getId, file.getStorageId()).one();
        return FileConvert.INSTANCE.toFileInfo(file, storageDO);
    }

    @Override
    public boolean delete(String url) {
        FileDO file = this.getFileByUrl(url);
        if (file == null) {
            return false;
        }
        return fileMapper.lambdaUpdate().eq(FileDO::getId, file.getId()).remove();
    }

    @Override
    public void update(FileInfo fileInfo) {
        /* 不使用分片功能则无需重写 */
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        /* 不使用分片功能则无需重写 */
    }

    @Override
    public void deleteFilePartByUploadId(String s) {
        /* 不使用分片功能则无需重写 */
    }

    /**
     * 根据 URL 查询文件
     *
     * @param url URL
     * @return 文件信息
     */
    private FileDO getFileByUrl(String url) {
        LambdaQueryChainWrapper<FileDO> queryWrapper = fileMapper.lambdaQuery()
                .eq(FileDO::getName, StrUtil.subAfter(url, StringConstants.SLASH, true));
        // 非 HTTP URL 场景
        if (!URLUtils.isHttpUrl(url)) {
            return queryWrapper.eq(FileDO::getPath, StrUtil.prependIfMissing(url, StringConstants.SLASH)).one();
        }
        // HTTP URL 场景
        List<FileDO> list = queryWrapper.list();
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        // 结合存储配置进行匹配
        List<StorageDO> storageList = storageMapper.selectByIds(CollUtils.mapToList(list, FileDO::getStorageId));
        Map<Long, StorageDO> storageMap = storageList.stream()
                .collect(Collectors.toMap(StorageDO::getId, storage -> storage));
        return list.stream().filter(file -> {
            // http://localhost:8080/file/user/avatar/6825e687db4174e7a297a5f8.png => http://localhost:8000/file/user/avatar
            String urlPrefix = StrUtil.subBefore(url, StringConstants.SLASH, true);
            // http://localhost:8080/file/ + /user/avatar => http://localhost:8000/file/user/avatar
            StorageDO storage = storageMap.get(file.getStorageId());
            return urlPrefix.equals(URLUtil.normalize(storage.getUrlPrefix() + file.getParentPath(), false, true));
        }).findFirst().orElse(null);
    }
}
