package cn.onesorigin.lemon.console.system.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.system.model.entity.UserDO;
import cn.onesorigin.lemon.console.system.service.OptionService;
import cn.onesorigin.lemon.console.system.service.UserPasswordHistoryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import top.continew.starter.core.util.validation.ValidationUtils;

import java.util.Map;

/**
 * 密码策略 枚举
 *
 * @author BruceMaa
 * @since 2025-09-24 15:04
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PasswordPolicyEnum {
    /**
     * 密码错误锁定阈值
     */
    PASSWORD_ERROR_LOCK_COUNT("密码错误锁定阈值取值范围为 %d-%d", 0, 10, "由于您连续 %s 次输入错误密码，账号已被锁定 %s 分钟，预计解锁时间为 %s，请稍后再试"),

    /**
     * 账号锁定时长（分钟）
     */
    PASSWORD_ERROR_LOCK_MINUTES("账号锁定时长取值范围为 %d-%d 分钟", 1, 1440, "您的账号已被锁定，预计解锁时间为 %s，请稍后再试"),

    /**
     * 密码有效期（天）
     */
    PASSWORD_EXPIRATION_DAYS("密码有效期取值范围为 %d-%d 天", 0, 999, null),

    /**
     * 密码到期提醒（天）
     */
    PASSWORD_EXPIRATION_WARNING_DAYS("密码到期提醒取值范围为 %d-%d 天", 0, 998, null) {
        @Override
        public void validateRange(int value, Map<String, String> policyMap) {
            if (CollUtil.isEmpty(policyMap)) {
                super.validateRange(value, policyMap);
                return;
            }
            Integer passwordExpirationDays = ObjectUtil.defaultIfNull(Convert.toInt(policyMap
                    .get(PASSWORD_EXPIRATION_DAYS.name())), SpringUtil.getBean(OptionService.class)
                    .getValueByCode2Int(PASSWORD_EXPIRATION_DAYS.name()));
            if (passwordExpirationDays > 0) {
                ValidationUtils.throwIf(value >= passwordExpirationDays, "密码到期提醒时间应小于密码有效期");
                return;
            }
            super.validateRange(value, policyMap);
        }
    },

    /**
     * 密码最小长度
     */
    PASSWORD_MIN_LENGTH("密码最小长度取值范围为 %d-%d", 8, 32, "密码最小长度为 %d 个字符") {
        @Override
        public void validate(String password, int value, UserDO user) {
            // 最小长度校验
            ValidationUtils.throwIf(StrUtil.length(password) < value, this.getMsg().formatted(value));
            // 完整校验
            int passwordMaxLength = this.getMax();
            ValidationUtils.throwIf(!ReUtil.isMatch(RegexConstants.PASSWORD_TEMPLATE
                    .formatted(value, passwordMaxLength), password), "密码长度为 {}-{} 个字符，支持大小写字母、数字、特殊字符，至少包含字母和数字", value, passwordMaxLength);
        }
    },

    /**
     * 密码是否必须包含特殊字符
     */
    PASSWORD_REQUIRE_SYMBOLS("密码是否必须包含特殊字符取值只能为是（%d）或否（%d）", 0, 1, "密码必须包含特殊字符") {
        @Override
        public void validateRange(int value, Map<String, String> policyMap) {
            ValidationUtils.throwIf(value != 1 && value != 0, this
                    .getDescription()
                    .formatted(1, 0));
        }

        @Override
        public void validate(String password, int value, UserDO user) {
            ValidationUtils.throwIf(value == 1 && !ReUtil
                    .isMatch(RegexConstants.SPECIAL_CHARACTER, password), this.getMsg());
        }
    },

    /**
     * 密码是否允许包含用户名
     */
    PASSWORD_ALLOW_CONTAIN_USERNAME("密码是否允许包含用户名取值只能为是（%d）或否（%d）", 0, 1, "密码不允许包含正反序用户名") {
        @Override
        public void validateRange(int value, Map<String, String> policyMap) {
            ValidationUtils.throwIf(value != 1 && value != 0, this
                    .getDescription()
                    .formatted(1, 0));
        }

        @Override
        public void validate(String password, int value, UserDO user) {
            if (value <= 0) {
                String username = user.getUsername();
                ValidationUtils.throwIf(StrUtil.containsAnyIgnoreCase(password, username, StrUtil
                        .reverse(username)), this.getMsg());
            }
        }
    },

    /**
     * 历史密码重复校验次数
     */
    PASSWORD_REPETITION_TIMES("历史密码重复校验次数取值范围为 %d-%d", 3, 32, "新密码不得与历史前 %d 次密码重复") {
        @Override
        public void validate(String password, int value, UserDO user) {
            UserPasswordHistoryService userPasswordHistoryService = SpringUtil
                    .getBean(UserPasswordHistoryService.class);
            ValidationUtils.throwIf(userPasswordHistoryService.isPasswordReused(user.getId(), password, value), this
                    .getMsg()
                    .formatted(value));
        }
    },
    ;

    /**
     * 描述
     */
    String description;

    /**
     * 最小值
     */
    Integer min;

    /**
     * 最大值
     */
    Integer max;

    /**
     * 提示信息
     */
    String msg;

    public static final OptionCategoryEnum CATEGORY = OptionCategoryEnum.PASSWORD;

    /**
     * 校验取值范围
     *
     * @param value     值
     * @param policyMap 策略集合
     */
    public void validateRange(int value, Map<String, String> policyMap) {
        var minValue = this.getMin();
        var maxValue = this.getMax();
        ValidationUtils.throwIf(value < minValue || value > maxValue, this.getDescription().formatted(minValue, maxValue));
    }

    /**
     * 校验
     *
     * @param password 密码
     * @param value    策略值
     * @param user     用户信息
     */
    public void validate(String password, int value, UserDO user) {
        // 无需校验
    }
}
