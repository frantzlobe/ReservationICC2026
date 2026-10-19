CREATE TABLE `personal_access_tokens` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `token` VARCHAR(36) NOT NULL,
    `user_id` INT(11) NOT NULL,
    `expires_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_personal_access_tokens_token` (`token`),
    KEY `idx_personal_access_tokens_user_id` (`user_id`),
    CONSTRAINT `fk_personal_access_tokens_user`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;