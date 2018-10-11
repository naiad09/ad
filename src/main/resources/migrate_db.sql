ALTER TABLE `forum`
	ADD COLUMN `login` VARCHAR(32) NULL DEFAULT '0' AFTER `isClient`,
	ADD COLUMN `password` VARCHAR(32) NULL DEFAULT '0' AFTER `login`;

update forum 
set login = (select login from account where forum = id),
password = (select password from account where forum = id)

drop table account