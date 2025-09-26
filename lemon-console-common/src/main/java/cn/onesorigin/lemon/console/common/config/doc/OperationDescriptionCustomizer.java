package cn.onesorigin.lemon.console.common.config.doc;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.onesorigin.lemon.console.common.base.controller.BaseController;
import cn.onesorigin.lemon.console.common.config.crud.CrudApiPermissionPrefixCache;
import org.springframework.web.method.HandlerMethod;
import top.continew.starter.core.constant.StringConstants;
import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.annotation.CrudRequestMapping;
import top.continew.starter.extension.crud.enums.Api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Operation 描述定制器 处理 SaToken 鉴权标识符
 *
 * @author BruceMaa
 * @since 2025-09-26 15:47
 */
public class OperationDescriptionCustomizer {

    /**
     * 获取 sa-token 注解信息
     *
     * @param handlerMethod 处理程序方法
     * @return 包含权限和角色校验信息的列表
     */
    public List<String> getPermission(HandlerMethod handlerMethod) {
        List<String> values = new ArrayList<>();

        // 获取权限校验信息
        String permissionInfo = getAnnotationInfo(handlerMethod, SaCheckPermission.class, "权限校验：");
        if (!permissionInfo.isEmpty()) {
            values.add(permissionInfo);
        }

        // 获取角色校验信息
        String roleInfo = getAnnotationInfo(handlerMethod, SaCheckRole.class, "角色校验：");
        if (!roleInfo.isEmpty()) {
            values.add(roleInfo);
        }

        // 处理 CrudRequestMapping 和 CrudApi 注解生成的权限信息
        String crudPermissionInfo = getCrudPermissionInfo(handlerMethod);
        if (!crudPermissionInfo.isEmpty()) {
            values.add(crudPermissionInfo);
        }
        return values;
    }

    /**
     * 获取类和方法上指定注解的信息
     *
     * @param handlerMethod   处理程序方法
     * @param annotationClass 注解类
     * @param title           信息标题
     * @param <A>             注解类型
     * @return 拼接好的注解信息字符串
     */
    private <A extends Annotation> String getAnnotationInfo(HandlerMethod handlerMethod,
                                                            Class<A> annotationClass,
                                                            String title) {
        StringBuilder infoBuilder = new StringBuilder();

        // 获取类上的注解
        A classAnnotation = handlerMethod.getBeanType().getAnnotation(annotationClass);
        if (classAnnotation != null) {
            appendAnnotationInfo(infoBuilder, "类：", classAnnotation);
        }

        // 获取方法上的注解
        A methodAnnotation = handlerMethod.getMethodAnnotation(annotationClass);
        if (methodAnnotation != null) {
            appendAnnotationInfo(infoBuilder, "方法：", methodAnnotation);
        }

        // 如果有注解信息，添加标题
        if (!infoBuilder.isEmpty()) {
            infoBuilder.insert(0, "<font style=\"color:red\" class=\"light-red\">" + title + "</font></br>");
        }

        return infoBuilder.toString();
    }

    /**
     * 拼接注解信息到 StringBuilder 中
     *
     * @param builder    用于拼接信息的 StringBuilder
     * @param prefix     前缀信息，如 "类：" 或 "方法："
     * @param annotation 注解对象
     */
    private void appendAnnotationInfo(StringBuilder builder, String prefix, Annotation annotation) {
        String[] values = null;
        SaMode mode = null;
        String type = "";
        String[] orRole = new String[0];

        if (annotation instanceof SaCheckPermission checkPermission) {
            values = checkPermission.value();
            mode = checkPermission.mode();
            type = checkPermission.type();
            orRole = checkPermission.orRole();
        } else if (annotation instanceof SaCheckRole checkRole) {
            values = checkRole.value();
            mode = checkRole.mode();
            type = checkRole.type();
        }

        if (values != null && mode != null) {
            builder.append("<font style=\"color:red\" class=\"light-red\">");
            builder.append(prefix);
            if (!type.isEmpty()) {
                builder.append("（类型：").append(type).append("）");
            }
            builder.append(getAnnotationNote(values, mode));
            if (orRole.length > 0) {
                builder.append(" 或 角色校验（").append(getAnnotationNote(orRole, mode)).append("）");
            }
            builder.append("</font></br>");
        }
    }

    /**
     * 根据注解的模式拼接注解值
     *
     * @param values 注解的值数组
     * @param mode   注解的模式（AND 或 OR）
     * @return 拼接好的注解值字符串
     */
    private String getAnnotationNote(String[] values, SaMode mode) {
        if (mode.equals(SaMode.AND)) {
            return String.join(" 且 ", values);
        } else {
            return String.join(" 或 ", values);
        }
    }

    /**
     * 处理 CrudRequestMapping 和 CrudApi 注解生成的权限信息
     *
     * @param handlerMethod 处理程序方法
     * @return 拼接好的权限信息字符串
     * @see BaseController#preHandle(CrudApi, Object[], Method, Class)
     */
    private String getCrudPermissionInfo(HandlerMethod handlerMethod) {
        Class<?> targetClass = handlerMethod.getBeanType();
        CrudRequestMapping crudRequestMapping = targetClass.getAnnotation(CrudRequestMapping.class);
        CrudApi crudApi = handlerMethod.getMethodAnnotation(CrudApi.class);
        if (crudRequestMapping == null || crudApi == null) {
            return StringConstants.EMPTY;
        }
        if (Api.DICT.equals(crudApi.value()) || Api.DICT_TREE.equals(crudApi.value())) {
            return StringConstants.EMPTY;
        }
        String permissionPrefix = CrudApiPermissionPrefixCache.get(targetClass);
        String apiName = BaseController.getApiName(crudApi.value());
        String permission = "%s:%s".formatted(permissionPrefix, apiName.toLowerCase());
        return "<font style=\"color:red\" class=\"light-red\">CRUD 权限校验：</font></br><font style=\"color:red\" class=\"light-red\">方法：</font><font style=\"color:red\" class=\"light-red\">" + permission + "</font>";
    }
}
