package cn.onesorigin.lemon.console.system.validation;

import jakarta.validation.groups.Default;

/**
 * 分组校验
 *
 * @author BruceMaa
 * @since 2025-09-29 09:10
 */
public interface ValidationGroup extends Default {

    /**
     * 分组校验-存储
     */
    interface Storage extends ValidationGroup {
        /**
         * 本地存储
         */
        interface Local extends Storage {
        }

        /**
         * 对象存储
         */
        interface OSS extends Storage {
        }
    }
}
