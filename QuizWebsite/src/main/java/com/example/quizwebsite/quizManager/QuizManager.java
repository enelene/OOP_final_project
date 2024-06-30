package com.example.quizwebsite.quizManager;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizManager {
    private BasicDataSource dataSource;

    public QuizManager(BasicDataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource cannot be null");
        }
        this.dataSource = dataSource;
    }

    public boolean saveQuizToDatabase(com.example.quizwebsite.quizManager.Quiz quiz, String username) {
        String sql = "INSERT INTO quizzes (name, description, category, display_on_single_page, display_in_random_order, allow_practice_mode, correct_immediately, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, quiz.getName());
            stmt.setString(2, quiz.getDescription());
            stmt.setString(3, quiz.getCategory());
            stmt.setBoolean(4, quiz.isDisplayOnSinglePage());
            stmt.setBoolean(5, quiz.isDisplayInRandomOrder());
            stmt.setBoolean(6, quiz.isAllowPracticeMode());
            stmt.setBoolean(7, quiz.isCorrectImmediately());
            stmt.setString(8, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQuizIdByName(String quizName) {
        String query = "SELECT id FROM quizzes WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, quizName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if quiz not found or an error occurs
    }

    public boolean saveQuestion(Question question) {
        String sql = "INSERT INTO questions (quiz_id, question_text, question_type, options, correct_options) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, question.getQuizId());
            stmt.setString(2, question.getText());
            stmt.setString(3, question.getType());

            if ("multiple_choice".equals(question.getType())) {
                stmt.setString(4, String.join("|", question.getOptions()));
                List<String> correctOptions = new ArrayList<>();
                for (int i = 0; i < question.getCorrectOptions().size(); i++) {
                    if (question.getCorrectOptions().get(i)) {
                        correctOptions.add(String.valueOf(i));
                    }
                }
                stmt.setString(5, String.join("|", correctOptions));
            } else if ("true_false".equals(question.getType())) {
                stmt.setString(4, "True|False");
                stmt.setString(5, question.getCorrectAnswer());
            } else if ("short_answer".equals(question.getType())) {
                stmt.setString(4, null);
                stmt.setString(5, question.getCorrectAnswer());
            } else {
                throw new IllegalArgumentException("Unsupported question type: " + question.getType());
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Quiz getQuizById(int quizId) {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Quiz(
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getBoolean("display_on_single_page"),
                            rs.getBoolean("display_in_random_order"),
                            rs.getBoolean("allow_practice_mode"),
                            rs.getBoolean("correct_immediately")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getInt("id"));
                    question.setQuizId(rs.getInt("quiz_id"));
                    question.setText(rs.getString("question_text"));
                    question.setType(rs.getString("question_type"));

                    if ("multiple_choice".equals(question.getType())) {
                        String[] options = rs.getString("options").split("\\|");
                        question.setOptions(new ArrayList<>(Arrays.asList(options)));

                        String[] correctOptionsStr = rs.getString("correct_options").split("\\|");
                        List<Boolean> correctOptions = new ArrayList<>();
                        for (int i = 0; i < options.length; i++) {
                            correctOptions.add(false);
                        }
                        for (String index : correctOptionsStr) {
                            correctOptions.set(Integer.parseInt(index), true);
                        }
                        question.setCorrectOptions(correctOptions);
                    } else {
                        question.setCorrectAnswer(rs.getString("correct_options"));
                    }

                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
