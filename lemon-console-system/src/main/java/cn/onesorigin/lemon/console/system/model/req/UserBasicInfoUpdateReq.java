package cn.onesorigin.lemon.console.system.model.req;

import cn.onesorigin.lemon.console.common.constant.RegexConstants;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 用户基础信息修改请求参数
 *
 * @author BruceMaa
 * @since 2025-09-26 15:03
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "用户基础信息修改请求参数")
public class UserBasicInfoUpdateReq {

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = RegexConstants.GENERAL_NAME, message = "昵称长度为 2-30 个字符，支持中文、字母、数字、下划线，短横线")
    String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别")
    @NotNull(message = "性别无效")
    GenderEnum gender;
}
