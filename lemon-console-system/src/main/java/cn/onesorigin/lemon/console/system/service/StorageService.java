package cn.onesorigin.lemon.console.system.service;

import cn.onesorigin.lemon.console.common.base.service.BaseService;
import cn.onesorigin.lemon.console.common.model.req.CommonStatusUpdateReq;
import cn.onesorigin.lemon.console.system.model.entity.StorageDO;
import cn.onesorigin.lemon.console.system.model.query.StorageQuery;
import cn.onesorigin.lemon.console.system.model.req.StorageReq;
import cn.onesorigin.lemon.console.system.model.resp.StorageResp;
import top.continew.starter.data.service.IService;

/**
 * 存储 业务接口
 *
 * @author BruceMaa
 * @since 2025-09-29 09:22
 */
public interface StorageService extends BaseService<StorageResp, StorageResp, StorageQuery, StorageReq>, IService<StorageDO> {
    /**
     * 更新状态
     *
     * @param req 状态信息
     * @param id  存储ID
     */
    void updateStatus(CommonStatusUpdateReq req, Long id);

    /**
     * 设置默认存储
     *
     * @param id 存储ID
     */
    void setDefaultStorage(Long id);

    /**
     * 查询默认存储
     *
     * @return 存储配置
     */
    StorageDO getDefaultStorage();

    /**
     * 根据编码查询（如果编码为空，则返回默认存储）
     *
     * @param code 编码
     * @return 存储配置
     */
    StorageDO getByCode(String code);

    /**
     * 加载存储引擎
     *
     * @param storage 存储配置
     */
    void load(StorageDO storage);

    /**
     * 卸载存储引擎
     *
     * @param storage 存储配置
     */
    void unload(StorageDO storage);
}
