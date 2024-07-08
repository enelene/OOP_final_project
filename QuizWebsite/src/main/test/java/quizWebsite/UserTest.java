package quizWebsite;
import com.example.quizwebsite.userManager.User;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends TestCase {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testUser", "password123");
    }

    public void testConstructor() {
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertFalse(user.isAdmin());
        assertNull(user.getId());
        assertNull(user.getCookieKey());
    }
    public void testSettersAndGetters() {
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());

        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());

        user.makeAdmin();
        assertTrue(user.isAdmin());

        user.setCookieKey("testCookieKey");
        assertEquals("testCookieKey", user.getCookieKey());
    }
    public void testFullConstructor() {
        User fullUser = new User(1, "fullUser", "fullPassword", true, "fullCookieKey");
        assertEquals("fullUser", fullUser.getUsername());
        assertEquals("fullPassword", fullUser.getPassword());
        assertTrue(fullUser.isAdmin());
        assertEquals("fullCookieKey", fullUser.getCookieKey());
    }
}