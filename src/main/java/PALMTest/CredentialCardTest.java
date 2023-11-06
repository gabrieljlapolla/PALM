package PALMTest;

import PALM.CredentialCard;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

class CredentialCardTest {

	@Test
	void testCreation() {
		HashMap<String, String> map = new HashMap<String, String>(
				Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
		CredentialCard test = new CredentialCard("name", "holder", 1, 1, "exp", map);
		assertTrue(test.getName().equals("name"));
		assertTrue(test.getHolderName().equals("holder"));
		assertTrue(test.getNumber() == 1);
		assertTrue(test.getCvv() == 1);
		assertTrue(test.getExpiration().equals("exp"));
		assertTrue(test.getFields() == map);
	}

}
