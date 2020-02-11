-- 删除外键
alter table u_examination_result drop foreign key FK_CorrespondingResult;
alter table u_examination_result drop foreign key FK_MappingResult;
-- 添加外键
ALTER TABLE u_examination_result ADD CONSTRAINT `FK_CorrespondingResult` FOREIGN KEY (`examining_id`) REFERENCES `u_student_examination_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_examination_result ADD CONSTRAINT `FK_MappingResult` FOREIGN KEY (`question_id`) REFERENCES `u_course_question` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;