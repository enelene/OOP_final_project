USE quizwebsite;

CREATE TABLE IF NOT EXISTS 'users' (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       is_admin TINYINT(1) DEFAULT 0,
                       cookie_key varchar(255) DEFAULT NULL,
                       UNIQUE KEY `cookie_key` (`cookie_key`)
);

CREATE TABLE quizzes (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         category VARCHAR(100),
                         display_on_single_page BOOLEAN,
                         display_in_random_order BOOLEAN,
                         allow_practice_mode BOOLEAN,
                         correct_immediately BOOLEAN,
                         username VARCHAR(100)
);

CREATE TABLE questions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           quiz_id INT,
                           question_text TEXT,
                           question_type VARCHAR(20),
                           options TEXT,
                           correct_options TEXT,
                           FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);