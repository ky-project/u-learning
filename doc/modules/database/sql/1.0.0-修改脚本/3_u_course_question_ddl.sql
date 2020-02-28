ALTER TABLE u_course_question ADD COLUMN `question_option` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试题选项，"|#|"分隔';
