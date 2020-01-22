-- 删除外键
alter table u_teacher_role drop foreign key FK_HasRole;
alter table u_teacher_role drop foreign key FK_ThisTeacher;
-- 添加外键
ALTER TABLE u_teacher_role ADD CONSTRAINT `FK_HasRole` FOREIGN KEY (`role_id`) REFERENCES `u_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_teacher_role ADD CONSTRAINT `FK_ThisTeacher` FOREIGN KEY (`tea_id`) REFERENCES `u_teacher` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;