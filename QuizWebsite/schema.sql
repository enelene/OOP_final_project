# usage:
# cd QuizWebsite
# Get-Content .\schema.sql | mysql -u root -p quizwebsite
# you can check that tables exist with this command in mysql cli  - SHOW TABLES;
USE quizwebsite;

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     is_admin TINYINT(1) DEFAULT 0,
                                     cookie_key VARCHAR(255) DEFAULT NULL,
                                     UNIQUE KEY cookie_key (cookie_key)
);

# INSERT INTO users (users.username, users.password, users.is_admin, users.cookie_key) VALUES
# ("Elene1", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220" , 0 , NULL );





CREATE TABLE IF NOT EXISTS quizzes (
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

CREATE TABLE IF NOT EXISTS questions (
                                         id INT PRIMARY KEY AUTO_INCREMENT,
                                         quiz_id INT,
                                         question_text TEXT,
                                         question_type VARCHAR(20),
                                         options TEXT,
                                         correct_options TEXT,
                                         FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);
=======
CREATE TABLE IF NOT EXISTS `users` (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       is_admin TINYINT(1) DEFAULT 0,
                       cookie_key varchar(255) DEFAULT NULL,
                       UNIQUE KEY `cookie_key` (`cookie_key`)
);

CREATE TABLE IF NOT EXISTS quizzes (
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

CREATE TABLE IF NOT EXISTS questions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           quiz_id INT,
                           question_text TEXT,
                           question_type VARCHAR(20),
                           options TEXT,
                           correct_options TEXT,
                           FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);


CREATE TABLE IF NOT EXISTS `relations` (
                                           relation INT AUTO_INCREMENT PRIMARY KEY,
                                           id_1 INT NOT NULL,
                                           id_2 INT NOT NULL,
                                           status VARCHAR(15) NOT NULL
);

