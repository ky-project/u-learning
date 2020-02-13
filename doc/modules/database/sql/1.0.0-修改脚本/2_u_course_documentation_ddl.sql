-- 删除外键
alter table u_course_documentation drop foreign key FK_HoldDocumentation;
-- 删除字段
alter table u_course_documentation drop column course_id;

-- 添加字段
alter table u_course_documentation add column file_id bigint(20) NULL DEFAULT NULL COMMENT  '课程文件ID';
-- 添加外键
ALTER TABLE u_course_documentation ADD CONSTRAINT `FK_HoldDocumentation` FOREIGN KEY (`file_id`) REFERENCES `u_course_file` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;