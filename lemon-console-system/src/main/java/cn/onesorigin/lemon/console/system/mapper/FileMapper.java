package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.FileDO;
import cn.onesorigin.lemon.console.system.model.resp.FileStatisticsResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.continew.starter.data.mapper.BaseMapper;

import java.util.List;

/**
 * 文件 数据层
 *
 * @author BruceMaa
 * @since 2025-09-29 10:45
 */
@Mapper
public interface FileMapper extends BaseMapper<FileDO> {

    /**
     * 查询文件资源统计信息
     *
     * @return 文件资源统计信息
     */
    @Select("SELECT type, COUNT(1) number, SUM(size) size FROM sys_file WHERE type != 0 GROUP BY type")
    List<FileStatisticsResp> statistics();
}
