/*
Navicat MySQL Data Transfer

Source Server         : 106.15.75.204
Source Server Version : 50644
Source Host           : 106.15.75.204:3306
Source Database       : ulearning_dev

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2020-04-27 01:05:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for u_activity
-- ----------------------------
DROP TABLE IF EXISTS `u_activity`;
CREATE TABLE `u_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '动态ID ',
  `user_ids` varchar(1024) DEFAULT NULL COMMENT '动态主题',
  `activity_topic` varchar(64) DEFAULT NULL COMMENT '动态内容',
  `activity_content` varchar(256) DEFAULT NULL COMMENT '动态类型 1：教师 2：学生',
  `activity_type` smallint(6) DEFAULT NULL COMMENT '未查阅的学生/教师id 逗号分隔',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `activity_email` text COMMENT '推送邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_course
-- ----------------------------
DROP TABLE IF EXISTS `u_course`;
CREATE TABLE `u_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_number` varchar(20) DEFAULT NULL,
  `course_name` varchar(50) NOT NULL COMMENT '课程名',
  `course_credit` float DEFAULT NULL COMMENT '学分',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_course_documentation
-- ----------------------------
DROP TABLE IF EXISTS `u_course_documentation`;
CREATE TABLE `u_course_documentation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件资料ID',
  `documentation_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `documentation_summary` varchar(1024) DEFAULT NULL COMMENT '摘要',
  `documentation_category` smallint(6) NOT NULL COMMENT '文件资料分类',
  `documentation_shared` tinyint(1) NOT NULL COMMENT '是否共享',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `file_id` bigint(20) DEFAULT NULL COMMENT '课程文件ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldDocumentation` (`file_id`),
  CONSTRAINT `FK_HoldDocumentation` FOREIGN KEY (`file_id`) REFERENCES `u_course_file` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_course_file
-- ----------------------------
DROP TABLE IF EXISTS `u_course_file`;
CREATE TABLE `u_course_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程文件ID',
  `course_id` bigint(20) DEFAULT NULL COMMENT '课程ID',
  `file_url` varchar(256) DEFAULT NULL COMMENT '文件url',
  `file_name` varchar(256) DEFAULT NULL COMMENT '文件名',
  `file_size` bigint(15) DEFAULT NULL COMMENT '文件大小',
  `file_ext` varchar(32) DEFAULT NULL COMMENT '文件后缀名',
  `file_type` smallint(1) DEFAULT NULL COMMENT '文件类型 1：文件 2：文件夹',
  `file_parent_id` bigint(20) DEFAULT NULL COMMENT '文件所属文件夹 0为根目录',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldFile` (`course_id`) USING BTREE,
  CONSTRAINT `FK_HoldFile` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_course_question
-- ----------------------------
DROP TABLE IF EXISTS `u_course_question`;
CREATE TABLE `u_course_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试题ID',
  `course_id` bigint(20) DEFAULT NULL COMMENT '课程ID',
  `question_text` text NOT NULL COMMENT '试题内容',
  `question_URL` varchar(1024) DEFAULT NULL COMMENT '图片URL',
  `question_key` text COMMENT '参考答案',
  `question_knowledge` varchar(50) NOT NULL COMMENT '知识模块',
  `question_type` smallint(6) DEFAULT NULL COMMENT '试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `question_option` varchar(1024) DEFAULT NULL COMMENT '试题选项，"|#|"分隔',
  `question_difficulty` smallint(6) DEFAULT '0' COMMENT '试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldQuestion` (`course_id`) USING BTREE,
  CONSTRAINT `FK_HoldQuestion` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_course_resource
-- ----------------------------
DROP TABLE IF EXISTS `u_course_resource`;
CREATE TABLE `u_course_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教学资源ID',
  `resource_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `resource_summary` varchar(1024) DEFAULT NULL COMMENT '概述',
  `resource_type` smallint(6) NOT NULL COMMENT '类型',
  `resource_shared` tinyint(1) NOT NULL COMMENT '是否共享',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `file_id` bigint(20) DEFAULT NULL COMMENT '课程文件ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldResource` (`file_id`),
  CONSTRAINT `FK_HoldResource` FOREIGN KEY (`file_id`) REFERENCES `u_course_file` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_examination_result
-- ----------------------------
DROP TABLE IF EXISTS `u_examination_result`;
CREATE TABLE `u_examination_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试结果ID',
  `question_id` bigint(20) DEFAULT NULL COMMENT '试题ID',
  `examining_id` bigint(20) DEFAULT NULL COMMENT '测试ID',
  `student_answer` text COMMENT '学生答案',
  `student_score` float DEFAULT NULL COMMENT '得分',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_CorrespondingResult` (`examining_id`) USING BTREE,
  KEY `FK_MappingResult` (`question_id`) USING BTREE,
  CONSTRAINT `FK_CorrespondingResult` FOREIGN KEY (`examining_id`) REFERENCES `u_student_examination_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_MappingResult` FOREIGN KEY (`question_id`) REFERENCES `u_course_question` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_experiment_result
-- ----------------------------
DROP TABLE IF EXISTS `u_experiment_result`;
CREATE TABLE `u_experiment_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '做实验ID',
  `experiment_id` bigint(20) DEFAULT NULL COMMENT '实验ID',
  `stu_id` bigint(20) DEFAULT NULL COMMENT '学生id',
  `experiment_commit_state` tinyint(1) NOT NULL COMMENT '已提交',
  `experiment_commit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `experiment_result` text COMMENT '实验结果',
  `experiment_URL` varchar(255) DEFAULT NULL COMMENT '成果附件',
  `experiment_score` float DEFAULT NULL COMMENT '成绩',
  `experiment_advice` varchar(255) DEFAULT NULL COMMENT '教师反馈',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `experiment_attachment_name` varchar(255) DEFAULT NULL COMMENT '附件名',
  `experiment_shared` tinyint(1) DEFAULT NULL COMMENT '是否共享分享展示 0：否 1：是',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_ExperimentBeingDone` (`experiment_id`) USING BTREE,
  KEY `FK_StudentDoingExperiment` (`stu_id`) USING BTREE,
  CONSTRAINT `FK_ExperimentBeingDone` FOREIGN KEY (`experiment_id`) REFERENCES `u_teaching_task_experiment` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_StudentDoingExperiment` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_file_record
-- ----------------------------
DROP TABLE IF EXISTS `u_file_record`;
CREATE TABLE `u_file_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件记录ID',
  `record_url` varchar(256) DEFAULT NULL COMMENT '文件记录url',
  `record_name` varchar(64) DEFAULT NULL COMMENT '文件记录名',
  `record_size` bigint(15) DEFAULT NULL COMMENT '文件记录名大小',
  `record_type` varchar(32) DEFAULT NULL COMMENT '文件记录类型',
  `record_table` varchar(64) DEFAULT NULL COMMENT '文件记录所属表',
  `record_table_id` bigint(20) DEFAULT NULL COMMENT '文件记录所属表对应id',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_log
-- ----------------------------
DROP TABLE IF EXISTS `u_log`;
CREATE TABLE `u_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `log_username` varchar(10) DEFAULT NULL COMMENT '用户账号',
  `log_description` varchar(100) DEFAULT NULL COMMENT '操作',
  `log_module` varchar(100) DEFAULT NULL COMMENT '执行模块',
  `log_ip` varchar(100) DEFAULT NULL COMMENT '用户ip',
  `log_type` varchar(200) DEFAULT NULL COMMENT '日志类型',
  `log_exception` varchar(255) DEFAULT NULL COMMENT '异常详细',
  `log_params` varchar(1024) DEFAULT NULL COMMENT '请求参数',
  `log_time` bigint(20) DEFAULT NULL COMMENT '请求耗时',
  `log_address` varchar(255) DEFAULT NULL COMMENT '操作地址',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_log_history
-- ----------------------------
DROP TABLE IF EXISTS `u_log_history`;
CREATE TABLE `u_log_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '历史日志ID',
  `log_history_date` varchar(50) DEFAULT NULL COMMENT '历史日志日期',
  `log_history_name` varchar(50) DEFAULT NULL COMMENT '历史日志文件名',
  `log_history_url` varchar(256) DEFAULT NULL COMMENT '历史日志url',
  `log_history_size` bigint(15) DEFAULT NULL COMMENT '文件大小',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_permission`;
CREATE TABLE `u_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名',
  `permission_source` varchar(100) NOT NULL COMMENT '权限码',
  `permission_group` varchar(50) NOT NULL COMMENT '权限组',
  `permission_url` varchar(50) NOT NULL COMMENT '权限url',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_role
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_source` varchar(20) NOT NULL COMMENT '角色资源',
  `is_admin` tinyint(1) NOT NULL COMMENT '是否管理员角色',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `u_role_permission`;
CREATE TABLE `u_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '用户权限ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '教师角色id',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_TeacherRole` (`role_id`) USING BTREE,
  KEY `FK_RolePermission` (`permission_id`) USING BTREE,
  CONSTRAINT `FK_RolePermission` FOREIGN KEY (`permission_id`) REFERENCES `u_permission` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_TeacherRole` FOREIGN KEY (`role_id`) REFERENCES `u_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_student
-- ----------------------------
DROP TABLE IF EXISTS `u_student`;
CREATE TABLE `u_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `stu_number` varchar(20) NOT NULL COMMENT '学号',
  `stu_name` varchar(20) NOT NULL COMMENT '姓名',
  `stu_password` varchar(100) NOT NULL COMMENT '密码',
  `stu_gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `stu_dept` varchar(20) DEFAULT NULL COMMENT '系部',
  `stu_class` varchar(50) DEFAULT NULL COMMENT '班级',
  `stu_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `stu_email` varchar(50) DEFAULT NULL COMMENT 'Email',
  `stu_photo` varchar(1024) DEFAULT NULL COMMENT '照片URL',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `pwd_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '密码更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_student_examination_task
-- ----------------------------
DROP TABLE IF EXISTS `u_student_examination_task`;
CREATE TABLE `u_student_examination_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试ID',
  `examination_task_id` bigint(20) DEFAULT NULL COMMENT '测试任务ID',
  `stu_id` bigint(20) DEFAULT NULL COMMENT '学生id',
  `examining_hostIP` varchar(150) NOT NULL COMMENT 'IP地址',
  `examining_login_time` datetime NOT NULL COMMENT '登录时间',
  `examining_remain_time` int(10) NOT NULL COMMENT '剩余时间',
  `examining_state` smallint(6) NOT NULL COMMENT '测试状态',
  `examining_state_switch_time` datetime NOT NULL COMMENT '状态变更时间',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_ExaminationTaskBeingExamined` (`examination_task_id`) USING BTREE,
  KEY `FK_StudentExamining` (`stu_id`) USING BTREE,
  CONSTRAINT `FK_ExaminationTaskBeingExamined` FOREIGN KEY (`examination_task_id`) REFERENCES `u_teaching_task_examination_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_StudentExamining` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_student_teaching_task
-- ----------------------------
DROP TABLE IF EXISTS `u_student_teaching_task`;
CREATE TABLE `u_student_teaching_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学习ID',
  `teaching_task_id` bigint(20) DEFAULT NULL COMMENT '教学任务ID',
  `stu_id` bigint(20) DEFAULT NULL COMMENT '学生id',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_StudentBeStudying` (`stu_id`) USING BTREE,
  KEY `FK_TeachingTaskBeingStudied` (`teaching_task_id`) USING BTREE,
  CONSTRAINT `FK_StudentBeStudying` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_TeachingTaskBeingStudied` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teacher
-- ----------------------------
DROP TABLE IF EXISTS `u_teacher`;
CREATE TABLE `u_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `tea_number` varchar(20) NOT NULL COMMENT '工号',
  `tea_name` varchar(20) NOT NULL COMMENT '姓名',
  `tea_password` varchar(100) NOT NULL COMMENT '密码',
  `tea_gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `tea_dept` varchar(20) DEFAULT NULL COMMENT '部门',
  `tea_title` varchar(50) DEFAULT NULL COMMENT '职称',
  `tea_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `tea_email` varchar(50) DEFAULT NULL COMMENT 'Email',
  `tea_photo` varchar(1024) DEFAULT NULL COMMENT '照片URL',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `pwd_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '密码更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teacher_role
-- ----------------------------
DROP TABLE IF EXISTS `u_teacher_role`;
CREATE TABLE `u_teacher_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教师角色ID',
  `tea_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HasRole` (`role_id`) USING BTREE,
  KEY `FK_ThisTeacher` (`tea_id`) USING BTREE,
  CONSTRAINT `FK_HasRole` FOREIGN KEY (`role_id`) REFERENCES `u_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_ThisTeacher` FOREIGN KEY (`tea_id`) REFERENCES `u_teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teaching_task
-- ----------------------------
DROP TABLE IF EXISTS `u_teaching_task`;
CREATE TABLE `u_teaching_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教学任务ID',
  `course_id` bigint(20) DEFAULT NULL COMMENT '课程ID',
  `tea_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `teaching_task_alias` varchar(255) NOT NULL COMMENT '教学任务别称',
  `term` varchar(50) NOT NULL COMMENT '开课学期',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldTask` (`course_id`) USING BTREE,
  KEY `FK_UndertakeTask` (`tea_id`) USING BTREE,
  CONSTRAINT `FK_HoldTask` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_UndertakeTask` FOREIGN KEY (`tea_id`) REFERENCES `u_teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teaching_task_examination_task
-- ----------------------------
DROP TABLE IF EXISTS `u_teaching_task_examination_task`;
CREATE TABLE `u_teaching_task_examination_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试任务ID',
  `teaching_task_id` bigint(20) DEFAULT NULL COMMENT '教学任务ID',
  `examination_name` varchar(64) NOT NULL COMMENT '测试任务名称',
  `examination_duration` smallint(6) NOT NULL COMMENT '测试时长',
  `examination_state` smallint(6) NOT NULL COMMENT '任务状态',
  `examination_parameters` varchar(1024) NOT NULL COMMENT '试题参数',
  `examination_show_result` tinyint(1) NOT NULL COMMENT '是否反馈测试结果',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldExaminationTask` (`teaching_task_id`) USING BTREE,
  CONSTRAINT `FK_HoldExaminationTask` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teaching_task_experiment
-- ----------------------------
DROP TABLE IF EXISTS `u_teaching_task_experiment`;
CREATE TABLE `u_teaching_task_experiment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '实验ID',
  `teaching_task_id` bigint(20) DEFAULT NULL COMMENT '课程ID',
  `experiment_order` smallint(6) NOT NULL COMMENT '序号',
  `experiment_title` varchar(255) NOT NULL COMMENT '标题',
  `experiment_content` text COMMENT '内容要求',
  `experiment_attachment` varchar(1024) DEFAULT NULL COMMENT '附件URL',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `experiment_attachment_name` varchar(255) DEFAULT NULL COMMENT '附件名',
  `experiment_shared` tinyint(1) DEFAULT NULL COMMENT '是否共享',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_HoldExperiment` (`teaching_task_id`) USING BTREE,
  CONSTRAINT `FK_HoldExperiment` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for u_teaching_task_notice
-- ----------------------------
DROP TABLE IF EXISTS `u_teaching_task_notice`;
CREATE TABLE `u_teaching_task_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通告ID',
  `teaching_task_id` bigint(20) DEFAULT NULL COMMENT '教学任务ID',
  `notice_title` varchar(255) NOT NULL COMMENT '标题',
  `notice_content` text COMMENT '内容',
  `notice_attachment` varchar(1024) DEFAULT NULL COMMENT '附件URL',
  `notice_shared` tinyint(1) DEFAULT NULL COMMENT '是否共享',
  `notice_post_time` datetime NOT NULL COMMENT '提交时间',
  `notice_keywords` varchar(255) DEFAULT NULL COMMENT '关键词',
  `valid` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `notice_attachment_name` varchar(255) DEFAULT NULL COMMENT '附件名',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_ReleaseNotice` (`teaching_task_id`) USING BTREE,
  CONSTRAINT `FK_ReleaseNotice` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` varchar(512) DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_cron` varchar(128) NOT NULL COMMENT '任务执行CRON',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
