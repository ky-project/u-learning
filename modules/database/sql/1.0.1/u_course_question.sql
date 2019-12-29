DROP TABLE IF EXISTS `u_course_question`;
CREATE TABLE `u_course_question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '试题ID',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程ID',
  `question_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试题内容',
  `question_URL` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片URL',
  `question_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参考答案',
  `question_knowledge` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识模块',
  `question_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试题类型',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_HoldQuestion`(`course_id`) USING BTREE,
  CONSTRAINT `FK_HoldQuestion` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;