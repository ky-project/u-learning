/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     19/11/05 23:08:10                            */
/*==============================================================*/


drop table if exists course;

drop table if exists do_experiment;

drop table if exists documentation;

drop table if exists examination_result;

drop table if exists examination_task;

drop table if exists examining;

drop table if exists experiment;

drop table if exists notice;

drop table if exists permission;

drop table if exists question;

drop table if exists resource;

drop table if exists studying;

drop table if exists sys_log;

drop table if exists sys_student;

drop table if exists sys_teacher;

drop table if exists teaching_task;

drop table if exists user_permission;

/*==============================================================*/
/* Table: course                                                */
/*==============================================================*/
create table course
(
   course_id            bigint not null auto_increment comment '课程ID',
   course_number        varchar(8) not null comment '课程号',
   course_name          varchar(50) not null comment '课程名',
   course_credit        smallint not null comment '学分',
   valid                bool DEFAULT 1 comment '是否有效',
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
   stu_id               bigint comment '学生id',
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
   stu_id               bigint comment '学生id',
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
   permission_group     varchar(50) not null comment '权限组',
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
/* Table: studying                                              */
/*==============================================================*/
create table studying
(
   studying_id          bigint not null auto_increment comment '学习ID',
   teaching_task_id     bigint comment '教学任务ID',
   stu_id               bigint comment '学生id',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (studying_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   log_id               bigint not null auto_increment comment '日志ID',
   log_user_number      varchar(10) comment '用户编号',
   log_user_name        varchar(10) comment '用户姓名',
   log_description      varchar(100) comment '操作',
   log_module           varchar(100) comment '执行模块',
   log_ip               varchar(100) comment '用户ip',
   log_type             varchar(200) comment '日志类型',
   log_exception        varchar(255) comment '异常详细',
   log_params           varchar(1024) comment '请求参数',
   log_time             bigint comment '请求耗时',
   log_address          varchar(255) comment '操作地址',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (log_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: sys_student                                           */
/*==============================================================*/
create table sys_student
(
   stu_id               bigint not null auto_increment comment '学生id',
   stu_number           varchar(20) not null comment '学号',
   stu_name             varchar(20) not null comment '姓名',
   stu_password         varchar(30) not null comment '密码',
   stu_gender           varchar(2) comment '性别',
   stu_dept             varchar(20) comment '系部',
   stu_class            varchar(50) comment '班级',
   stu_phone            varchar(20) comment '联系电话',
   stu_email            varchar(50) comment 'Email',
   stu_photo            varchar(1024) comment '照片URL',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (stu_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: sys_teacher                                           */
/*==============================================================*/
create table sys_teacher
(
   tea_id               bigint not null auto_increment comment '教师id',
   tea_number           varchar(20) not null comment '工号',
   tea_name             varchar(20) not null comment '姓名',
   tea_password         varchar(30) not null comment '密码',
   tea_gender           varchar(2) comment '性别',
   tea_dept             varchar(20) comment '部门',
   tea_title            varchar(50) comment '职称',
   tea_phone            varchar(20) comment '联系电话',
   tea_email            varchar(50) comment 'Email',
   tea_photo            varchar(1024) comment '照片URL',
   manage               smallint comment '是否是管理员',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (tea_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

/*==============================================================*/
/* Table: teaching_task                                         */
/*==============================================================*/
create table teaching_task
(
   teaching_task_id     bigint not null auto_increment comment '教学任务ID',
   course_id            bigint comment '课程ID',
   tea_id               bigint comment '教师id',
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
/* Table: user_permission                                       */
/*==============================================================*/
create table teacher_permission
(
   role_permission_id   bigint not null auto_increment comment '权限id',
   permission_id        bigint comment '用户权限ID',
   tea_id               bigint comment '教师id',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (role_permission_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

create table student_permission
(
   role_permission_id   bigint not null auto_increment comment '权限id',
   permission_id        bigint comment '用户权限ID',
   stu_id               bigint comment '学生id',
   valid                bool NULL DEFAULT 1 comment '是否有效',
   memo                 varchar(255) NULL comment '备注',
   create_time          datetime NULL DEFAULT CURRENT_TIMESTAMP(0) comment '创建时间',
   create_by            varchar(50) NULL DEFAULT NULL comment '创建者',
   update_time          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) comment '更新时间',
   update_by            varchar(50) NULL DEFAULT NULL comment '更新者',
   primary key (role_permission_id)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

alter table do_experiment add constraint FK_ExperimentBeingDone foreign key (experiment_id)
      references experiment (experiment_id);

alter table do_experiment add constraint FK_StudentDoingExperiment foreign key (stu_id)
      references sys_student (stu_id);

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

alter table examining add constraint FK_StudentExamining foreign key (stu_id)
      references sys_student (stu_id);

alter table experiment add constraint FK_HoldExperiment foreign key (course_id)
      references course (course_id);

alter table notice add constraint FK_ReleaseNotice foreign key (teaching_task_id)
      references teaching_task (teaching_task_id);

alter table question add constraint FK_HoldQuestion foreign key (course_id)
      references course (course_id);

alter table resource add constraint FK_HoldResource foreign key (course_id)
      references course (course_id);

alter table studying add constraint FK_StudentBeStudying foreign key (stu_id)
      references sys_student (stu_id);

alter table studying add constraint FK_TeachingTaskBeingStudied foreign key (teaching_task_id)
      references teaching_task (teaching_task_id);

alter table teaching_task add constraint FK_HoldTask foreign key (course_id)
      references course (course_id);

alter table teaching_task add constraint FK_UndertakeTask foreign key (tea_id)
      references sys_teacher (tea_id);

alter table student_permission add constraint FK_StudentObtain foreign key (stu_id)
      references sys_student (stu_id);

alter table teacher_permission add constraint FK_TeacherObtain foreign key (tea_id)
      references sys_teacher (tea_id);

alter table student_permission add constraint FK_Studentdistribution foreign key (permission_id)
      references permission (permission_id);

alter table teacher_permission add constraint FK_Teacherdistribution foreign key (permission_id)
      references permission (permission_id);
