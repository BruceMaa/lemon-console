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
    "modified_by"   int8         DEFAULT NULL,
    "modified_at"   timestamp    DEFAULT NULL,
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
COMMENT ON COLUMN "sys_option"."modified_by"   IS '修改人';
COMMENT ON COLUMN "sys_option"."modified_at"   IS '修改时间';
COMMENT ON TABLE  "sys_option"                 IS '参数表';


CREATE TABLE IF NOT EXISTS "sys_client" (
    "id"             int8         NOT NULL,
    "client_id"      varchar(50)  NOT NULL,
    "client_type"    varchar(50)  NOT NULL,
    "auth_type"      json         NOT NULL,
    "active_timeout" int8         NOT NULL DEFAULT -1,
    "timeout"        int8         NOT NULL DEFAULT 2592000,
    "status"         int2         NOT NULL DEFAULT 1,
    "created_by"    int8         NOT NULL,
    "created_at"    timestamp    NOT NULL,
    "modified_by"    int8         DEFAULT NULL,
    "modified_at"    timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_client_client_id" ON "sys_client" ("client_id");
CREATE INDEX "idx_client_created_by" ON "sys_client" ("created_by");
CREATE INDEX "idx_client_modified_by" ON "sys_client" ("modified_by");
COMMENT ON COLUMN "sys_client"."id"             IS 'ID';
COMMENT ON COLUMN "sys_client"."client_id"      IS '客户端ID';
COMMENT ON COLUMN "sys_client"."client_type"    IS '客户端类型';
COMMENT ON COLUMN "sys_client"."auth_type"      IS '认证类型';
COMMENT ON COLUMN "sys_client"."active_timeout" IS 'Token最低活跃频率（单位：秒，-1：不限制，永不冻结）';
COMMENT ON COLUMN "sys_client"."timeout"        IS 'Token有效期（单位：秒，-1：永不过期）';
COMMENT ON COLUMN "sys_client"."status"         IS '状态（1：启用；2：禁用）';
COMMENT ON COLUMN "sys_client"."created_by"    IS '创建人';
COMMENT ON COLUMN "sys_client"."created_at"    IS '创建时间';
COMMENT ON COLUMN "sys_client"."modified_by"    IS '修改人';
COMMENT ON COLUMN "sys_client"."modified_at"    IS '修改时间';
COMMENT ON TABLE  "sys_client"                  IS '客户端表';


CREATE TABLE IF NOT EXISTS "sys_user" (
    "id"             int8         NOT NULL,
    "username"       varchar(64)  NOT NULL,
    "nickname"       varchar(30)  NOT NULL,
    "password"       varchar(255) DEFAULT NULL,
    "gender"         int2         NOT NULL DEFAULT 0,
    "email"          varchar(255) DEFAULT NULL,
    "phone"          varchar(255) DEFAULT NULL,
    "avatar"         text         DEFAULT NULL,
    "description"    varchar(200) DEFAULT NULL,
    "status"         int2         NOT NULL DEFAULT 1,
    "is_system"      bool         NOT NULL DEFAULT false,
    "pwd_reset_time" timestamp    DEFAULT NULL,
    "dept_id"        int8         NOT NULL,
    "created_by"    int8         DEFAULT NULL,
    "created_at"    timestamp    NOT NULL,
    "modified_by"    int8         DEFAULT NULL,
    "modified_at"    timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_user_username" ON "sys_user" ("username");
CREATE UNIQUE INDEX "uk_user_email"    ON "sys_user" ("email");
CREATE UNIQUE INDEX "uk_user_phone"    ON "sys_user" ("phone");
CREATE INDEX "idx_user_dept_id"        ON "sys_user" ("dept_id");
CREATE INDEX "idx_user_created_by"    ON "sys_user" ("created_by");
CREATE INDEX "idx_user_modified_by"    ON "sys_user" ("modified_by");
COMMENT ON COLUMN "sys_user"."id"             IS 'ID';
COMMENT ON COLUMN "sys_user"."username"       IS '用户名';
COMMENT ON COLUMN "sys_user"."nickname"       IS '昵称';
COMMENT ON COLUMN "sys_user"."password"       IS '密码';
COMMENT ON COLUMN "sys_user"."gender"         IS '性别（0：未知；1：男；2：女）';
COMMENT ON COLUMN "sys_user"."email"          IS '邮箱';
COMMENT ON COLUMN "sys_user"."phone"          IS '手机号码';
COMMENT ON COLUMN "sys_user"."avatar"         IS '头像';
COMMENT ON COLUMN "sys_user"."description"    IS '描述';
COMMENT ON COLUMN "sys_user"."status"         IS '状态（1：启用；2：禁用）';
COMMENT ON COLUMN "sys_user"."is_system"      IS '是否为系统内置数据';
COMMENT ON COLUMN "sys_user"."pwd_reset_time" IS '最后一次修改密码时间';
COMMENT ON COLUMN "sys_user"."dept_id"        IS '部门ID';
COMMENT ON COLUMN "sys_user"."created_by"    IS '创建人';
COMMENT ON COLUMN "sys_user"."created_at"    IS '创建时间';
COMMENT ON COLUMN "sys_user"."modified_by"    IS '修改人';
COMMENT ON COLUMN "sys_user"."modified_at"    IS '修改时间';
COMMENT ON TABLE  "sys_user"                  IS '用户表';
