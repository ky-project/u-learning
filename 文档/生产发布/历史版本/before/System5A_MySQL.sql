/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     19/07/20 15:53:35                            */
/*==============================================================*/


drop table if exists course;

-- drop index StudentDoingExperiment_FK on do_experiment;

drop table if exists do_experiment;

drop table if exists documentation;

drop table if exists examination_result;

drop table if exists examination_task;

-- drop index StudentExamining_FK on examining;

drop table if exists examining;

drop table if exists experiment;

drop table if exists notice;

drop table if exists permission;

drop table if exists question;

drop table if exists resource;

drop table if exists role_permission;

-- drop index StudentBeStudying_FK on studying;

drop table if exists studying;

drop table if exists sys_log;

drop table if exists sys_role;

drop table if exists sys_user;

-- drop index UndertakeTask_FK on teaching_task;

drop table if exists teaching_task;

drop table if exists user_role;

/*==============================================================*/
/* Table: course                                                */
/*==============================================================*/
create table course
(
   course_id            bigint not null auto_increment comment '课程ID',
   course_number        varchar(8) not null comment '课程号',
   course_name          varchar(50) not null comment '课程名',
   course_credit        float(6, 1) not null comment '学分',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (course_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: do_experiment                                         */
/*==============================================================*/
create table do_experiment
(
   doexperiment_id      bigint not null auto_increment comment '做实验ID',
   experiment_id        bigint comment '实验ID',
   user_id              bigint comment '用户id',
   experiment_commit_state bool not null comment '已提交',
   experiment_commit_time datetime comment '提交时间',
   experiment_result    text comment '实验结果',
   experiment_URL       varchar(255) comment '成果附件',
   experiment_score     float comment '成绩',
   experiment_advice    varchar(255) comment '教师反馈',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (doexperiment_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Index: StudentDoingExperiment_FK                             */
/*==============================================================*/
create index StudentDoingExperiment_FK on do_experiment
(
   doexperiment_id
);

/*==============================================================*/
/* Table: documentation                                         */
/*==============================================================*/
create table documentation
(
   documentation_id     bigint not null auto_increment comment '文件资料ID',
   course_id            bigint comment '课程ID',
   documentation_title  varchar(255) not null comment '标题',
   documentation_summary varchar(1024) comment '摘要',
   documentation_URL    varchar(1024) comment '图片URL',
   documentation_attachment varchar(1024) comment '附件URL',
   documentation_category varchar(255) not null comment '文件资料分类',
   documentation_shared bool not null comment '是否共享',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (documentation_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: examination_result                                    */
/*==============================================================*/
create table examination_result
(
   examination_result_id bigint not null auto_increment comment '测试结果ID',
   question_id          bigint comment '试题ID',
   examining_id         bigint comment '测试ID',
   student_answer       text comment '学生答案',
   student_score        float comment '得分',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (examination_result_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: examination_task                                      */
/*==============================================================*/
create table examination_task
(
   examination_task_id  bigint not null auto_increment comment '测试任务ID',
   teaching_task_id     bigint comment '教学任务ID',
   examination_duration smallint not null comment '测试时长',
   examination_state    smallint not null comment '任务状态',
   examination_parameters varchar(1024) not null comment '试题参数',
   examination_show_result bool not null comment '是否反馈测试结果',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (examination_task_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: examining                                             */
/*==============================================================*/
create table examining
(
   examining_id         bigint not null auto_increment comment '测试ID',
   examination_task_id  bigint comment '测试任务ID',
   user_id              bigint comment '用户id',
   examining_hostIP     varchar(150) not null comment 'IP地址',
   examining_login_time datetime not null comment '登录时间',
   examining_remain_time smallint not null comment '剩余时间',
   examining_state      smallint not null comment '测试状态',
   examining_state_switch_time datetime not null comment '状态变更时间',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (examining_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Index: StudentExamining_FK                                   */
/*==============================================================*/
create index StudentExamining_FK on examining
(
   examining_id
);

/*==============================================================*/
/* Table: experiment                                            */
/*==============================================================*/
create table experiment
(
   experiment_id        bigint not null auto_increment comment '实验ID',
   course_id            bigint comment '课程ID',
   experiment_order     smallint not null comment '序号',
   experiment_title     varchar(255) not null comment '标题',
   experiment_content   text comment '内容要求',
   experiment_attachment varchar(1024) comment '附件URL',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (experiment_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice
(
   notice_id            bigint not null auto_increment comment '通告ID',
   teaching_task_id     bigint comment '教学任务ID',
   notice_title         varchar(255) not null comment '标题',
   notice_content       text comment '内容',
   notice_attachment    varchar(1024) comment '附件URL',
   notice_shared        bool not null comment '是否共享',
   notice_post_time     datetime not null comment '提交时间',
   notice_keywords      varchar(255) comment '关键词',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (notice_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   permission_id        bigint not null auto_increment comment '权限ID',
   permission_name      varchar(50) not null comment '权限名',
   permission_source    varchar(100) not null comment '权限码',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (permission_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: question                                              */
/*==============================================================*/
create table question
(
   question_id          bigint not null auto_increment comment '试题ID',
   course_id            bigint comment '课程ID',
   question_text        text not null comment '试题内容',
   question_URL         varchar(1024) comment '图片URL',
   question_key         text comment '参考答案',
   question_knowledge   varchar(50) not null comment '知识模块',
   question_type        varchar(10) not null comment '试题类型',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (question_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: resource                                              */
/*==============================================================*/
create table resource
(
   resource_id          bigint not null auto_increment comment '教学资源ID',
   course_id            bigint comment '课程ID',
   resource_title       varchar(255) not null comment '标题',
   resource_summary     varchar(1024) comment '概述',
   resource_URL         varchar(1024) not null comment '资源URL',
   resource_type        smallint not null comment '类型',
   resource_shared      bool not null comment '是否共享',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (resource_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission
(
   role_permission_id   bigint not null auto_increment comment '权限id',
   permission_id        bigint comment '用户权限ID',
   role_id              bigint comment '角色ID',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (role_permission_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: studying                                              */
/*==============================================================*/
create table studying
(
   studying_id          bigint not null auto_increment comment '学习ID',
   teaching_task_id     bigint comment '教学任务ID',
   user_id              bigint comment '用户id',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (studying_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Index: StudentBeStudying_FK                                  */
/*==============================================================*/
create index StudentBeStudying_FK on studying
(
   studying_id
);

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   log_id               bigint not null auto_increment comment '日志ID',
   log_user_number      varchar(10) comment '用户编号',
   log_user_name        varchar(10) comment '用户姓名',
   log_operator         varchar(100) comment '操作',
   log_module           varchar(100) comment '执行模块',
   log_ip               varchar(100) comment '用户ip',
   log_content          varchar(200) comment '具体操作内容',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (log_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   role_id              bigint not null auto_increment comment '角色ID',
   role_name            varchar(20) comment '角色名称',
   role_source          varchar(20) comment '角色资源',
   role_manage          bool comment '是否管理员角色',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (role_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              bigint not null auto_increment comment '用户id',
   user_number          varchar(20) not null comment '编号',
   user_name            varchar(20) not null comment '姓名',
   user_password        varchar(30) not null comment '密码',
   user_gender          varchar(2) comment '性别',
   user_class           varchar(50) comment '班级',
   user_title           varchar(20) comment '职称',
   user_phone           varchar(20) comment '联系电话',
   user_email           varchar(50) comment 'Email',
   user_photo           varchar(1024) comment '照片URL',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (user_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: teaching_task                                         */
/*==============================================================*/
create table teaching_task
(
   teaching_task_id     bigint not null auto_increment comment '教学任务ID',
   course_id            bigint comment '课程ID',
   user_id              bigint,
   teaching_task_alias  varchar(255) not null comment '教学任务别称',
   term                 varchar(50) not null comment '开课学期',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (teaching_task_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Index: UndertakeTask_FK                                      */
/*==============================================================*/
create index UndertakeTask_FK on teaching_task
(
   teaching_task_id
);

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   user_role_id         bigint not null auto_increment comment '用户角色ID',
   user_id              bigint comment '用户id',
   role_id              bigint comment '角色ID',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (user_role_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

alter table do_experiment add constraint FK_ExperimentBeingDone foreign key (experiment_id)
      references experiment (experiment_id);

alter table do_experiment add constraint FK_StudentDoingExperiment foreign key (user_id)
      references sys_user (user_id);

alter table documentation add constraint FK_HoldDocumentation foreign key (course_id)
      references course (course_id);

alter table examination_result add constraint FK_CorrespondingResult foreign key (examining_id)
      references examining (examining_id);

alter table examination_result add constraint FK_MappingResult foreign key (question_id)
      references question (question_id);

alter table examination_task add constraint FK_HoldExaminationTask foreign key (teaching_task_id)
      references teaching_task (teaching_task_id);

alter table examining add constraint FK_ExaminationTaskBeingExamined foreign key (examination_task_id)
      references examination_task (examination_task_id);

alter table examining add constraint FK_StudentExamining foreign key (user_id)
      references sys_user (user_id);

alter table experiment add constraint FK_HoldExperiment foreign key (course_id)
      references course (course_id);

alter table notice add constraint FK_ReleaseNotice foreign key (teaching_task_id)
      references teaching_task (teaching_task_id);

alter table question add constraint FK_HoldQuestion foreign key (course_id)
      references course (course_id);

alter table resource add constraint FK_HoldResource foreign key (course_id)
      references course (course_id);

alter table role_permission add constraint FK_Relationship_18 foreign key (permission_id)
      references permission (permission_id);

alter table role_permission add constraint FK_Relationship_21 foreign key (role_id)
      references sys_role (role_id);

alter table studying add constraint FK_StudentBeStudying foreign key (user_id)
      references sys_user (user_id);

alter table studying add constraint FK_TeachingTaskBeingStudied foreign key (teaching_task_id)
      references teaching_task (teaching_task_id);

alter table teaching_task add constraint FK_HoldTask foreign key (course_id)
      references course (course_id);

alter table teaching_task add constraint FK_UndertakeTask foreign key (user_id)
      references sys_user (user_id);

alter table user_role add constraint FK_Relationship_19 foreign key (user_id)
      references sys_user (user_id);

alter table user_role add constraint FK_Relationship_20 foreign key (role_id)
      references sys_role (role_id);

