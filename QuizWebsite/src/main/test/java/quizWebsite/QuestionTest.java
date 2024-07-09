package quizWebsite;

import java.util.*;
import com.example.quizwebsite.quizManager.Question;
import com.example.quizwebsite.quizManager.QuestionType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;
    private int quizId;
    private String text;
    private QuestionType type;

    @BeforeEach
    void setUp() {
        quizId = 1;
        text = "What is 2+2?";
        type = QuestionType.MULTIPLE_CHOICE;
        question = new Question(quizId, text, type, null);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(quizId, question.getQuizId());
        assertEquals(text, question.getText());
        assertEquals(type, question.getType());
        assertTrue(question.getOptions().isEmpty());
        assertTrue(question.getCorrectOptions().isEmpty());
        assertNull(question.getImageUrl());
    }

    @Test
    void testSetId() {
        Question question1 = new Question(2, "test Question", QuestionType.MULTIPLE_CHOICE, null);
        question1.setId(5);
        assertEquals(5, question1.getId());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            question1.setId(-1);
        });
        assertEquals("ID must be non-negative", exception.getMessage());
    }

    @Test
    void testSetQuizId() {
        question.setQuizId(2);
        assertEquals(2, question.getQuizId());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            question.setQuizId(-1);
        });
        assertEquals("Quiz ID must be non-negative", exception.getMessage());
    }

    @Test
    void testSetText() {
        question.setText("What is 3+3?");
        assertEquals("What is 3+3?", question.getText());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.setText(null);
        });
        assertEquals("Question text cannot be null", exception.getMessage());
    }

    @Test
    void testSetType() {
        question.setType(QuestionType.TRUE_FALSE);
        assertEquals(QuestionType.TRUE_FALSE, question.getType());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.setType(null);
        });
        assertEquals("Question type cannot be null", exception.getMessage());
    }

    @Test
    void testSetOptions() {
        List<String> options = Arrays.asList("3", "4");
        question.setOptions(options);
        assertEquals(options, question.getOptions());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.setOptions(null);
        });
        assertEquals("Options list cannot be null", exception.getMessage());
    }

    @Test
    void testSetCorrectOptions() {
        List<Boolean> correctOptions = Arrays.asList(false, true);
        question.setCorrectOptions(correctOptions);
        assertEquals(correctOptions, question.getCorrectOptions());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.setCorrectOptions(null);
        });
        assertEquals("Correct options list cannot be null", exception.getMessage());
    }

    @Test
    void testAddOption() {
        question.addOption("3", false);
        question.addOption("4", true);

        assertEquals(Arrays.asList("3", "4"), question.getOptions());
        assertEquals(Arrays.asList(false, true), question.getCorrectOptions());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            question.addOption(null, false);
        });
        assertEquals("Option cannot be null", exception.getMessage());
    }

    @Test
    void testClearOptions() {
        question.addOption("3", false);
        question.addOption("4", true);

        question.clearOptions();
        assertTrue(question.getOptions().isEmpty());
        assertTrue(question.getCorrectOptions().isEmpty());
    }

    @Test
    void testHasCorrectOption() {
        assertFalse(question.hasCorrectOption());

        question.addOption("3", false);
        question.addOption("4", true);
        assertTrue(question.hasCorrectOption());
    }

    @Test
    void testGetOptionCount() {
        assertEquals(0, question.getOptionCount());

        question.addOption("3", false);
        question.addOption("4", true);
        assertEquals(2, question.getOptionCount());
    }

    @Test
    void testIsValid() {
        Question multipleChoiceQuestion = new Question(1, "What is 2+2?", QuestionType.MULTIPLE_CHOICE, null);
        assertFalse(multipleChoiceQuestion.isValid());

        multipleChoiceQuestion.addOption("3", false);
        multipleChoiceQuestion.addOption("4", true);
        assertTrue(multipleChoiceQuestion.isValid());

        Question trueFalseQuestion = new Question(1, "2+2=4", QuestionType.TRUE_FALSE, null);
        assertFalse(trueFalseQuestion.isValid());

        trueFalseQuestion.addOption("True", true);
        trueFalseQuestion.addOption("False", false);
        assertTrue(trueFalseQuestion.isValid());

        Question singleAnswerQuestion = new Question(1, "What is the capital of France?", QuestionType.SINGLE_ANSWER, null);
        assertFalse(singleAnswerQuestion.isValid());

        singleAnswerQuestion.setCorrectAnswer("Paris");
        assertTrue(singleAnswerQuestion.isValid());

        Question pictureResponseQuestion = new Question(1, "What is shown in this image?", QuestionType.PICTURE_RESPONSE, "http://example.com/image.jpg");
        assertFalse(pictureResponseQuestion.isValid());

        pictureResponseQuestion.setCorrectAnswer("Eiffel Tower");
        assertTrue(pictureResponseQuestion.isValid());
    }

    @Test
    void testPictureResponseQuestion() {
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/85/Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg";
        Question pictureQuestion = new Question(1, "What is shown in this image?", QuestionType.PICTURE_RESPONSE, imageUrl);

        assertEquals(QuestionType.PICTURE_RESPONSE, pictureQuestion.getType());
        assertEquals(imageUrl, pictureQuestion.getImageUrl());

        pictureQuestion.setCorrectAnswer("Eiffel Tower");
        assertTrue(pictureQuestion.isValid());

        pictureQuestion.setImageUrl(null);
        assertFalse(pictureQuestion.isValid());
    }

    @Test
    void testSetImageUrl() {
        String imageUrl = "http://example.com/new-image.jpg";
        question.setImageUrl(imageUrl);
        assertEquals(imageUrl, question.getImageUrl());
    }
}