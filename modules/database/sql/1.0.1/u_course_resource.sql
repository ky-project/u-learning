DROP TABLE IF EXISTS `u_course_resource`;
CREATE TABLE `u_course_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教学资源ID',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程ID',
  `resource_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `resource_summary` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '概述',
  `resource_URL` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源URL',
  `resource_type` smallint(6) NOT NULL COMMENT '类型',
  `resource_shared` tinyint(1) NOT NULL COMMENT '是否共享',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_HoldResource`(`course_id`) USING BTREE,
  CONSTRAINT `FK_HoldResource` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;