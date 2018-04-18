-- auto-generated definition
create table sso_user
(
	ID int auto_increment primary key,
	USERNAME varchar(64) null,
	PASSWORD varchar(255) null,
	MOBILE varchar(11) null,
	EMAIL varchar(255) null,
	ISEMAIL_STAUS varchar(255) null comment '邮箱是否激活 0 没有  1 有',
	USER_IP varchar(255) null,
	LAST_TIME timestamp default CURRENT_TIMESTAMP not null,
	CREATE_TIME timestamp default '0000-00-00 00:00:00' not null,
	CREATE_MAN varchar(64) null,
	UPDATE_TIME timestamp default '0000-00-00 00:00:00' not null,
	UPDATE_MAN varchar(64) null,
	STATUS char default '0' null,
	USER_PORTAL varchar(64) null
)
;


-- auto-generated definition
create table sso_ticket
(
	ID int auto_increment
		primary key,
	TICKET varchar(255) null comment '票据内容',
	CREATE_TIME timestamp default CURRENT_TIMESTAMP not null,
	VALIDATE_TIME int(4) null comment '有效时间，分钟为单位，',
	TICKET_TYPE char null comment '票据类型 0邮箱 1手机号',
	USER_ID int null comment '用户表ID'
)
;

