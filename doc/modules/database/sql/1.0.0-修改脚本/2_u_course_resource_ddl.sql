-- 删除外键
alter table u_course_resource drop foreign key FK_HoldResource;
-- 删除字段
alter table u_course_resource drop column course_id;

-- 添加字段
alter table u_course_resource add column file_id bigint(20) NULL DEFAULT NULL COMMENT  '课程文件ID';
-- 添加外键
ALTER TABLE u_course_resource ADD CONSTRAINT `FK_HoldResource` FOREIGN KEY (`file_id`) REFERENCES `u_course_file` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;