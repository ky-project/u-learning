-- 删除外键
alter table u_teaching_task_experiment drop foreign key FK_HoldExperiment;
-- 添加外键
ALTER TABLE u_teaching_task_experiment ADD CONSTRAINT `FK_HoldExperiment` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;