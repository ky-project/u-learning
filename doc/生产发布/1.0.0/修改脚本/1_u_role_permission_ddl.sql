-- 删除外键
alter table u_role_permission drop foreign key FK_TeacherRole;
alter table u_role_permission drop foreign key FK_RolePermission;
-- 添加外键
ALTER TABLE u_role_permission ADD CONSTRAINT `FK_TeacherRole` FOREIGN KEY (`role_id`) REFERENCES `u_role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE u_role_permission ADD CONSTRAINT `FK_RolePermission` FOREIGN KEY (`permission_id`) REFERENCES `u_permission` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;