-- 删除外键
alter table u_course_resource drop foreign key FK_HoldResource;
-- 添加外键
ALTER TABLE u_course_resource ADD CONSTRAINT `FK_HoldResource` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;