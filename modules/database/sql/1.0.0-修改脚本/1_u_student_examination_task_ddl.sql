-- 删除外键
alter table u_student_examination_task drop foreign key FK_ExaminationTaskBeingExamined;
alter table u_student_examination_task drop foreign key FK_StudentExamining;
-- 添加外键
ALTER TABLE u_student_examination_task ADD CONSTRAINT `FK_ExaminationTaskBeingExamined` FOREIGN KEY (`examination_task_id`) REFERENCES `u_teaching_task_examination_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_student_examination_task ADD CONSTRAINT `FK_StudentExamining` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;