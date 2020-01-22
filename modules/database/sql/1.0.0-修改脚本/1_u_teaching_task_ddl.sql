-- 删除外键
alter table u_teaching_task drop foreign key FK_HoldTask;
alter table u_teaching_task drop foreign key FK_UndertakeTask;
-- 添加外键
ALTER TABLE u_teaching_task ADD CONSTRAINT `FK_HoldTask` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_teaching_task ADD CONSTRAINT `FK_UndertakeTask` FOREIGN KEY (`tea_id`) REFERENCES `u_teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;