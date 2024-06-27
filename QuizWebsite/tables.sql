USE quizwebsite;

CREATE TABLE IF NOT EXISTS 'users' (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       is_admin TINYINT(1) DEFAULT 0,
                       cookie_key varchar(255) DEFAULT NULL,
                       UNIQUE KEY `cookie_key` (`cookie_key`)
);