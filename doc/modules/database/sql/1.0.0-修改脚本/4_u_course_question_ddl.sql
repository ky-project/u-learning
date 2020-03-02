ALTER TABLE u_course_question ADD COLUMN `question_difficulty` smallint DEFAULT 0 COMMENT '试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难';
