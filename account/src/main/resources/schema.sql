-- Account
CREATE TABLE IF NOT EXISTS `account` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) UNSIGNED NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `bio` VARCHAR(255) NOT NULL DEFAULT "",
  `status` INT(11) UNSIGNED NOT NULL,
  `role` VARCHAR(32) NOT NULL DEFAULT "",
  PRIMARY KEY(`id`),
  CONSTRAINT `account_unique_username` UNIQUE (`username`),
  CONSTRAINT `account_unique_user_id` UNIQUE (`user_id`)
);

CREATE INDEX `account_idx_user_id` ON `account`(`user_id`);
