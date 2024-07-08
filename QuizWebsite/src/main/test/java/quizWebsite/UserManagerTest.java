package quizWebsite;

import com.example.quizwebsite.userManager.User;
import com.example.quizwebsite.userManager.UserManager;
import junit.framework.TestCase;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {
    private static BasicDataSource dataSource;
    private static UserManager userManager;

    @BeforeAll
    public static void setup() {
        // Set up the database connection pool
        dataSource = new BasicDataSource();
        // Configure the data source with your database credentials
        dataSource.setUrl("jdbc:mysql://localhost/test_quizwebsite_db");
        dataSource.setUsername("root");
        dataSource.setPassword("Elene2004!");
        // Create an instance of the UserManager
        userManager = new UserManager(dataSource);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            // Clear existing data
            stmt.executeUpdate("DELETE FROM users");

            // Insert some initial data if needed
//            stmt.executeUpdate("INSERT INTO users (username, password, is_admin) VALUES ('testuser', 'password123', false)");
//            stmt.executeUpdate("INSERT INTO users (username, password, is_admin) VALUES ('adminuser', 'adminpass', true)");
        }
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM users");
        }
    }

    @Test
    public void testAddUser() {
        User newUser = new User("testuser", "password123");
        User addedUser = UserManager.addUser(newUser);

        assertNotNull(addedUser);
        assertNotNull(addedUser.getId());
        assertEquals("testuser", addedUser.getUsername());
        assertTrue(UserManager.userExists("testuser"));
    }

    @Test
    public void testDeleteUser() {
        User newUser = new User("deleteuser", "password123");
        User addedUser = UserManager.addUser(newUser);

        assertTrue(UserManager.deleteUser(addedUser.getId()));
        assertFalse(UserManager.userExists("deleteuser"));
    }

    @Test
    public void testGetUserByUsername() {
        User newUser = new User("getuser", "password123");
        UserManager.addUser(newUser);

        User retrievedUser = UserManager.getUserByUsername("getuser");
        assertNotNull(retrievedUser);
        assertEquals("getuser", retrievedUser.getUsername());
    }

    @Test
    public void testValidateUser() {
        User newUser = new User("validateuser", "password123");
        UserManager.addUser(newUser);

        assertTrue(userManager.validateUser("validateuser", "password123"));
        assertFalse(userManager.validateUser("validateuser", "wrongpassword"));
    }

    @Test
    public void testMakeUserAdmin() {
        User newUser = new User("adminuser", "password123");
        User addedUser = UserManager.addUser(newUser);

        assertTrue(userManager.makeUserAdmin(addedUser.getId()));
        User adminUser = UserManager.getUserByUsername("adminuser");
        assertTrue(adminUser.isAdmin());
    }

    @Test
    public void testSetCookieKey() {
        User newUser = new User("cookieuser", "password123");
        User addedUser = UserManager.addUser(newUser);

        String cookieKey = userManager.setCookieKey(addedUser.getId());
        assertNotNull(cookieKey);
        User updatedUser = UserManager.getUserByUsername("cookieuser");
        assertEquals(cookieKey, updatedUser.getCookieKey());
    }

    @Test
    public void testGetUserByCookieKey() {
        User newUser = new User("cookiekeyuser", "password123");
        User addedUser = UserManager.addUser(newUser);
        String cookieKey = userManager.setCookieKey(addedUser.getId());

        User retrievedUser = UserManager.getUserByCookieKey(cookieKey);
        assertNotNull(retrievedUser);
        assertEquals("cookiekeyuser", retrievedUser.getUsername());
    }
}