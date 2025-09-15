package cn.onesorigin.lemon.console.auth.model.entity;

import cn.onesorigin.lemon.console.common.base.model.entity.BaseDO;
import cn.onesorigin.lemon.console.common.enums.DisEnableStatusEnum;
import cn.onesorigin.lemon.console.common.enums.GenderEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.annotation.DictModel;
import top.continew.starter.security.crypto.annotation.FieldEncrypt;
import top.continew.starter.security.crypto.enums.Algorithm;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author BruceMaa
 * @since 2025-09-17 17:42
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@DictModel(labelKey = "nickname", extraKeys = { "username" })
@TableName("sys_user")
public class UserDO extends BaseDO {

    /**
     * 用户名
     */
    String username;

    /**
     * 昵称
     */
    String nickname;

    /**
     * 密码
     */
    @FieldEncrypt(Algorithm.PASSWORD_ENCODER)
    String password;

    /**
     * 性别
     */
    GenderEnum gender;

    /**
     * 邮箱地址
     */
    @FieldEncrypt
    String email;

    /**
     * 手机号
     */
    @FieldEncrypt
    String phone;

    /**
     * 头像地址
     */
    String avatar;

    /**
     * 描述
     */
    String description;

    /**
     * 状态
     */
    DisEnableStatusEnum status;

    /**
     * 是否为系统内置数据
     */
    Boolean isSystem;

    /**
     * 最后一次修改密码时间
     */
    LocalDateTime pwdResetTime;

    /**
     * 部门ID
     */
    Long deptId;
}
