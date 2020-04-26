-- 删除外键
alter table u_teaching_task_examination_task drop foreign key FK_HoldExaminationTask;
-- 添加外键
ALTER TABLE u_teaching_task_examination_task ADD CONSTRAINT `FK_HoldExaminationTask` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;