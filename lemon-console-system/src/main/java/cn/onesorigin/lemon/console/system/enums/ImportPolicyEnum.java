package cn.onesorigin.lemon.console.system.enums;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.enums.BaseEnum;

import java.util.List;

/**
 * 数据导入策略 枚举
 *
 * @author BruceMaa
 * @since 2025-09-30 11:12
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ImportPolicyEnum implements BaseEnum<Integer> {

    /**
     * 跳过该行
     */
    SKIP(1, "跳过该行"),

    /**
     * 修改数据
     */
    UPDATE(2, "修改数据"),

    /**
     * 停止导入
     */
    EXIT(3, "停止导入");

    Integer value;
    String description;

    public boolean validate(ImportPolicyEnum importPolicy, String data, List<String> existList) {
        return this == importPolicy && CollUtil.isNotEmpty(existList) && existList.contains(data);
    }
}
