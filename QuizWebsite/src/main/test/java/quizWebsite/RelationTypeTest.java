package quizWebsite;
import com.example.quizwebsite.relationManager.RelationType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RelationTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(4, RelationType.values().length);
        assertArrayEquals(new RelationType[] {
                RelationType.NOT_FRIENDS,
                RelationType.REQUEST_FROM_USER_1,
                RelationType.REQUEST_FROM_USER_2,
                RelationType.FRIENDS
        }, RelationType.values());
    }

    @Test
    public void testEnumValuesExist() {
        assertNotNull(RelationType.valueOf("NOT_FRIENDS"));
        assertNotNull(RelationType.valueOf("REQUEST_FROM_USER_1"));
        assertNotNull(RelationType.valueOf("REQUEST_FROM_USER_2"));
        assertNotNull(RelationType.valueOf("FRIENDS"));
    }
}