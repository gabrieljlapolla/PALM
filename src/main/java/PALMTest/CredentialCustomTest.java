package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import PALM.CredentialCustom;

import java.util.HashMap;
import java.util.Map;

class CredentialCustomTest {

	@Test
	void testCreation() {
		HashMap<String, String> map = new HashMap<String, String>(
				Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
		CredentialCustom test = new CredentialCustom("name", map);
		assertTrue(test.getName().equals("name"));
		assertTrue(test.getFields() == map);
	}

}
