-- liquibase formatted sql

-- changeset BruceMaa:1
-- comment 初始化表结构

-- 药品基本信息表
CREATE TABLE IF NOT EXISTS "medicine_base_info" (
    "id" int8 PRIMARY KEY,
    "code" VARCHAR(50) NOT NULL,
    "generic_name" VARCHAR(100) NOT NULL,
    "english_name" VARCHAR(100),
    "pinyin" VARCHAR(100),
    "appearance" VARCHAR(100),
    "dosage_form" VARCHAR(100) NOT NULL,
    "spec" VARCHAR(100) NOT NULL,
    "spec_unit" VARCHAR(50) NOT NULL,
    "package_spec" VARCHAR(50),
    "storage_conditions" VARCHAR(100) NOT NULL,
    "validity_period" VARCHAR(50) NOT NULL,
    "created_by"    int8         NOT NULL,
    "created_at"    timestamp    NOT NULL,
    "modified_by"    int8         DEFAULT NULL,
    "modified_at"    timestamp    DEFAULT NULL
);
COMMENT ON TABLE "medicine_base_info" IS '药品基本信息表';
COMMENT ON COLUMN "medicine_base_info"."id" IS '主键ID';
COMMENT ON COLUMN "medicine_base_info"."code" IS '药品唯一标识符';
COMMENT ON COLUMN "medicine_base_info"."generic_name" IS '药品通用名称，中文';
COMMENT ON COLUMN "medicine_base_info"."english_name" IS '药品英文名称';
COMMENT ON COLUMN "medicine_base_info"."pinyin" IS '中文拼音';
COMMENT ON COLUMN "medicine_base_info"."appearance" IS '药品性状，颜色、形状等';
COMMENT ON COLUMN "medicine_base_info"."dosage_form" IS '剂型';
COMMENT ON COLUMN "medicine_base_info"."spec" IS '规格';
COMMENT ON COLUMN "medicine_base_info"."spec_unit" IS '规格单位';
COMMENT ON COLUMN "medicine_base_info"."package_spec" IS '包装规格';
COMMENT ON COLUMN "medicine_base_info"."storage_conditions" IS '存储条件';
COMMENT ON COLUMN "medicine_base_info"."validity_period" IS '有效期';
COMMENT ON COLUMN "medicine_base_info"."created_by" IS '创建人ID';
COMMENT ON COLUMN "medicine_base_info"."created_at" IS '创建时间';
COMMENT ON COLUMN "medicine_base_info"."modified_by" IS '更新人ID';
COMMENT ON COLUMN "medicine_base_info"."modified_at" IS '更新时间';

-- 创建索引
CREATE INDEX IF NOT EXISTS "idx_medicine_base_info_code" ON "medicine_base_info"("code");
CREATE INDEX IF NOT EXISTS "idx_medicine_base_info_generic_name" ON "medicine_base_info"("generic_name");
CREATE INDEX IF NOT EXISTS "idx_medicine_base_info_pinyin" ON "medicine_base_info"("pinyin");

-- 药品基本信息扩展表
CREATE TABLE IF NOT EXISTS "medicine_base_ext" (
    "id" int8 PRIMARY KEY,
    "active_ingredients" json,
    "excipients" json,
    "route_of_administration" json,
    "therapeutic_categories" json,
    "pharmacology" TEXT,
    "pharmacokinetics" TEXT,
    "indications" TEXT,
    "dosage" TEXT,
    "contraindications" TEXT,
    "precautions" TEXT,
    "adverse_reactions" TEXT,
    "drug_interactions" TEXT
);
COMMENT ON TABLE "medicine_base_ext" IS '药品基本信息扩展表';
COMMENT ON COLUMN "medicine_base_ext"."id" IS '主键ID';
COMMENT ON COLUMN "medicine_base_ext"."active_ingredients" IS '活性成分列表';
COMMENT ON COLUMN "medicine_base_ext"."excipients" IS '辅料 / 非活性成分列表';
COMMENT ON COLUMN "medicine_base_ext"."route_of_administration" IS '给药途径';
COMMENT ON COLUMN "medicine_base_ext"."therapeutic_categories" IS '治疗类别';
COMMENT ON COLUMN "medicine_base_ext"."pharmacology" IS '药理毒理';
COMMENT ON COLUMN "medicine_base_ext"."pharmacokinetics" IS '药代动力学';
COMMENT ON COLUMN "medicine_base_ext"."indications" IS '适应症';
COMMENT ON COLUMN "medicine_base_ext"."dosage" IS '用法用量';
COMMENT ON COLUMN "medicine_base_ext"."contraindications" IS '禁忌症';
COMMENT ON COLUMN "medicine_base_ext"."precautions" IS '注意事项';
COMMENT ON COLUMN "medicine_base_ext"."adverse_reactions" IS '不良反应';
COMMENT ON COLUMN "medicine_base_ext"."drug_interactions" IS '药物相互作用';

