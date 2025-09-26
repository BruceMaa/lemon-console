package cn.onesorigin.lemon.console.common.config.crud;

import cn.hutool.core.util.StrUtil;
import top.continew.starter.core.constant.StringConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRUD API 权限前缀缓存
 *
 * @author BruceMaa
 * @since 2025-09-24 19:40
 */
public class CrudApiPermissionPrefixCache {

    private static final Map<Class<?>, String> PERMISSION_PREFIX_CACHE = new HashMap<>();

    /**
     * 存储CRUD API权限前缀
     *
     * @param controllerClazz 控制器类
     * @param path            路径
     */
    public static void put(Class<?> controllerClazz, String path) {
        String permissionPrefix = parsePermissionPrefix(path);
        PERMISSION_PREFIX_CACHE.put(controllerClazz, permissionPrefix);
    }

    /**
     * 获取CRUD API权限前缀
     *
     * @param controllerClazz 控制器类
     * @return 权限前缀
     */
    public static String get(Class<?> controllerClazz) {
        return PERMISSION_PREFIX_CACHE.get(controllerClazz);
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        PERMISSION_PREFIX_CACHE.clear();
    }

    /**
     * 获取所有缓存
     *
     * @return 所有缓存
     */
    public static Map<Class<?>, String> getAll() {
        return PERMISSION_PREFIX_CACHE;
    }

    /**
     * 解析权限前缀（解析路径获取模块名和资源名）
     *
     * <p>
     * 例如：/system/user => system:user <br>
     * /system/dict/item => system:dictItem
     * </p>
     *
     * @param path 路径
     * @return 权限前缀
     */
    private static String parsePermissionPrefix(String path) {
        List<String> pathSegmentList = StrUtil.splitTrim(path, StringConstants.SLASH);
        if (pathSegmentList.size() < 2) {
            throw new IllegalArgumentException("无效的 @CrudRequestMapping 路径配置：" + path);
        }
        String moduleName = pathSegmentList.get(0);
        String resourceName = StrUtil.toCamelCase(String.join(StringConstants.UNDERLINE, pathSegmentList
                .subList(1, pathSegmentList.size())));
        return "%s:%s".formatted(moduleName, resourceName);
    }
}
