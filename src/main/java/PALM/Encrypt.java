package PALM;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Encrypt {
    private final static String HASH_ALGORITHM = "SHA-256";
    private final static String ENCRYPT_ALGORITHM = "AES";
    private final static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    // Argon2 Hashing parameters
    private final static int SALT_LENGTH = 16;
    private final static int HASH_LENGTH = 32;
    private final static int PARALLELISM = 3;
    private final static int MEMORY = 100000;
    private final static int ITERATIONS = 5;

    /**
     * Creates a hash of the given password using the given salt or by generating a
     * new one
     *
     * @param username Current user
     * @param password Password to be hashed
     * @param salt     Salt to use when hashing, giving null generates a new hash
     * @return SaltHash object containing the created salt and hash
     * @preconditions Password and salt are not null
     * @postconditions New SaltHash object is created containing salt and newly
     * hashed password
     */
    public static SaltHash getPasswordHash(String username, String password, byte[] salt) {
        if (salt == null) {
            // Generate random salt
            salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);
        }

        Argon2PasswordEncoder encoder =
                new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);

        return new SaltHash(username, salt, encoder.encode(password));
    }

    /**
     * Compares a given username and password combination to saved ones
     *
     * @param password Password to check
     * @return True if a correct combination is given
     * @preconditions Password must already be hashed and given as SaltHash
     * @postconditions If hashes match, true is returned
     */
    public static boolean checkPassword(String password, SaltHash sh) {
        Argon2PasswordEncoder encoder =
                new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);
        return encoder.matches(password, sh.hash);
    }

    /**
     * Generates a SecretKey based on a given key
     *
     * @param key Key used to create SecretKey
     * @return Created SecretKeySpec object
     * @throws NoSuchAlgorithmException     Incorrect algorithm
     * @throws UnsupportedEncodingException Cannot encode
     * @preconditions Key is not null
     * @postconditions Creates a SecretKeySpec object used to encrypt or decrypt
     * data
     */
    public static SecretKeySpec setKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] keyBytes;
        MessageDigest md = null;
        keyBytes = key.getBytes(StandardCharsets.UTF_8);
        md = MessageDigest.getInstance(HASH_ALGORITHM);
        keyBytes = md.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, ENCRYPT_ALGORITHM);
    }

    /**
     * Encrypts data using given key
     *
     * @param data Data to be encrypted
     * @param key  Key used to encrypt data
     * @return Encrypted String of data or null if unsuccessful
     * @preconditions Data and key are not null
     * @postconditions Data will be encrypted using key
     */
    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, setKey(key));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.err.println("Error encrypting...");
        }
        return null;
    }

    /**
     * Decrypts a string of encrypted data using the given key
     *
     * @param encryptedData Data to be decrypted
     * @param key           Key used to decrypt data
     * @return Decrypted data or null if unsuccessful
     * @preconditions Data must already be encrypted using the same key as the given key
     * @postconditions Data will be decrypted to plain text
     */
    public static String decrypt(String encryptedData, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, setKey(key));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        } catch (Exception e) {
            System.err.println("Error decrypting...");
        }
        return null;
    }

    public static void main(String[] args) {
        SaltHash sh = getPasswordHash("username1", "password1", null);
        System.out.println(getPasswordHash("username1", "password1", sh.salt));
        System.out.println(checkPassword("password1", sh));
    }

    public interface Encryptable {
        public String encrypt(String key);

        public String decrypt(String encryptedData, String key);
    }
}
