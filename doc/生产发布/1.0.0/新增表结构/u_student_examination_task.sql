DROP TABLE IF EXISTS `u_student_examination_task`;
CREATE TABLE `u_student_examination_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试ID',
  `examination_task_id` bigint(20) NULL DEFAULT NULL COMMENT '测试任务ID',
  `stu_id` bigint(20) NULL DEFAULT NULL COMMENT '学生id',
  `examining_hostIP` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP地址',
  `examining_login_time` datetime(0) NOT NULL COMMENT '登录时间',
  `examining_remain_time` int(10) NOT NULL COMMENT '剩余时间',
  `examining_state` smallint(6) NOT NULL COMMENT '测试状态',
  `examining_state_switch_time` datetime(0) NOT NULL COMMENT '状态变更时间',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_ExaminationTaskBeingExamined`(`examination_task_id`) USING BTREE,
  INDEX `FK_StudentExamining`(`stu_id`) USING BTREE,
  CONSTRAINT `FK_ExaminationTaskBeingExamined` FOREIGN KEY (`examination_task_id`) REFERENCES `u_teaching_task_examination_task` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_StudentExamining` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
