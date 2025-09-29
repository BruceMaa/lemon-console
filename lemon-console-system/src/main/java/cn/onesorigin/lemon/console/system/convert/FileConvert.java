package cn.onesorigin.lemon.console.system.convert;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.onesorigin.lemon.console.system.enums.FileTypeEnum;
import cn.onesorigin.lemon.console.system.model.entity.FileDO;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import org.dromara.x.file.storage.core.FileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.continew.starter.core.constant.StringConstants;

/**
 * 文件 转换器
 *
 * @author BruceMaa
 * @since 2025-09-29 10:24
 */
@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    /**
     * 基于 {@link FileInfo} 构建文件信息对象
     *
     * @param fileInfo {@link FileInfo} 文件信息
     */
    default FileDO toDO(FileInfo fileInfo) {
        var fileDO = new FileDO();
        fileDO.setName(fileInfo.getFilename());
        fileDO.setOriginalName(fileInfo.getOriginalFilename());
        fileDO.setSize(fileInfo.getSize());
        // 如果为空，则为 /；如果不为空，则调整格式为：/xxx
        fileDO.setParentPath(StrUtil.isEmpty(fileInfo.getPath())
                ? StringConstants.SLASH
                : StrUtil.removeSuffix(StrUtil.prependIfMissing(fileInfo
                .getPath(), StringConstants.SLASH), StringConstants.SLASH));
        fileDO.setPath(StrUtil.prependIfMissing(fileInfo.getUrl(), StringConstants.SLASH));
        fileDO.setExtension(fileInfo.getExt());
        fileDO.setContentType(fileInfo.getContentType());
        fileDO.setType(FileTypeEnum.getByExtension(fileDO.getExtension()));
        fileDO.setSha256(fileInfo.getHashInfo().getSha256());
        fileDO.setMetadata(JSONUtil.toJsonStr(fileInfo.getMetadata()));
        fileDO.setThumbnailName(fileInfo.getThFilename());
        fileDO.setThumbnailSize(fileInfo.getThSize());
        fileDO.setThumbnailMetadata(JSONUtil.toJsonStr(fileInfo.getThMetadata()));
        fileDO.setCreatedAt(DateUtil.toLocalDateTime(fileInfo.getCreateTime()));
        return fileDO;
    }


    /**
     * 转换为 {@link FileInfo} 文件信息对象
     *
     * @param storage 存储配置信息
     * @return {@link FileInfo} 文件信息对象
     */
    default FileInfo toFileInfo(FileDO fileDO, StorageDO storage) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPlatform(storage.getCode());
        fileInfo.setFilename(fileDO.getName());
        fileInfo.setOriginalFilename(fileDO.getOriginalName());
        // 暂不使用，所以保持空
        fileInfo.setBasePath(StringConstants.EMPTY);
        fileInfo.setSize(fileDO.getSize());
        fileInfo.setPath(StringConstants.SLASH.equals(fileDO.getParentPath())
                ? StringConstants.EMPTY
                : StrUtil.appendIfMissing(StrUtil
                .removePrefix(fileDO.getParentPath(), StringConstants.SLASH), StringConstants.SLASH));
        fileInfo.setExt(fileDO.getExtension());
        fileInfo.setContentType(fileDO.getContentType());
        if (StrUtil.isNotBlank(fileDO.getMetadata())) {
            fileInfo.setMetadata(JSONUtil.toBean(fileDO.getMetadata(), new TypeReference<>() {
            }, true));
        }
        fileInfo.setUrl(StrUtil.removePrefix(fileDO.getPath(), StringConstants.SLASH));
        // 缩略图信息
        fileInfo.setThFilename(fileDO.getThumbnailName());
        fileInfo.setThSize(fileDO.getThumbnailSize());
        fileInfo.setThUrl(fileInfo.getPath() + fileInfo.getThFilename());
        if (StrUtil.isNotBlank(fileDO.getThumbnailMetadata())) {
            fileInfo.setThMetadata(JSONUtil.toBean(fileDO.getThumbnailMetadata(), new TypeReference<>() {
            }, true));
        }
        return fileInfo;
    }
}
