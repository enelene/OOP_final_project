package quizWebsite;
import com.example.quizwebsite.quizManager.QuestionType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(4, QuestionType.values().length);
        assertArrayEquals(new QuestionType[] {
                QuestionType.MULTIPLE_CHOICE,
                QuestionType.SINGLE_ANSWER,
                QuestionType.TRUE_FALSE,
                QuestionType.FILL_IN_THE_BLANK
        }, QuestionType.values());
    }

    @Test
    public void testEnumValuesExist() {
        assertNotNull(QuestionType.valueOf("MULTIPLE_CHOICE"));
        assertNotNull(QuestionType.valueOf("SINGLE_ANSWER"));
        assertNotNull(QuestionType.valueOf("TRUE_FALSE"));
        assertNotNull(QuestionType.valueOf("FILL_IN_THE_BLANK"));
    }
}