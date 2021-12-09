-- Auth client
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
