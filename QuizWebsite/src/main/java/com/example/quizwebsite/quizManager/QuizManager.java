package com.example.quizwebsite.quizManager;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuizManager {
    private BasicDataSource dataSource;

    public QuizManager(BasicDataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource cannot be null");
        }
        this.dataSource = dataSource;
    }

    public int saveQuizToDatabase(Quiz quiz, String username) throws SQLException {
        String sql = "INSERT INTO quizzes (name, description, category, display_on_single_page, display_in_random_order, allow_practice_mode, correct_immediately, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, quiz.getName());
            stmt.setString(2, quiz.getDescription());
            stmt.setString(3, quiz.getCategory().name());
            stmt.setBoolean(4, quiz.isDisplayOnSinglePage());
            stmt.setBoolean(5, quiz.isDisplayInRandomOrder());
            stmt.setBoolean(6, quiz.isAllowPracticeMode());
            stmt.setBoolean(7, quiz.isCorrectImmediately());
            stmt.setString(8, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            throw new SQLException("Creating quiz failed, no ID obtained.");
        }
    }

    public boolean saveQuestion(Question question) throws SQLException {
        switch (question.getType()) {
            case MULTIPLE_CHOICE:
                return saveMultipleChoiceQuestion(question);
            case TRUE_FALSE:
                return saveTrueFalseQuestion(question);
            case SINGLE_ANSWER:
                return saveSingleAnswerQuestion(question);
            default:
                throw new IllegalArgumentException("Unsupported question type: " + question.getType());
        }
    }

    private boolean saveMultipleChoiceQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_text, question_type, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, question.getQuizId());
            stmt.setString(2, question.getText());
            stmt.setString(3, question.getType().name());
            stmt.setString(4, question.getCorrectAnswer());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                int questionId;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        questionId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating question failed, no ID obtained.");
                    }
                }
                return saveQuestionOptions(conn, questionId, question.getOptions(), question.getCorrectOptions());
            }
            return false;
        }
    }

    private boolean saveQuestionOptions(Connection conn, int questionId, List<String> options, List<Boolean> correctOptions) throws SQLException {
        String sql = "INSERT INTO question_options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < options.size(); i++) {
                stmt.setInt(1, questionId);
                stmt.setString(2, options.get(i));
                stmt.setBoolean(3, correctOptions.get(i));
                stmt.addBatch();
            }
            int[] rowsAffected = stmt.executeBatch();
            return Arrays.stream(rowsAffected).allMatch(r -> r > 0);
        }
    }

    private boolean saveTrueFalseQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_text, question_type, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, question.getQuizId());
            stmt.setString(2, question.getText());
            stmt.setString(3, question.getType().name());
            stmt.setString(4, question.getCorrectAnswer());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                int questionId;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        questionId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating question failed, no ID obtained.");
                    }
                }
                return saveQuestionOptions(conn, questionId, Arrays.asList("True", "False"),
                        Arrays.asList(question.getCorrectAnswer().equalsIgnoreCase("true"),
                                question.getCorrectAnswer().equalsIgnoreCase("false")));
            }
            return false;
        }
    }

    private boolean saveSingleAnswerQuestion(Question question) throws SQLException {
        String sql = "INSERT INTO questions (quiz_id, question_text, question_type, correct_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, question.getQuizId());
            stmt.setString(2, question.getText());
            stmt.setString(3, question.getType().name());
            stmt.setString(4, question.getCorrectAnswer());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


    public Quiz getQuizById(int quizId) throws SQLException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Quiz(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            QuizCategory.valueOf(rs.getString("category")),
                            rs.getBoolean("display_on_single_page"),
                            rs.getBoolean("display_in_random_order"),
                            rs.getBoolean("allow_practice_mode"),
                            rs.getBoolean("correct_immediately")
                    );
                }
            }
        }
        return null;
    }

    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("quiz_id"),
                            rs.getString("question_text"),
                            QuestionType.valueOf(rs.getString("question_type"))
                    );
                    question.setId(rs.getInt("id"));
                    question.setCorrectAnswer(rs.getString("correct_answer"));
                    loadQuestionOptions(conn, question);
                    questions.add(question);
                }
            }
        }
        return questions;
    }

<<<<<<< Updated upstream
        // Existing code...

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Quiz quiz = new Quiz(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getBoolean("display_on_single_page"),
                        rs.getBoolean("display_in_random_order"),
                        rs.getBoolean("allow_practice_mode"),
                        rs.getBoolean("correct_immediately"),
                        rs.getString("username"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
=======
    private void loadQuestionOptions(Connection conn, Question question) throws SQLException {
        String sql = "SELECT * FROM question_options WHERE question_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, question.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    question.addOption(rs.getString("option_text"), rs.getBoolean("is_correct"));
                }
            }
        }
        // For multiple choice questions, update the correct answer if it's not set
        if (question.getType() == QuestionType.MULTIPLE_CHOICE && (question.getCorrectAnswer() == null || question.getCorrectAnswer().isEmpty())) {
            List<String> correctOptions = question.getOptions().stream()
                    .filter(option -> question.getCorrectOptions().get(question.getOptions().indexOf(option)))
                    .collect(Collectors.toList());
            question.setCorrectAnswer(String.join(", ", correctOptions));
        }
>>>>>>> Stashed changes
    }

}
