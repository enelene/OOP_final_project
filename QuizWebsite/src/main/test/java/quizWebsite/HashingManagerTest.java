package quizWebsite;
import com.example.quizwebsite.userManager.HashingManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

public class HashingManagerTest {

    @Test
    public void testGenerateHash() throws NoSuchAlgorithmException {
        String password = "testPassword123";
        String hash = HashingManager.generateHash(password);

        assertNotNull(hash);
        assertEquals(40, hash.length()); // SHA-1 hash is always 40 characters long

        // Verify that the same input produces the same hash
        String secondHash = HashingManager.generateHash(password);
        assertEquals(hash, secondHash);
    }

    @Test
    public void testHexToString() {
        byte[] bytes = {(byte)0xAB, (byte)0xCD, (byte)0xEF};
        String hexString = HashingManager.hexToString(bytes);

        assertEquals("abcdef", hexString);
    }

    @Test
    public void testHexToStringWithZeroes() {
        byte[] bytes = {(byte)0x00, (byte)0x01, (byte)0x02};
        String hexString = HashingManager.hexToString(bytes);

        assertEquals("000102", hexString);
    }
}
