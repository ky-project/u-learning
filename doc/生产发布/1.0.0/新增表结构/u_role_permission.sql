DROP TABLE IF EXISTS `u_role_permission`;
CREATE TABLE `u_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '用户权限ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '教师角色id',
  `valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_TeacherRole`(`role_id`) USING BTREE,
  INDEX `FK_RolePermission`(`permission_id`) USING BTREE,
  CONSTRAINT `FK_TeacherRole` FOREIGN KEY (`role_id`) REFERENCES `u_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_RolePermission` FOREIGN KEY (`permission_id`) REFERENCES `u_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;