package cn.onesorigin.lemon.console.system.service;

import cn.hutool.core.util.StrUtil;
import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.system.model.entity.FileDO;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import cn.onesorigin.lemon.console.system.model.query.FileQuery;
import cn.onesorigin.lemon.console.system.model.req.FileReq;
import cn.onesorigin.lemon.console.system.model.resp.FileResp;
import cn.onesorigin.lemon.console.system.model.resp.FileStatisticsResp;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.web.multipart.MultipartFile;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.data.service.IService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * 文件 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-29 10:20
 */
public interface FileService extends BaseService<FileResp, FileResp, FileQuery, FileReq>, IService<FileDO> {

    /**
     * 根据存储 ID 列表查询
     *
     * @param storageIds 存储 ID 列表
     * @return 文件数量
     */
    Long countByStorageIds(List<Long> storageIds);

    /**
     * 上传到默认存储
     *
     * @param file 文件信息
     * @return 文件信息
     * @throws IOException /
     */
    default FileInfo upload(MultipartFile file) throws IOException {
        return upload(file, getDefaultParentPath(), null);
    }

    /**
     * 上传到默认存储
     *
     * @param file       文件信息
     * @param parentPath 上级目录
     * @return 文件信息
     * @throws IOException /
     */
    default FileInfo upload(MultipartFile file, String parentPath) throws IOException {
        return upload(file, StrUtil.blankToDefault(parentPath, getDefaultParentPath()), null);
    }

    /**
     * 上传到指定存储
     *
     * @param file        文件信息
     * @param parentPath  上级目录
     * @param storageCode 存储编码
     * @return 文件信息
     * @throws IOException /
     */
    FileInfo upload(MultipartFile file, String parentPath, String storageCode) throws IOException;

    /**
     * 上传到默认存储
     *
     * @param file 文件信息
     * @return 文件信息
     * @throws IOException /
     */
    default FileInfo upload(File file) throws IOException {
        return upload(file, getDefaultParentPath(), null);
    }

    /**
     * 上传到默认存储
     *
     * @param file       文件信息
     * @param parentPath 上级目录
     * @return 文件信息
     * @throws IOException /
     */
    default FileInfo upload(File file, String parentPath) throws IOException {
        return upload(file, StrUtil.blankToDefault(parentPath, getDefaultParentPath()), null);
    }

    /**
     * 上传到指定存储
     *
     * @param file        文件信息
     * @param parentPath  上级目录
     * @param storageCode 存储编码
     * @return 文件信息
     * @throws IOException /
     */
    FileInfo upload(File file, String parentPath, String storageCode) throws IOException;

    /**
     * 创建目录
     *
     * @param req 请求参数
     * @return ID
     */
    Long createDir(FileReq req);

    /**
     * 查询文件资源统计信息
     *
     * @return 资源统计信息
     */
    FileStatisticsResp statistics();

    /**
     * 检查文件是否存在
     *
     * @param fileHash 文件 Hash
     * @return 响应参数
     */
    FileResp check(String fileHash);

    /**
     * 计算文件夹大小
     *
     * @param id ID
     * @return 文件夹大小（字节）
     */
    Long calcDirSize(Long id);

    /**
     * 创建上级文件夹（支持多级）
     *
     * <p>
     * user/avatar/ => user（path：/user）、avatar（path：/user/avatar）
     * </p>
     *
     * @param parentPath 上级目录
     * @param storage    存储配置
     */
    void createParentDir(String parentPath, StorageDO storage);

    /**
     * 获取默认上级目录
     *
     * <p>
     * 默认上级目录：yyyy/MM/dd/
     * </p>
     *
     * @return 默认上级目录
     */
    default String getDefaultParentPath() {
        LocalDate today = LocalDate.now();
        return today.getYear() + StringConstants.SLASH + today.getMonthValue() + StringConstants.SLASH + today
                .getDayOfMonth() + StringConstants.SLASH;
    }
}
