package quizWebsite;
import com.example.quizwebsite.relationManager.RelationManager;
import com.example.quizwebsite.relationManager.RelationType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RelationManagerTest {
    private static BasicDataSource dataSource;
    private static RelationManager relationManager;

    @BeforeAll
    public static void setup() {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_quiz_website_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        relationManager = new RelationManager(dataSource);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM relations");
            stmt.executeUpdate("DELETE FROM users");
            stmt.executeUpdate("INSERT INTO users (id, username) VALUES (1, 'user1'), (2, 'user2')");
        }
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM relations");
            stmt.executeUpdate("DELETE FROM users");
        }
    }

    @Test
    public void testAddFriend() {
        assertTrue(relationManager.sendRequest(1, 2));
        assertTrue(relationManager.addFriend(1, 2));
        assertEquals(RelationType.FRIENDS, relationManager.getRelation(1, 2));
    }

    @Test
    public void testRemoveFriend() {
        relationManager.sendRequest(1, 2);
        relationManager.addFriend(1, 2);
        assertTrue(relationManager.removeFriend(1, 2));
        assertEquals(RelationType.NOT_FRIENDS, relationManager.getRelation(1, 2));
    }

    @Test
    public void testSendRequest() {
        assertTrue(relationManager.sendRequest(1, 2));
        assertEquals(RelationType.REQUEST_FROM_USER_1, relationManager.getRelation(1, 2));
    }

    @Test
    public void testGetFriends() {
        relationManager.sendRequest(1, 2);
        relationManager.addFriend(1, 2);
        assertEquals(1, relationManager.getFriends(1).size());
        assertTrue(relationManager.getFriends(1).stream().anyMatch(u -> u.getUsername().equals("user2")));
    }

    // Add more tests as needed
}