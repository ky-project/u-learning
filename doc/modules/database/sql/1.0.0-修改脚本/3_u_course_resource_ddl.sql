-- 删除字段
alter table u_course_resource drop column resource_URL;

ALTER TABLE u_course_resource MODIFY `resource_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标题'
