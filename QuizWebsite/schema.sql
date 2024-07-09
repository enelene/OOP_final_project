# usage:
# cd QuizWebsite
# Get-Content .\schema.sql | mysql -u root -p quizwebsite
# you can check that tables exist with this command in mysql cli  - SHOW TABLES;
CREATE DATABASE IF NOT EXISTS quizwebsite;
USE quizwebsite;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS question_options;
DROP TABLE IF EXISTS relations;

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     is_admin TINYINT(1) DEFAULT 0,
                                     cookie_key VARCHAR(255) DEFAULT NULL,
                                     UNIQUE KEY cookie_key (cookie_key)
);

INSERT INTO users (id, username, password, is_admin, cookie_key) VALUES
                                                                     (4,'Elene1', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4' , 0 , NULL ), # password is 1234
                                                                     (5,'AnaJoba', '690ed3ee68986dc00db60c2cc240530186b385a54bf98274d58c72022cdae1e8' , 0 , NULL ), # password is oop
                                                                     (6,'AnaR','9261d118ef45cf60c2d41bbab24c1455099cb85fb912fafc844178bf3b907a7d', 0 , NULL ) #passowrd is coolPass
;

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

INSERT INTO quizzes (id, name, description, category, display_on_single_page, display_in_random_order,
                     allow_practice_mode, correct_immediately, username, created_at) VALUES
                                                                                         (1, 'Quiz1', 'first quiz ever to be created', 'MATH', 1, 0, 0, 0, 'AnaJoba', '2024-07-09 02:04:48'),
                                                                                         (2, 'Seahorses', 'Seahorses might be the strangest swimmers you’ve ever seen.', 'SCIENCE', 0, 1, 0, 0, 'Elene', '2024-07-09 02:12:35'),
                                                                                         (3, 'ფოცხვერი', 'ქართული ქვიზი ფოცხვერზე.', 'SCIENCE', 0, 0, 0, 1, 'Elene', '2024-07-09 02:16:17');


CREATE TABLE IF NOT EXISTS questions (
                                         id INT PRIMARY KEY AUTO_INCREMENT,
                                         quiz_id INT NOT NULL,
                                         question_text TEXT NOT NULL,
                                         question_type ENUM('MULTIPLE_CHOICE', 'SINGLE_ANSWER', 'TRUE_FALSE', 'FILL_IN_THE_BLANK', 'PICTURE_RESPONSE') NOT NULL,
                                         correct_answer TEXT,
                                         image_url TEXT,
                                         FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);
INSERT INTO questions (id, quiz_id, question_text, question_type, correct_answer) VALUES
                                                                                      (1, 1, '2+2=4', 'TRUE_FALSE', 'true'),
                                                                                      (2, 2, 'About how many species of seahorses live in the ocean?', 'MULTIPLE_CHOICE', '50'),
                                                                                      (3, 2, 'True or false: Seahorses do not have fins.', 'TRUE_FALSE', 'false'),
                                                                                      (4, 2, 'What do seahorses use to hold on to plants?', 'SINGLE_ANSWER', 'tails'),
                                                                                      (5, 2, 'How do seahorses camouflage themselves?', 'MULTIPLE_CHOICE', 'They can change color.'),
                                                                                      (6, 3, 'მართალია თუ მცდარი: იბერიული ფოცხვერი ფოცხვერის სამი სახეობიდან ერთ-ერთია.', 'TRUE_FALSE', 'false');

CREATE TABLE IF NOT EXISTS question_options (
                                                id INT PRIMARY KEY AUTO_INCREMENT,
                                                question_id INT NOT NULL,
                                                option_text TEXT NOT NULL,
                                                is_correct BOOLEAN NOT NULL,
                                                FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);


INSERT INTO question_options (id, question_id, option_text, is_correct) VALUES
                                                                            (1, 1, 'True', 1),
                                                                            (2, 1, 'False', 0),
                                                                            (3, 2, '15', 0),
                                                                            (4, 2, '30', 0),
                                                                            (5, 2, '50', 1),
                                                                            (6, 3, 'True', 0),
                                                                            (7, 3, 'False', 1),
                                                                            (8, 5, 'They tuck themselves into sea caves.', 0),
                                                                            (9, 5, 'They hide behind large schools of other fish.', 0),
                                                                            (10, 5, 'They burrow in sand on the seafloor.', 0),
                                                                            (11, 5, 'They can change color.', 1),
                                                                            (12, 6, 'True', 0),
                                                                            (13, 6, 'False', 1);


CREATE TABLE IF NOT EXISTS relations (
                                         relation INT AUTO_INCREMENT PRIMARY KEY,
                                         id_1 INT NOT NULL,
                                         id_2 INT NOT NULL,
                                         status VARCHAR(15) NOT NULL
);
INSERT INTO relations (id_1, id_2, status) VALUES
                                               (4, 5, 'request'),
                                               (5, 6, 'request'),
                                               (4, 6, 'friends');

CREATE TABLE  IF NOT EXISTS notes (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      sender_username VARCHAR(255) NOT NULL,
                                      recipient_username VARCHAR(255) NOT NULL,
                                      message TEXT NOT NULL,
                                      timestamp DATETIME NOT NULL,
                                      is_read BOOLEAN NOT NULL DEFAULT FALSE,
                                      FOREIGN KEY (sender_username) REFERENCES users(username),
                                      FOREIGN KEY (recipient_username) REFERENCES users(username)
);

CREATE INDEX idx_sender ON notes (sender_username);
CREATE INDEX idx_recipient ON notes (recipient_username);
CREATE INDEX idx_timestamp ON notes (timestamp);

# INSERT INTO notes (sender_username, recipient_username, message, timestamp, is_read) VALUES ('elene', 'ana', 'This is the first test note from Elene to Ana.', NOW() - INTERVAL 2 DAY, TRUE), ('ana', 'elene', 'Hi Elene, thanks for your note! This is a reply.', NOW() - INTERVAL 1 DAY, FALSE), ('elene', 'ana', 'Another test note to check multiple note display.', NOW(), FALSE);
INSERT INTO notes (id, sender_username, recipient_username, message, timestamp, is_read) VALUES
    (1, 'AnaR', 'Elene', 'Hello Elene, thanks for sending friend request', '2024-07-09 02:36:52', 0);

CREATE TABLE IF NOT EXISTS attempts (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        quiz_id INT NOT NULL,
                                        user_id INT NOT NULL,
                                        score INT NOT NULL,
                                        time DATETIME NOT NULL,
                                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                        FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);