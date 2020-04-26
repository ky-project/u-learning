DROP TABLE IF EXISTS `u_examination_result`;
CREATE TABLE `u_examination_result`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试结果ID',
  `question_id` bigint(20) NULL DEFAULT NULL COMMENT '试题ID',
  `examining_id` bigint(20) NULL DEFAULT NULL COMMENT '测试ID',
  `student_answer` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '学生答案',
  `student_score` float NULL DEFAULT NULL COMMENT '得分',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_CorrespondingResult`(`examining_id`) USING BTREE,
  INDEX `FK_MappingResult`(`question_id`) USING BTREE,
  CONSTRAINT `FK_CorrespondingResult` FOREIGN KEY (`examining_id`) REFERENCES `u_student_examination_task` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_MappingResult` FOREIGN KEY (`question_id`) REFERENCES `u_course_question` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
