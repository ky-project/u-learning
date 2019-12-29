DROP TABLE IF EXISTS `u_teaching_task_examination_task`;
CREATE TABLE `u_teaching_task_examination_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试任务ID',
  `teaching_task_id` bigint(20) NULL DEFAULT NULL COMMENT '教学任务ID',
  `examination_name` varchar(64) NOT NULL COMMENT '测试任务名称',
  `examination_duration` smallint(6) NOT NULL COMMENT '测试时长',
  `examination_state` smallint(6) NOT NULL COMMENT '任务状态',
  `examination_parameters` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试题参数',
  `examination_show_result` tinyint(1) NOT NULL COMMENT '是否反馈测试结果',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_HoldExaminationTask`(`teaching_task_id`) USING BTREE,
  CONSTRAINT `FK_HoldExaminationTask` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;