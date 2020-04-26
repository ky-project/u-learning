DROP TABLE IF EXISTS `u_experiment_result`;
CREATE TABLE `u_experiment_result`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '做实验ID',
  `experiment_id` bigint(20) NULL DEFAULT NULL COMMENT '实验ID',
  `stu_id` bigint(20) NULL DEFAULT NULL COMMENT '学生id',
  `experiment_commit_state` tinyint(1) NOT NULL COMMENT '已提交',
  `experiment_commit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `experiment_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '实验结果',
  `experiment_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '成果附件',
  `experiment_score` float NULL DEFAULT NULL COMMENT '成绩',
  `experiment_advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师反馈',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_ExperimentBeingDone`(`experiment_id`) USING BTREE,
  INDEX `FK_StudentDoingExperiment`(`stu_id`) USING BTREE,
  CONSTRAINT `FK_ExperimentBeingDone` FOREIGN KEY (`experiment_id`) REFERENCES `u_teaching_task_experiment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_StudentDoingExperiment` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;