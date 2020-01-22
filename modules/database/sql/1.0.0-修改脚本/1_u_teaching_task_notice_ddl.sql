-- 删除外键
alter table u_teaching_task_notice drop foreign key FK_ReleaseNotice;
-- 添加外键
ALTER TABLE u_teaching_task_notice ADD CONSTRAINT `FK_ReleaseNotice` FOREIGN KEY (`teaching_task_id`) REFERENCES `u_teaching_task` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;