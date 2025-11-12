package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.NoticeDO;
import cn.onesorigin.lemon.console.system.model.query.NoticeQuery;
import cn.onesorigin.lemon.console.system.model.resp.NoticeResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.data.mapper.BaseMapper;

import java.util.List;

/**
 * 公告 数据层
 *
 * @author BruceMaa
 * @since 2025-11-11 09:23
 */
@Mapper
public interface NoticeMapper extends BaseMapper<NoticeDO> {

    /**
     * 分页查询公告列表
     *
     * @param page  分页条件
     * @param query 查询条件
     * @return 公告列表
     */
    IPage<NoticeResp> findNotices(@Param("page") Page<NoticeDO> page, @Param("query") NoticeQuery query);

    /**
     * 根据用户ID和通知方式查询未读公告ID列表
     *
     * @param noticeMethod 通知方式
     * @param userId       用户ID
     * @return 未读公告ID列表
     */
    List<Long> findUnreadIdsByUserId(@Param("noticeMethod") Integer noticeMethod, @Param("userId") Long userId);
}
