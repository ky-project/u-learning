ALTER TABLE u_teaching_task_notice ADD COLUMN `notice_attachment_name` varchar(255) COMMENT '附件名';

ALTER TABLE u_teaching_task_notice MODIFY `notice_shared` tinyint(1) COMMENT '是否共享';