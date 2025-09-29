package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.system.enums.FileTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.constant.StringConstants;

/**
 * 文件 实体
 *
 * @author BruceMaa
 * @since 2025-09-29 10:21
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_file")
public class FileDO extends BaseDO {

    /**
     * 名称
     */
    String name;

    /**
     * 原始名称
     */
    String originalName;

    /**
     * 大小（字节）
     */
    Long size;

    /**
     * 上级目录
     */
    String parentPath;

    /**
     * 路径
     */
    String path;

    /**
     * 扩展名
     */
    String extension;

    /**
     * 内容类型
     */
    String contentType;

    /**
     * 类型
     */
    FileTypeEnum type;

    /**
     * SHA256 值
     */
    String sha256;

    /**
     * 元数据
     */
    String metadata;

    /**
     * 缩略图名称
     */
    String thumbnailName;

    /**
     * 缩略图大小（字节)
     */
    Long thumbnailSize;

    /**
     * 缩略图元数据
     */
    String thumbnailMetadata;

    /**
     * 存储 ID
     */
    Long storageId;

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
        this.path = StringConstants.SLASH.equals(parentPath)
                ? parentPath + this.name
                : parentPath + StringConstants.SLASH + this.name;
    }
}
