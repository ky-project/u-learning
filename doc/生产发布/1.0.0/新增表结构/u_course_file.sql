DROP TABLE IF EXISTS `u_course_file`;
CREATE TABLE `u_course_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程文件ID',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程ID',
  `file_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文件url',
  `file_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文件名',
  `file_size` bigint(15) NULL COMMENT '文件大小',
  `file_ext` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文件后缀名',
  `file_type` smallint(6) NULL COMMENT '文件类型 1：文件 2：文件夹',
  `file_parent_id` bigint(20) NULL DEFAULT NULL COMMENT '文件所属文件夹 0为根目录',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_HoldFile`(`course_id`) USING BTREE,
  CONSTRAINT `FK_HoldFile` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;