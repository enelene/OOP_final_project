package quizWebsite;

import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.quizManager.QuestionType;
import com.example.quizwebsite.quizManager.Quiz;
import com.example.quizwebsite.quizManager.QuizCategory;
import org.junit.jupiter.api.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {
    private static BasicDataSource dataSource;
    private static Quiz quiz;

    @BeforeAll
    public static void setUp() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_quiz_website_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM quizzes");
            stmt.executeUpdate("INSERT INTO quizzes (name, description, category, display_on_single_page, display_in_random_order, allow_practice_mode, correct_immediately) VALUES ('Test Quiz', 'Test Description', 'MATH', true, false, true, false)");
        }
        quiz = new Quiz("Test Quiz", "Test Description", QuizCategory.MATH, true, false, true, false);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM quizzes");
        }
    }

    @Test
    public void testAddQuestion() {
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE, null);
        quiz.addQuestion(question);
        assertEquals(1, quiz.getQuestionCount());
    }

    @Test
    public void testAddPictureResponseQuestion() {
        Question question = new Question(1, "What is shown in this image?", QuestionType.PICTURE_RESPONSE, "http://example.com/image.jpg");
        quiz.addQuestion(question);
        assertEquals(1, quiz.getQuestionCount());
        assertEquals(QuestionType.PICTURE_RESPONSE, quiz.getQuestions().get(0).getType());
        assertEquals("http://example.com/image.jpg", quiz.getQuestions().get(0).getImageUrl());
    }

    @Test
    public void testRemoveQuestion() {
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE, null);
        quiz.addQuestion(question);
        quiz.removeQuestion(question);
        assertEquals(0, quiz.getQuestionCount());
    }

    @Test
    public void testIsValid() {
        assertFalse(quiz.isValid());  // No questions added yet
        Question question = new Question(1, "Test question?", QuestionType.MULTIPLE_CHOICE, null);
        quiz.addQuestion(question);
        assertTrue(quiz.isValid());
    }

    @Test
    public void testIsValidWithPictureResponseQuestion() {
        Question question = new Question(1, "What is shown in this image?", QuestionType.PICTURE_RESPONSE, "http://example.com/image.jpg");
        question.setCorrectAnswer("Correct answer");
        quiz.addQuestion(question);
        assertTrue(quiz.isValid());
    }

    // Add more tests as needed
}