-- Auth client
INSERT INTO `auth_client` (`client`, `secret`, `grant_types`, `access_token_duration`, `refresh_token_duration`, `is_enabled`) VALUE('gateway', '$2a$10$jm8YwIrIJgN1jM/aK2lEGubs7ZeEB9tUA7M9DLacK7kJYIKrqsmL6', 'password refresh_token', 900, 2592000, true);

-- User
INSERT INTO `user` (`username`, `password`, `roles`, `is_enabled`) VALUE('admin', '$2a$10$SBes0ccOP73lP.4ryxiQOu5pShE002lp6iozsK6NYLqaP3v95FLpG', 'ROLE_ADMIN', true);
