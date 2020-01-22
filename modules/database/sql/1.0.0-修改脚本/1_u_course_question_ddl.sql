-- 删除外键
alter table u_course_question drop foreign key FK_HoldQuestion;
-- 添加外键
ALTER TABLE u_course_question ADD CONSTRAINT `FK_HoldQuestion` FOREIGN KEY (`course_id`) REFERENCES `u_course` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;