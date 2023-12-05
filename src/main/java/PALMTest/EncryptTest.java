package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import PALM.Encrypt;

class EncryptTest {

	@Test
	void testGetPasswordHash() {
		byte[] salt = new byte[] {1, 2, 3, 4, 5};
		assertTrue(Encrypt.getPasswordHash("user", "password", salt) != null);
	}
	
	void testGetPasswordHashNull() {
		assertTrue(Encrypt.getPasswordHash("user", "password", null) != null);
	}
	
	void testGetPasswordHashFail() {
		assertTrue(Encrypt.getPasswordHash(null, null, null) == null);
	}
	
	void testEncryptDecrypt() {
		String data = "data";
		String password = "password";
		String encrypted = Encrypt.encrypt(data, password);
		assertTrue(Encrypt.decrypt(encrypted, password).equals(data));
	}
	
	void testEncryptFail() {
		assertTrue(Encrypt.encrypt("data", null) == null);
	}
	
	void testDecryptFail() {
		assertTrue(Encrypt.decrypt("data", null) == null);
	}
}
