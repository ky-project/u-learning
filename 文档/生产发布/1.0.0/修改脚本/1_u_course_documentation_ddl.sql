-- 删除外键
alter table u_course_documentation drop foreign key FK_HoldDocumentation;
-- 添加外键
ALTER TABLE u_course_documentation ADD CONSTRAINT `FK_HoldDocumentation` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;