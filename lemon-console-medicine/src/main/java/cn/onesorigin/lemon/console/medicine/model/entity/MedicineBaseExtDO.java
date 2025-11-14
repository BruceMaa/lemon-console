package cn.onesorigin.lemon.console.medicine.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import top.continew.starter.extension.crud.model.entity.BaseIdDO;

import java.util.List;

/**
 * 药品基本信息扩展 实体
 *
 * @author BruceMaa
 * @since 2025-11-13 15:12
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName(value = "medicine_base_ext", autoResultMap = true)
public class MedicineBaseExtDO extends BaseIdDO {

    /**
     * 活性成分列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> activeIngredients;

    /**
     * 辅料 / 非活性成分列表
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> excipients;

    /**
     * 给药途径
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> routeOfAdministration;

    /**
     * 治疗类别
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    List<String> therapeuticCategories;

    /**
     * 药理毒理
     */
    String pharmacology;

    /**
     * 药代动力学
     */
    String pharmacokinetics;

    /**
     * 适应症
     */
    String indications;

    /**
     * 用法用量
     */
    String dosage;

    /**
     * 禁忌症
     */
    String contraindications;

    /**
     * 注意事项
     */
    String precautions;

    /**
     * 不良反应
     */
    String adverseReactions;

    /**
     * 药物相互作用
     */
    String drugInteractions;
}
