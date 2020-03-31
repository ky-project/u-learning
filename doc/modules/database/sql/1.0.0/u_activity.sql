DROP TABLE IF EXISTS `u_activity`;
CREATE TABLE `u_activity`
(
  `id`               bigint(20)                                               NOT NULL AUTO_INCREMENT COMMENT '动态ID ',
  `user_ids`         varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '动态主题',
  `activity_topic`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL     DEFAULT NULL COMMENT '动态内容',
  `activity_content` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '动态类型 1：教师 2：学生',
  `activity_type`    tinyint(1)                                               NULL     DEFAULT NULL COMMENT '未查阅的学生/教师id 逗号分隔',
  `valid`            tinyint(1)                                               NULL     DEFAULT 1 COMMENT '是否有效',
  `memo`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '备注',
  `create_time`      datetime(0)                                              NULL     DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `create_by`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL     DEFAULT NULL COMMENT '创建者',
  `update_time`      datetime(0)                                              NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_by`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL     DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = COMPACT;
