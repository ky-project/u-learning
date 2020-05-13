-- 删除外键
alter table u_student_teaching_task drop foreign key FK_StudentBeStudying;
alter table u_student_teaching_task drop foreign key FK_TeachingTaskBeingStudied;
-- 添加外键
ALTER TABLE u_student_teaching_task ADD CONSTRAINT `FK_StudentBeStudying` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_student_teaching_task ADD CONSTRAINT `FK_TeachingTaskBeingStudied` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;