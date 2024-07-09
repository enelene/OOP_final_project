package com.example.quizwebsite.quizManager;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages quiz-related database operations.
 */
public class QuizManager {
    private static final Logger LOGGER = Logger.getLogger(QuizManager.class.getName());
    private static final String INSERT_QUIZ_SQL = "INSERT INTO quizzes (name, description, category, display_on_single_page, display_in_random_order, allow_practice_mode, correct_immediately, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_QUESTION_SQL = "INSERT INTO questions (quiz_id, question_text, question_type, correct_answer) VALUES (?, ?, ?, ?)";
    private static final String INSERT_OPTION_SQL = "INSERT INTO question_options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
    private static final String INSERT_ATTEMPT_SQL = "INSERT INTO attempts (quiz_id, user_id, score, time) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUIZ_SQL = "SELECT * FROM quizzes WHERE id = ?";
    private static final String SELECT_QUESTIONS_SQL = "SELECT * FROM questions WHERE quiz_id = ?";
    private static final String SELECT_OPTIONS_SQL = "SELECT * FROM question_options WHERE question_id = ?";
    private static final String SELECT_AVERAGE_SCORE_SQL = "SELECT AVG(score) AS average_score FROM attempts WHERE quiz_id = ?;";
    private static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM attempts WHERE quiz_id = ?;";

    private final BasicDataSource dataSource;

    /**
     * Constructs a QuizManager with the given data source.
     * @param dataSource the data source to use for database connections
     * @throws IllegalArgumentException if the data source is null
     */
    public QuizManager(BasicDataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("DataSource cannot be null");
        }
        this.dataSource = dataSource;
    }

    /**
     * Saves a quiz to the database.
     * @param quiz the quiz to save
     * @param username the username of the quiz creator
     * @return the ID of the saved quiz
     * @throws SQLException if a database error occurs
     */
    public int saveQuizToDatabase(Quiz quiz, String username) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUIZ_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setQuizParameters(stmt, quiz, username);
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

    /**
     * Sets the parameters for a quiz PreparedStatement.
     * @param stmt the PreparedStatement to set parameters for
     * @param quiz the Quiz object containing the parameter values
     * @param username the username of the quiz creator
     * @throws SQLException if a database error occurs
     */
    private void setQuizParameters(PreparedStatement stmt, Quiz quiz, String username) throws SQLException {
        stmt.setString(1, quiz.getName());
        stmt.setString(2, quiz.getDescription());
        stmt.setString(3, quiz.getCategory().name());
        stmt.setBoolean(4, quiz.isDisplayOnSinglePage());
        stmt.setBoolean(5, quiz.isDisplayInRandomOrder());
        stmt.setBoolean(6, quiz.isAllowPracticeMode());
        stmt.setBoolean(7, quiz.isCorrectImmediately());
        stmt.setString(8, username);
    }

    /**
     * Saves a question to the database.
     * @param question the question to save
     * @return true if the question was saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
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

    /**
     * Saves a multiple-choice question to the database.
     * @param question the multiple-choice question to save
     * @return true if the question was saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean saveMultipleChoiceQuestion(Question question) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUESTION_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setQuestionParameters(stmt, question);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                int questionId = getGeneratedId(stmt);
                return saveQuestionOptions(conn, questionId, question.getOptions(), question.getCorrectOptions());
            }
            return false;
        }
    }

    /**
     * Saves options for a question to the database.
     * @param conn the database connection
     * @param questionId the ID of the question
     * @param options the list of options
     * @param correctOptions the list indicating which options are correct
     * @return true if all options were saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean saveQuestionOptions(Connection conn, int questionId, List<String> options, List<Boolean> correctOptions) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_OPTION_SQL)) {
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

    /**
     * Saves a true/false question to the database.
     * @param question the true/false question to save
     * @return true if the question was saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean saveTrueFalseQuestion(Question question) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUESTION_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setQuestionParameters(stmt, question);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                int questionId = getGeneratedId(stmt);
                return saveQuestionOptions(conn, questionId, Arrays.asList("True", "False"),
                        Arrays.asList(question.getCorrectAnswer().equalsIgnoreCase("true"),
                                question.getCorrectAnswer().equalsIgnoreCase("false")));
            }
            return false;
        }
    }

    /**
     * Saves a single answer question to the database.
     * @param question the single answer question to save
     * @return true if the question was saved successfully, false otherwise
     * @throws SQLException if a database error occurs
     */
    private boolean saveSingleAnswerQuestion(Question question) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_QUESTION_SQL)) {
            setQuestionParameters(stmt, question);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Sets the parameters for a question PreparedStatement.
     * @param stmt the PreparedStatement to set parameters for
     * @param question the Question object containing the parameter values
     * @throws SQLException if a database error occurs
     */
    private void setQuestionParameters(PreparedStatement stmt, Question question) throws SQLException {
        stmt.setInt(1, question.getQuizId());
        stmt.setString(2, question.getText());
        stmt.setString(3, question.getType().name());
        stmt.setString(4, question.getCorrectAnswer());
    }

    /**
     * Retrieves the generated ID from a PreparedStatement after execution.
     * @param stmt the PreparedStatement that was executed
     * @return the generated ID
     * @throws SQLException if a database error occurs or no ID was generated
     */
    private int getGeneratedId(PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating question failed, no ID obtained.");
            }
        }
    }

    /**
     * Retrieves a quiz by its ID.
     * @param quizId the ID of the quiz to retrieve
     * @return the Quiz object, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Quiz getQuizById(int quizId) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUIZ_SQL)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createQuizFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Creates a Quiz object from a ResultSet.
     * @param rs the ResultSet containing quiz data
     * @return a new Quiz object
     * @throws SQLException if a database error occurs
     */
    private Quiz createQuizFromResultSet(ResultSet rs) throws SQLException {
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

    /**
     * Retrieves all questions for a given quiz ID.
     * @param quizId the ID of the quiz
     * @return a List of Question objects
     * @throws SQLException if a database error occurs
     */
    public List<Question> getQuestionsByQuizId(int quizId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_QUESTIONS_SQL)) {
            stmt.setInt(1, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = createQuestionFromResultSet(rs);
                    loadQuestionOptions(conn, question);
                    questions.add(question);
                }
            }
        }
        return questions;
    }

    /**
     * Creates a Question object from a ResultSet.
     * @param rs the ResultSet containing question data
     * @return a new Question object
     * @throws SQLException if a database error occurs
     */
    private Question createQuestionFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question(
                rs.getInt("quiz_id"),
                rs.getString("question_text"),
                QuestionType.valueOf(rs.getString("question_type"))
        );
        question.setId(rs.getInt("id"));
        question.setCorrectAnswer(rs.getString("correct_answer"));
        return question;
    }

    /**
     * Loads options for a question from the database.
     * @param conn the database connection
     * @param question the Question object to load options for
     * @throws SQLException if a database error occurs
     */
    private void loadQuestionOptions(Connection conn, Question question) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_OPTIONS_SQL)) {
            stmt.setInt(1, question.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    question.addOption(rs.getString("option_text"), rs.getBoolean("is_correct"));
                }
            }
        }
        updateMultipleChoiceCorrectAnswer(question);
    }

    /**
     * Updates the correct answer for a multiple-choice question if it's not set.
     * @param question the Question object to update
     */
    private void updateMultipleChoiceCorrectAnswer(Question question) {
        if (question.getType() == QuestionType.MULTIPLE_CHOICE && (question.getCorrectAnswer() == null || question.getCorrectAnswer().isEmpty())) {
            List<String> correctOptions = question.getOptions().stream()
                    .filter(option -> question.getCorrectOptions().get(question.getOptions().indexOf(option)))
                    .collect(Collectors.toList());
            question.setCorrectAnswer(String.join(", ", correctOptions));
        }
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                QuizCategory category = QuizCategory.valueOf(rs.getString("category"));
                Quiz quiz = new Quiz( rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        category,
                        rs.getBoolean("display_on_single_page"),
                        rs.getBoolean("display_in_random_order"),
                        rs.getBoolean("allow_practice_mode"),
                        rs.getBoolean("correct_immediately")
                        //rs.getString("username")
                );
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }


    public List<Attempt> getAttemptsByQuizId(int quizId, boolean sortByScore, boolean sortByTime, boolean ascending, int intervalInHours, int userId) {
        List<Attempt> attempts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM attempts WHERE quiz_id = ?");
            if (userId > -1) {
                queryBuilder.append(" AND user_id = ?");
            }
            if (intervalInHours > 0) {
                queryBuilder.append(" AND time >= NOW() - INTERVAL ").append(intervalInHours).append(" HOUR");
            }
            if (sortByScore) {
                queryBuilder.append(" ORDER BY score ").append(ascending ? "ASC;" : "DESC;");
            } else if (sortByTime) {
                queryBuilder.append(" ORDER BY time ").append(ascending ? "ASC;" : "DESC;");
            }
            stmt = conn.prepareStatement(queryBuilder.toString());
            stmt.setInt(1, quizId);
            if (userId > -1) {
                stmt.setInt(2, userId);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attempt attempt = createAttemptFromResultSet(rs);
                attempts.add(attempt);
            }
            return attempts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }

    public int getAverageScoreOfQuiz(int quizId){
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SELECT_AVERAGE_SCORE_SQL);
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return 0;
    }

    public int getAttemptCountOfQuiz(int quizId){
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SELECT_COUNT_SQL);
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
        return 0;
    }

    public void addAttempt(int quizId, int userId, int score, LocalDateTime time) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(INSERT_ATTEMPT_SQL);
            pstmt.setInt(1, quizId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, score);
            pstmt.setTimestamp(4, Timestamp.valueOf(time));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            }
            catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }
    }


    private Attempt createAttemptFromResultSet(ResultSet rs) throws SQLException {
        return new Attempt(rs.getInt(3), rs.getInt(2), rs.getInt(4), rs.getTimestamp(5).toLocalDateTime());
    }
}