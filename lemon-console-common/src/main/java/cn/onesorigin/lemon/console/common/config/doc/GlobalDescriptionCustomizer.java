package cn.onesorigin.lemon.console.common.config.doc;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.models.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局描述定制器 - 处理 SaToken 的注解权限码
 *
 * @author BruceMaa
 * @since 2025-09-26 15:47
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GlobalDescriptionCustomizer implements GlobalOperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 将 sa-token 注解数据添加到 operation 的描述中
        // 权限
        List<String> noteList = new ArrayList<>(new OperationDescriptionCustomizer().getPermission(handlerMethod));

        // 如果注解数据列表为空，直接返回原 operation
        if (noteList.isEmpty()) {
            return operation;
        }
        // 拼接注解数据为字符串
        String noteStr = StrUtil.join("<br/>", noteList);
        // 获取原描述
        String originalDescription = operation.getDescription();
        // 根据原描述是否为空，更新描述
        String newDescription = StrUtil.isNotEmpty(originalDescription)
                ? originalDescription + "<br/>" + noteStr
                : noteStr;

        // 设置新描述
        operation.setDescription(newDescription);
        return operation;
    }
}
