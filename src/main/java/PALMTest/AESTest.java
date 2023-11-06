package PALMTest;

import static org.junit.Assert.assertTrue;

import PALM.LoginDevice;
import org.junit.Test;
import PALM.AES;

class AESTest {

	@Test
	void testGetPasswordHash() {
		byte[] salt = new byte[] {1, 2, 3, 4, 5};
		assertTrue(AES.getPasswordHash("user", "password", salt) != null);
	}
	
	void testGetPasswordHashNull() {
		assertTrue(AES.getPasswordHash("user", "password", null) != null);
	}
	
	void testGetPasswordHashFail() {
		assertTrue(AES.getPasswordHash(null, null, null) == null);
	}
	
	void testEncryptDecrypt() {
		String data = "data";
		String password = "password";
		String encrypted = AES.encrypt(data, password);
		assertTrue(AES.decrypt(encrypted, password).equals(data));
	}
	
	void testEncryptFail() {
		assertTrue(AES.encrypt("data", null) == null);
	}
	
	void testDecryptFail() {
		assertTrue(AES.decrypt("data", null) == null);
	}
}
