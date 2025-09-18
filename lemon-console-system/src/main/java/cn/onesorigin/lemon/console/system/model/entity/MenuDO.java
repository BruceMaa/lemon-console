package cn.onesorigin.lemon.console.system.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.system.enums.MenuTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 菜单 实体
 *
 * @author BruceMaa
 * @since 2025-09-18 16:17
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("sys_menu")
public class MenuDO extends BaseDO {
    /**
     * 标题
     */
    String title;

    /**
     * 上级菜单 ID
     */
    Long parentId;

    /**
     * 类型
     */
    MenuTypeEnum type;

    /**
     * 路由地址
     */
    String path;

    /**
     * 组件名称
     */
    String name;

    /**
     * 组件路径
     */
    String component;

    /**
     * 重定向地址
     */
    String redirect;

    /**
     * 图标
     */
    String icon;

    /**
     * 是否外链
     */
    Boolean isExternal;

    /**
     * 是否缓存
     */
    Boolean isCache;

    /**
     * 是否隐藏
     */
    Boolean isHidden;

    /**
     * 权限标识
     */
    String permission;

    /**
     * 排序
     */
    Integer sort;

    /**
     * 状态
     */
    DisEnableStatusEnum status;
}
