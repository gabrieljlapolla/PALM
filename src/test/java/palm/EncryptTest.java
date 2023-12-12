package palm;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.Assertions;

import java.security.NoSuchAlgorithmException;

public class EncryptTest {

    @FuzzTest
    public void completeFuzzTest(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        byte[] salt = fdp.consumeBytes(16);
        Encrypt.getPasswordHash(username, password, salt);

        String hash = fdp.consumeRemainingAsString();
        SaltHash sh = new SaltHash("username", null, hash);
        // Check random password
        Encrypt.checkPassword(password, sh);
        // Check correct password
        SaltHash correctSh = Encrypt.getPasswordHash(username, password, null);
        Encrypt.checkPassword(password, correctSh);

        String key = fdp.consumeRemainingAsString();
        try {
            Encrypt.setKey(key);
        } catch (NoSuchAlgorithmException ignored) {
        }

        String data = fdp.consumeRemainingAsString();
        // Random fail decrypt
        Encrypt.decrypt(data, key);
        String encryptedData = Encrypt.encrypt(data, key);
        Assertions.assertEquals(data, Encrypt.decrypt(encryptedData, key));
    }

    @FuzzTest
    public void fuzzGetPasswordHash(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        byte[] salt = fdp.consumeBytes(16);
        Encrypt.getPasswordHash(username, password, salt);
    }

    @FuzzTest
    public void fuzzCheckPassword(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        String hash = fdp.consumeRemainingAsString();
        SaltHash sh = new SaltHash("username", null, hash);
        // Check random password
        Encrypt.checkPassword(password, sh);
        // Check correct password
        SaltHash correctSh = Encrypt.getPasswordHash(username, password, null);
        Encrypt.checkPassword(password, correctSh);
    }

    @FuzzTest
    public void fuzzSetKey(FuzzedDataProvider fdp) {
        String key = fdp.consumeRemainingAsString();
        try {
            Encrypt.setKey(key);
        } catch (NoSuchAlgorithmException ignored) {
        }
    }

    @FuzzTest
    public void fuzzEncryption(FuzzedDataProvider fdp) {
        String data = fdp.consumeRemainingAsString();
        String key = fdp.consumeRemainingAsString();
        // Random fail decrypt
        Encrypt.decrypt(data, key);
        String encryptedData = Encrypt.encrypt(data, key);
        Assertions.assertEquals(data, Encrypt.decrypt(encryptedData, key));
    }
}
