package cn.onesorigin.lemon.console.common.config.excel;

import java.lang.annotation.*;

/**
 * 字典字段注解
 *
 * @author BruceMaa
 * @since 2025-09-26 15:53
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DictExcelProperty {

    /**
     * 字典编码
     *
     * @return 字典编码
     */
    String value();
}
