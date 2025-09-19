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


CREATE TABLE IF NOT EXISTS "sys_dict" (
    "id"          int8         NOT NULL,
    "name"        varchar(30)  NOT NULL,
    "code"        varchar(30)  NOT NULL,
    "description" varchar(200) DEFAULT NULL,
    "is_system"   bool         NOT NULL DEFAULT false,
    "created_by" int8         NOT NULL,
    "created_at" timestamp    NOT NULL,
    "modified_by" int8         DEFAULT NULL,
    "modified_at" timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_dict_name" ON "sys_dict" ("name");
CREATE UNIQUE INDEX "uk_dict_code" ON "sys_dict" ("code");
COMMENT ON COLUMN "sys_dict"."id"          IS 'ID';
COMMENT ON COLUMN "sys_dict"."name"        IS '名称';
COMMENT ON COLUMN "sys_dict"."code"        IS '编码';
COMMENT ON COLUMN "sys_dict"."description" IS '描述';
COMMENT ON COLUMN "sys_dict"."is_system"   IS '是否为系统内置数据';
COMMENT ON COLUMN "sys_dict"."created_by" IS '创建人';
COMMENT ON COLUMN "sys_dict"."created_at" IS '创建时间';
COMMENT ON COLUMN "sys_dict"."modified_by" IS '修改人';
COMMENT ON COLUMN "sys_dict"."modified_at" IS '修改时间';
COMMENT ON TABLE  "sys_dict"               IS '字典表';

CREATE TABLE IF NOT EXISTS "sys_dict_item" (
    "id"          int8         NOT NULL,
    "label"       varchar(30)  NOT NULL,
    "value"       varchar(30)  NOT NULL,
    "color"       varchar(30)  DEFAULT NULL,
    "sort"        int4         NOT NULL DEFAULT 999,
    "description" varchar(200) DEFAULT NULL,
    "status"      int2         NOT NULL DEFAULT 1,
    "dict_id"     int8         NOT NULL,
    "created_by" int8         NOT NULL,
    "created_at" timestamp    NOT NULL,
    "modified_by" int8         DEFAULT NULL,
    "modified_at" timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_dict_item_value_dict_id" ON "sys_dict_item" ("value", "dict_id");
CREATE INDEX "idx_dict_item_dict_id"     ON "sys_dict_item" ("dict_id");
CREATE INDEX "idx_dict_item_created_by" ON "sys_dict_item" ("created_by");
CREATE INDEX "idx_dict_item_modified_by" ON "sys_dict_item" ("modified_by");
COMMENT ON COLUMN "sys_dict_item"."id"          IS 'ID';
COMMENT ON COLUMN "sys_dict_item"."label"       IS '标签';
COMMENT ON COLUMN "sys_dict_item"."value"       IS '值';
COMMENT ON COLUMN "sys_dict_item"."color"       IS '标签颜色';
COMMENT ON COLUMN "sys_dict_item"."sort"        IS '排序';
COMMENT ON COLUMN "sys_dict_item"."description" IS '描述';
COMMENT ON COLUMN "sys_dict_item"."status"      IS '状态（1：启用；2：禁用）';
COMMENT ON COLUMN "sys_dict_item"."dict_id"     IS '字典ID';
COMMENT ON COLUMN "sys_dict_item"."created_by" IS '创建人';
COMMENT ON COLUMN "sys_dict_item"."created_at" IS '创建时间';
COMMENT ON COLUMN "sys_dict_item"."modified_by" IS '修改人';
COMMENT ON COLUMN "sys_dict_item"."modified_at" IS '修改时间';
COMMENT ON TABLE  "sys_dict_item"               IS '字典项表';


CREATE TABLE IF NOT EXISTS "sys_menu" (
    "id"          int8         NOT NULL,
    "title"       varchar(30)  NOT NULL,
    "parent_id"   int8         NOT NULL DEFAULT 0,
    "type"        int2         NOT NULL DEFAULT 1,
    "path"        varchar(255) DEFAULT NULL,
    "name"        varchar(50)  DEFAULT NULL,
    "component"   varchar(255) DEFAULT NULL,
    "redirect"    varchar(255) DEFAULT NULL,
    "icon"        varchar(50)  DEFAULT NULL,
    "is_external" bool         DEFAULT false,
    "is_cache"    bool         DEFAULT false,
    "is_hidden"   bool         DEFAULT false,
    "permission"  varchar(100) DEFAULT NULL,
    "sort"        int4         NOT NULL DEFAULT 999,
    "status"      int2         NOT NULL DEFAULT 1,
    "created_by" int8         NOT NULL,
    "created_at" timestamp    NOT NULL,
    "modified_by" int8         DEFAULT NULL,
    "modified_at" timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE INDEX "idx_menu_parent_id"   ON "sys_menu" ("parent_id");
CREATE INDEX "idx_menu_created_by" ON "sys_menu" ("created_by");
CREATE INDEX "idx_menu_modified_by" ON "sys_menu" ("modified_by");
CREATE UNIQUE INDEX "uk_menu_title_parent_id" ON "sys_menu" ("title", "parent_id");
COMMENT ON COLUMN "sys_menu"."id"          IS 'ID';
COMMENT ON COLUMN "sys_menu"."title"       IS '标题';
COMMENT ON COLUMN "sys_menu"."parent_id"   IS '上级菜单ID';
COMMENT ON COLUMN "sys_menu"."type"        IS '类型（1：目录；2：菜单；3：按钮）';
COMMENT ON COLUMN "sys_menu"."path"        IS '路由地址';
COMMENT ON COLUMN "sys_menu"."name"        IS '组件名称';
COMMENT ON COLUMN "sys_menu"."component"   IS '组件路径';
COMMENT ON COLUMN "sys_menu"."redirect"    IS '重定向地址';
COMMENT ON COLUMN "sys_menu"."icon"        IS '图标';
COMMENT ON COLUMN "sys_menu"."is_external" IS '是否外链';
COMMENT ON COLUMN "sys_menu"."is_cache"    IS '是否缓存';
COMMENT ON COLUMN "sys_menu"."is_hidden"   IS '是否隐藏';
COMMENT ON COLUMN "sys_menu"."permission"  IS '权限标识';
COMMENT ON COLUMN "sys_menu"."sort"        IS '排序';
COMMENT ON COLUMN "sys_menu"."status"      IS '状态（1：启用；2：禁用）';
COMMENT ON COLUMN "sys_menu"."created_by" IS '创建人';
COMMENT ON COLUMN "sys_menu"."created_at" IS '创建时间';
COMMENT ON COLUMN "sys_menu"."modified_by" IS '修改人';
COMMENT ON COLUMN "sys_menu"."modified_at" IS '修改时间';
COMMENT ON TABLE  "sys_menu"               IS '菜单表';


CREATE TABLE IF NOT EXISTS "sys_dept" (
    "id"          int8         NOT NULL,
    "name"        varchar(30)  NOT NULL,
    "parent_id"   int8         NOT NULL DEFAULT 0,
    "ancestors"   varchar(512) NOT NULL DEFAULT '',
    "description" varchar(200) DEFAULT NULL,
    "sort"        int4         NOT NULL DEFAULT 999,
    "status"      int2         NOT NULL DEFAULT 1,
    "is_system"   bool         NOT NULL DEFAULT false,
    "created_by" int8         NOT NULL,
    "created_at" timestamp    NOT NULL,
    "modified_by" int8         DEFAULT NULL,
    "modified_at" timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE INDEX "idx_dept_parent_id"   ON "sys_dept" ("parent_id");
CREATE INDEX "idx_dept_created_by" ON "sys_dept" ("created_by");
CREATE INDEX "idx_dept_modified_by" ON "sys_dept" ("modified_by");
CREATE UNIQUE INDEX "uk_dept_name_parent_id" ON "sys_dept" ("name", "parent_id");
COMMENT ON COLUMN "sys_dept"."id"          IS 'ID';
COMMENT ON COLUMN "sys_dept"."name"        IS '名称';
COMMENT ON COLUMN "sys_dept"."parent_id"   IS '上级部门ID';
COMMENT ON COLUMN "sys_dept"."ancestors"   IS '祖级列表';
COMMENT ON COLUMN "sys_dept"."description" IS '描述';
COMMENT ON COLUMN "sys_dept"."sort"        IS '排序';
COMMENT ON COLUMN "sys_dept"."status"      IS '状态（1：启用；2：禁用）';
COMMENT ON COLUMN "sys_dept"."is_system"   IS '是否为系统内置数据';
COMMENT ON COLUMN "sys_dept"."created_by" IS '创建人';
COMMENT ON COLUMN "sys_dept"."created_at" IS '创建时间';
COMMENT ON COLUMN "sys_dept"."modified_by" IS '修改人';
COMMENT ON COLUMN "sys_dept"."modified_at" IS '修改时间';
COMMENT ON TABLE  "sys_dept"               IS '部门表';


CREATE TABLE IF NOT EXISTS "sys_role" (
    "id"                  int8         NOT NULL,
    "name"                varchar(30)  NOT NULL,
    "code"                varchar(30)  NOT NULL,
    "data_scope"          int2         NOT NULL DEFAULT 4,
    "description"         varchar(200) DEFAULT NULL,
    "sort"                int4         NOT NULL DEFAULT 999,
    "is_system"           bool         NOT NULL DEFAULT false,
    "menu_check_strictly" bool DEFAULT true,
    "dept_check_strictly" bool DEFAULT true,
    "created_by"         int8         NOT NULL,
    "created_at"         timestamp    NOT NULL,
    "modified_by"         int8         DEFAULT NULL,
    "modified_at"         timestamp    DEFAULT NULL,
    PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX "uk_role_name"  ON "sys_role" ("name");
CREATE UNIQUE INDEX "uk_role_code"  ON "sys_role" ("code");
CREATE INDEX "idx_role_created_by" ON "sys_role" ("created_by");
CREATE INDEX "idx_role_modified_by" ON "sys_role" ("modified_by");
COMMENT ON COLUMN "sys_role"."id"          IS 'ID';
COMMENT ON COLUMN "sys_role"."name"        IS '名称';
COMMENT ON COLUMN "sys_role"."code"        IS '编码';
COMMENT ON COLUMN "sys_role"."data_scope"  IS '数据权限（1：全部数据权限；2：本部门及以下数据权限；3：本部门数据权限；4：仅本人数据权限；5：自定义数据权限）';
COMMENT ON COLUMN "sys_role"."description" IS '描述';
COMMENT ON COLUMN "sys_role"."sort"        IS '排序';
COMMENT ON COLUMN "sys_role"."is_system"   IS '是否为系统内置数据';
COMMENT ON COLUMN "sys_role"."menu_check_strictly" IS '菜单选择是否父子节点关联';
COMMENT ON COLUMN "sys_role"."dept_check_strictly" IS '部门选择是否父子节点关联';
COMMENT ON COLUMN "sys_role"."created_by" IS '创建人';
COMMENT ON COLUMN "sys_role"."created_at" IS '创建时间';
COMMENT ON COLUMN "sys_role"."modified_by" IS '修改人';
COMMENT ON COLUMN "sys_role"."modified_at" IS '修改时间';
COMMENT ON TABLE  "sys_role"               IS '角色表';
