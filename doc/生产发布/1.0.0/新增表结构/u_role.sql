DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role`  (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
`role_name`            varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
`role_source`          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色资源',
`is_admin`          tinyint(1) NOT NULL COMMENT '是否管理员角色',
`valid` tinyint(1) NULL DEFAULT 1 COMMENT '是否有效',
`memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
`create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
`update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
`update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;