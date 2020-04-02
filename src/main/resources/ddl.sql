DROP TABLE IF EXISTS `user`;
create table `user`(
	`id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
	`username` VARCHAR(50) COMMENT '用户名',
	`password` VARCHAR(100) COMMENT '密码',
	`sex` INT(1) COMMENT '性别，0：男，1：女',
	`phone` VARCHAR(11) COMMENT '手机号',
	`del` INT(1) DEFAULT 0 COMMENT '是否删除，0：未删除，1：已删除',
	UNIQUE(username, del),
	UNIQUE(phone, del)
)ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '用户表';

DROP TABLE IF EXISTS `role`;
create table role(
	`id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
	`name` VARCHAR(20) COMMENT '角色英文名',
	`desc` VARCHAR(50) COMMENT '描述',
	del INT(1) DEFAULT 0 COMMENT '是否删除'
)ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '角色表';

DROP TABLE IF EXISTS `user_role_ref`;
create table user_role_ref(
	`id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户角色关联ID',
	`role_id` INT COMMENT '角色ID',
	user_id INT COMMENT '用户ID',
	del INT(1) DEFAULT 0 COMMENT '是否删除'
)ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT '用户角色关联表';
