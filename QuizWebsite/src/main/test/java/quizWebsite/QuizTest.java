package quizWebsite;

import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.quizManager.QuestionType;
import com.example.quizwebsite.quizManager.Quiz;
//import com.example.quizwebsite.quizManager.QuizCategory;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest extends TestCase {
    private static BasicDataSource dataSource;
    private static Quiz quiz;

    public void setUp() {
        // Set up the database connection pool
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_quiz_website_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Clear existing data and insert test data if needed
            stmt.executeUpdate("DELETE FROM quizzes");
            stmt.executeUpdate("INSERT INTO quizzes (name, description, category) VALUES ('Test Quiz', 'Test Description', 'MATH')");
        }
        //quiz = new Quiz("Test Quiz", "Test Description", QuizCategory.MATH, true, false, true, false);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM quizzes");
        }
    }

    public void testAddQuestion() {
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE);
        quiz.addQuestion(question);
        assertEquals(1, quiz.getQuestionCount());
    }

    public void testRemoveQuestion() {
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE);
        quiz.addQuestion(question);
        quiz.removeQuestion(question);
        assertEquals(0, quiz.getQuestionCount());
    }

    public void testIsValid() {
        assertFalse(quiz.isValid());  // No questions added yet
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE);
        quiz.addQuestion(question);
        assertTrue(quiz.isValid());
    }

    // Add more tests as needed
}