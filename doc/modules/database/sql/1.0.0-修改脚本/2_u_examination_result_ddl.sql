ALTER TABLE u_experiment_result ADD COLUMN `experiment_attachment_name` varchar(255) COMMENT '附件名';

ALTER TABLE u_experiment_result ADD COLUMN `experiment_shared` tinyint(1) NULL COMMENT '是否共享分享展示 0：否 1：是';
