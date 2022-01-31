-- Auth client
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE IF NOT EXISTS `auth_client` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `client` VARCHAR(64) NOT NULL,
  `secret` VARCHAR(64) NOT NULL,
  `grant_types` VARCHAR(64) NOT NULL DEFAULT "",
  `access_token_duration` INT(11) UNSIGNED NOT NULL,
  `refresh_token_duration` INT(11) UNSIGNED NOT NULL,
  `is_enabled` BOOLEAN NOT NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT `auth_client_unique_client` UNIQUE (`client`)
);

CREATE INDEX `auth_client_idx_client` ON `auth_client`(`client`);


-- User
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `roles` VARCHAR(64) NOT NULL DEFAULT "",
  `is_enabled` BOOLEAN NOT NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT `user_unique_username` UNIQUE (`username`)
);

CREATE INDEX `username_idx_username` ON `user`(`username`);

-- Auth client
INSERT INTO `auth_client` (`client`, `secret`, `grant_types`, `access_token_duration`, `refresh_token_duration`, `is_enabled`) VALUE('gateway', '$2a$10$jm8YwIrIJgN1jM/aK2lEGubs7ZeEB9tUA7M9DLacK7kJYIKrqsmL6', 'password refresh_token', 900, 2592000, true);

-- User
INSERT INTO `user` (`username`, `password`, `roles`, `is_enabled`) VALUE('admin', '$2a$10$SBes0ccOP73lP.4ryxiQOu5pShE002lp6iozsK6NYLqaP3v95FLpG', 'ROLE_ADMIN', true);
