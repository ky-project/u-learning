-- 删除字段
alter table u_course_documentation drop column documentation_URL;

alter table u_course_documentation drop column documentation_attachment;

ALTER TABLE u_course_documentation MODIFY `documentation_category` smallint(6) NOT NULL COMMENT '文件资料分类';