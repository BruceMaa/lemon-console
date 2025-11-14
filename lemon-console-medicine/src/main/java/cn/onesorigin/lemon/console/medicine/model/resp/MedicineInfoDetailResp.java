package cn.onesorigin.lemon.console.medicine.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseDetailResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 药品基本信息详情响应参数
 *
 * @author BruceMaa
 * @since 2025-11-13 14:50
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "药品基本信息详情响应参数")
public class MedicineInfoDetailResp extends BaseDetailResp {
    /**
     * 药品唯一标识符
     */
    String code;
    /**
     * 药品通用名称，中文
     */
    String genericName;
    /**
     * 药品英文名称
     */
    String englishName;
    /**
     * 中文拼音
     */
    String pinyin;
    /**
     * 药品性状，颜色、形状等
     */
    String appearance;
    /**
     * 剂型
     */
    String dosageForm;
    /**
     * 规格
     */
    String spec;
    /**
     * 规格单位
     */
    String specUnit;
    /**
     * 包装规格
     */
    String packageSpec;
    /**
     * 存储条件
     */
    String storageConditions;
    /**
     * 有效期
     */
    String validityPeriod;

    /**
     * 活性成分列表
     */
    List<String> activeIngredients;

    /**
     * 辅料 / 非活性成分列表
     */
    List<String> excipients;

    /**
     * 给药途径
     */
    List<String> routeOfAdministration;

    /**
     * 治疗类别
     */
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
