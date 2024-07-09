package quizWebsite;

import com.example.quizwebsite.quizManager.QuestionType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(5, QuestionType.values().length);
        assertArrayEquals(new QuestionType[] {
                QuestionType.MULTIPLE_CHOICE,
                QuestionType.SINGLE_ANSWER,
                QuestionType.TRUE_FALSE,
                QuestionType.FILL_IN_THE_BLANK,
                QuestionType.PICTURE_RESPONSE
        }, QuestionType.values());
    }

    @Test
    public void testEnumValuesExist() {
        assertNotNull(QuestionType.valueOf("MULTIPLE_CHOICE"));
        assertNotNull(QuestionType.valueOf("SINGLE_ANSWER"));
        assertNotNull(QuestionType.valueOf("TRUE_FALSE"));
        assertNotNull(QuestionType.valueOf("FILL_IN_THE_BLANK"));
        assertNotNull(QuestionType.valueOf("PICTURE_RESPONSE"));
    }

    @Test
    public void testEnumValuesCount() {
        assertEquals(5, QuestionType.values().length);
    }

    @Test
    public void testPictureResponseEnumValue() {
        assertEquals(QuestionType.PICTURE_RESPONSE, QuestionType.valueOf("PICTURE_RESPONSE"));
    }
}