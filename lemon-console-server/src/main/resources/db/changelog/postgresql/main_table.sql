-- liquibase formatted sql

-- changeset BruceMaa:1
-- comment 初始化表结构
CREATE TABLE IF NOT EXISTS "sys_option" (
    "id"            int8         NOT NULL,
    "category"      varchar(50)  NOT NULL,
    "name"          varchar(50)  NOT NULL,
    "code"          varchar(100) NOT NULL,
    "value"         text         DEFAULT NULL,
    "default_value" text         DEFAULT NULL,
    "description"   varchar(200) DEFAULT NULL,
    "update_user"   int8         DEFAULT NULL,
    "update_time"   timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_option_category_code" ON "sys_option" ("category", "code");
COMMENT ON COLUMN "sys_option"."id"            IS 'ID';
COMMENT ON COLUMN "sys_option"."category"      IS '类别';
COMMENT ON COLUMN "sys_option"."name"          IS '名称';
COMMENT ON COLUMN "sys_option"."code"          IS '键';
COMMENT ON COLUMN "sys_option"."value"         IS '值';
COMMENT ON COLUMN "sys_option"."default_value" IS '默认值';
COMMENT ON COLUMN "sys_option"."description"   IS '描述';
COMMENT ON COLUMN "sys_option"."update_user"   IS '修改人';
COMMENT ON COLUMN "sys_option"."update_time"   IS '修改时间';
COMMENT ON TABLE  "sys_option"                 IS '参数表';
