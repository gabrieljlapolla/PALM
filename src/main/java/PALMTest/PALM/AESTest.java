package PALM;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
public class AESTest {

    @Test
    public void testGetPasswordHash() {
        byte[] salt = new byte[]{1, 2, 3, 4, 5};
        assertTrue(AES.getPasswordHash("user", "password", salt) != null);
    }

    @Test
    public void testGetPasswordHashNull() {
        assertTrue(AES.getPasswordHash("user", "password", null) != null);
    }

    @Test
    public void testGetPasswordHashFail() {
        assertTrue(AES.getPasswordHash(null, null, null) == null);
    }

    @Test
    public void testEncryptDecrypt() {
        String data = "data";
        String password = "password";
        String encrypted = AES.encrypt(data, password);
        assertTrue(AES.decrypt(encrypted, password).equals(data));
    }

    @Test
    public void testEncryptFail() {
        assertTrue(AES.encrypt("data", null) == null);
    }

    @Test
    public void testDecryptFail() {
        assertTrue(AES.decrypt("data", null) == null);
    }
}
