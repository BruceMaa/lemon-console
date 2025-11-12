package cn.onesorigin.lemon.console.system.enums;

import cn.onesorigin.lemon.console.common.constant.UiConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

/**
 * 公告状态 枚举
 *
 * @author BruceMaa
 * @since 2025-11-11 09:22
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum NoticeStatusEnum implements BaseEnum<Integer> {

    /**
     * 草稿
     */
    DRAFT(1, "草稿", UiConstants.COLOR_WARNING),

    /**
     * 待发布
     */
    PENDING(2, "待发布", UiConstants.COLOR_PRIMARY),

    /**
     * 已发布
     */
    PUBLISHED(3, "已发布", UiConstants.COLOR_SUCCESS),
    ;

    Integer value;
    String description;
    String color;
}
