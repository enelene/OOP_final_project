package quizWebsite;
import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.quizManager.QuestionType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    private static BasicDataSource dataSource;
    private static Question question;

    @BeforeAll
    public static void setup() {
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
            stmt.executeUpdate("DELETE FROM questions");
            stmt.executeUpdate("INSERT INTO questions (quiz_id, text, type) VALUES (1, 'Test question?', 'MULTIPLE_CHOICE')");
        }
        question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM questions");
        }
    }

    @Test
    public void testAddOption() {
        question.addOption("Option 1", true);
        question.addOption("Option 2", false);
        assertEquals(2, question.getOptionCount());
        assertTrue(question.hasCorrectOption());
    }

    @Test
    public void testClearOptions() {
        question.addOption("Option 1", true);
        question.clearOptions();
        assertEquals(0, question.getOptionCount());
        assertFalse(question.hasCorrectOption());
    }

    @Test
    public void testIsValid() {
        question.addOption("Option 1", true);
        question.addOption("Option 2", false);
        assertTrue(question.isValid());

        Question invalidQuestion = new Question(1, "Invalid?", QuestionType.MULTIPLE_CHOICE);
        assertFalse(invalidQuestion.isValid());
    }

    // Add more tests as needed
}