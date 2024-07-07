# usage:
# cd QuizWebsite
# Get-Content .\schema.sql | mysql -u root -p test_quizwebsite_db
# you can check that tables exist with this command in mysql cli  - SHOW TABLES;

CREATE DATABASE IF NOT EXISTS test_quiz_website_db;
USE test_quiz_website_db;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS question_options;
DROP TABLE IF EXISTS relations;
# DROP TABLE IF EXISTS quiz_history;

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     is_admin TINYINT(1) DEFAULT 0,
                                     cookie_key VARCHAR(255) DEFAULT NULL,
                                     UNIQUE KEY cookie_key (cookie_key)
);
CREATE TABLE IF NOT EXISTS quizzes (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(255) NOT NULL,
                                    description TEXT,
                                    category ENUM('MATH', 'SCIENCE', 'HISTORY', 'LITERATURE', 'GENERAL_KNOWLEDGE') NOT NULL,
                                    display_on_single_page BOOLEAN NOT NULL,
                                    display_in_random_order BOOLEAN NOT NULL,
                                    allow_practice_mode BOOLEAN NOT NULL,
                                    correct_immediately BOOLEAN NOT NULL,
                                    username VARCHAR(100) NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS questions (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    quiz_id INT NOT NULL,
                                    question_text TEXT NOT NULL,
                                    question_type ENUM('MULTIPLE_CHOICE', 'SINGLE_ANSWER', 'TRUE_FALSE', 'FILL_IN_THE_BLANK') NOT NULL,
                                    correct_answer TEXT,
                                    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS question_options (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    question_id INT NOT NULL,
                                    option_text TEXT NOT NULL,
                                    is_correct BOOLEAN NOT NULL,
                                    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `relations` (
                                           relation INT AUTO_INCREMENT PRIMARY KEY,
                                           id_1 INT NOT NULL,
                                           id_2 INT NOT NULL,
                                           status VARCHAR(15) NOT NULL
);

