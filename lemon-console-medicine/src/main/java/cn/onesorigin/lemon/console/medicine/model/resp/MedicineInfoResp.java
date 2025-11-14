package cn.onesorigin.lemon.console.medicine.model.resp;

import cn.onesorigin.lemon.console.common.base.model.resp.BaseResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 药品基本信息响应参数
 *
 * @author BruceMaa
 * @since 2025-11-13 14:49
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "药品基本信息响应参数")
public class MedicineInfoResp extends BaseResp {
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
}
