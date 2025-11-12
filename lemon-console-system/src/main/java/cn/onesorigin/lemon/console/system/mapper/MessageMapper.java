package cn.onesorigin.lemon.console.system.mapper;

import cn.onesorigin.lemon.console.system.model.entity.MessageDO;
import cn.onesorigin.lemon.console.system.model.query.MessageQuery;
import cn.onesorigin.lemon.console.system.model.resp.MessageDetailResp;
import cn.onesorigin.lemon.console.system.model.resp.MessageResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.continew.starter.data.mapper.BaseMapper;

import java.util.List;

/**
 * 消息 数据层
 *
 * @author BruceMaa
 * @since 2025-11-12 09:15
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessageDO> {
    /**
     * 查询未读消息数量
     *
     * @param userId 用户ID
     * @param type   消息类型
     * @return 未读消息数量
     */
    Long findUnreadCountByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 查询消息分页列表
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 消息分页列表
     */
    IPage<MessageResp> findPage(@Param("page") Page<MessageDO> page, @Param("query") MessageQuery query);

    /**
     * 查询消息详情
     *
     * @param messageId 消息ID
     * @return 消息详情
     */
    MessageDetailResp findDetailById(Long messageId);

    /**
     * 查询用户未读消息列表
     *
     * @param userId 用户ID
     * @return 未读消息列表
     */
    List<MessageDO> findUnreadListByUserId(Long userId);
}
