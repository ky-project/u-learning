-- 删除外键
alter table u_experiment_result drop foreign key FK_ExperimentBeingDone;
alter table u_experiment_result drop foreign key FK_StudentDoingExperiment;
-- 添加外键
ALTER TABLE u_experiment_result ADD CONSTRAINT `FK_ExperimentBeingDone` FOREIGN KEY (`experiment_id`) REFERENCES `u_teaching_task_experiment` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_experiment_result ADD CONSTRAINT `FK_StudentDoingExperiment` FOREIGN KEY (`stu_id`) REFERENCES `u_student` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;