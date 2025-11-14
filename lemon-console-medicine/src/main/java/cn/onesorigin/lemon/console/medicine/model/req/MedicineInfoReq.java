package cn.onesorigin.lemon.console.medicine.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * 药品基本信息创建或修改请求参数
 *
 * @author BruceMaa
 * @since 2025-11-13 14:51
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineInfoReq {
    /**
     * 药品唯一标识符
     */
    @NotBlank(message = "药品唯一标识符不能为空")
    @Size(max = 50, message = "药品唯一标识符长度不能超过 {max} 个字符")
    String code;
    /**
     * 药品通用名称，中文
     */
    @NotBlank(message = "药品通用名称不能为空")
    @Size(max = 100, message = "药品通用名称长度不能超过 {max} 个字符")
    String genericName;
    /**
     * 药品英文名称
     */
    @Size(max = 100, message = "药品英文名称长度不能超过 {max} 个字符")
    String englishName;
    /**
     * 中文拼音
     */
    @Size(max = 100, message = "中文拼音长度不能超过 {max} 个字符")
    String pinyin;
    /**
     * 药品性状，颜色、形状等
     */
    @Size(max = 100, message = "药品性状长度不能超过 {max} 个字符")
    String appearance;
    /**
     * 剂型
     */
    @NotBlank(message = "剂型不能为空")
    @Size(max = 100, message = "剂型长度不能超过 {max} 个字符")
    String dosageForm;
    /**
     * 规格
     */
    @NotBlank(message = "规格不能为空")
    @Size(max = 100, message = "规格长度不能超过 {max} 个字符")
    String spec;
    /**
     * 规格单位
     */
    @NotBlank(message = "规格单位不能为空")
    @Size(max = 50, message = "规格单位长度不能超过 {max} 个字符")
    String specUnit;
    /**
     * 包装规格
     */
    @Size(max = 50, message = "包装规格长度不能超过 {max} 个字符")
    String packageSpec;
    /**
     * 存储条件
     */
    @NotBlank(message = "存储条件不能为空")
    @Size(max = 100, message = "存储条件长度不能超过 {max} 个字符")
    String storageConditions;
    /**
     * 有效期
     */
    @NotBlank(message = "有效期不能为空")
    @Size(max = 50, message = "有效期长度不能超过 {max} 个字符")
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
    @Size(max = 500, message = "药理毒理长度不能超过 {max} 个字符")
    String pharmacology;

    /**
     * 药代动力学
     */
    @Size(max = 500, message = "药代动力学长度不能超过 {max} 个字符")
    String pharmacokinetics;

    /**
     * 适应症
     */
    @Size(max = 500, message = "适应症长度不能超过 {max} 个字符")
    String indications;

    /**
     * 用法用量
     */
    @Size(max = 500, message = "用法用量长度不能超过 {max} 个字符")
    String dosage;

    /**
     * 禁忌症
     */
    @Size(max = 500, message = "禁忌症长度不能超过 {max} 个字符")
    String contraindications;

    /**
     * 注意事项
     */
    @Size(max = 500, message = "注意事项长度不能超过 {max} 个字符")
    String precautions;

    /**
     * 不良反应
     */
    @Size(max = 500, message = "不良反应长度不能超过 {max} 个字符")
    String adverseReactions;

    /**
     * 药物相互作用
     */
    @Size(max = 500, message = "药物相互作用长度不能超过 {max} 个字符")
    String drugInteractions;
}
