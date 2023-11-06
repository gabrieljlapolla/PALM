package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import PALM.CredentialIdentity;

class CredentialIdentityTest {

	@Test
	void testCreation() {
		HashMap<String, String> map = new HashMap<String, String>(
				Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
		CredentialIdentity test = new CredentialIdentity("name", "type", map);
		assertTrue(test.getName().equals("name"));
		assertTrue(test.getType().equals("type"));
		assertTrue(test.getFields() == map);
	}

}
