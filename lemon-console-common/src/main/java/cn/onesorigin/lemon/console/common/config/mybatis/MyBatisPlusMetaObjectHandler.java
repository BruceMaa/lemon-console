package cn.onesorigin.lemon.console.common.config.mybatis;

import cn.hutool.core.util.ObjectUtil;
import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.content.UserContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 元对象处理器配置（插入或修改时自动填充）
 *
 * @author BruceMaa
 * @since 2025-09-02 09:45
 */
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建人
     */
    private static final String CREATE_USER = "createdBy";
    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "createdAt";
    /**
     * 修改人
     */
    private static final String UPDATE_USER = "modifiedBy";
    /**
     * 修改时间
     */
    private static final String UPDATE_TIME = "modifiedAt";

    /**
     * 插入数据时填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject == null) {
            return;
        }
        Long createUser = UserContextHolder.getUserId();
        LocalDateTime createTime = LocalDateTime.now();
        if (metaObject.getOriginalObject() instanceof BaseDO baseDO) {
            // 继承了 BaseDO 的类，填充创建信息字段
            baseDO.setCreatedBy(ObjectUtil.defaultIfNull(baseDO.getCreatedBy(), createUser));
            baseDO.setCreatedAt(ObjectUtil.defaultIfNull(baseDO.getCreatedAt(), createTime));
        } else {
            // 未继承 BaseDO 的类，如存在创建信息字段则进行填充
            this.fillFieldValue(metaObject, CREATE_USER, createUser, false);
            this.fillFieldValue(metaObject, CREATE_TIME, createTime, false);
        }
    }

    /**
     * 修改数据时填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject == null) {
            return;
        }
        Long updateUser = UserContextHolder.getUserId();
        LocalDateTime updateTime = LocalDateTime.now();
        if (metaObject.getOriginalObject() instanceof BaseDO baseDO) {
            // 继承了 BaseDO 的类，填充修改信息
            baseDO.setModifiedBy(updateUser);
            baseDO.setModifiedAt(updateTime);
        } else {
            // 未继承 BaseDO 的类，根据类中拥有的修改信息字段进行填充，不存在修改信息字段不进行填充
            this.fillFieldValue(metaObject, UPDATE_USER, updateUser, true);
            this.fillFieldValue(metaObject, UPDATE_TIME, updateTime, true);
        }
    }

    /**
     * 填充字段值
     *
     * @param metaObject     元数据对象
     * @param fieldName      要填充的字段名
     * @param fillFieldValue 要填充的字段值
     * @param isOverride     如果字段值不为空，是否覆盖（true：覆盖；false：不覆盖）
     */
    private void fillFieldValue(MetaObject metaObject, String fieldName, Object fillFieldValue, boolean isOverride) {
        if (metaObject.hasSetter(fieldName)) {
            Object fieldValue = metaObject.getValue(fieldName);
            setFieldValByName(fieldName, fieldValue != null && !isOverride ? fieldValue : fillFieldValue, metaObject);
        }
    }
}
