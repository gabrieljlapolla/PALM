package PALM;

import PALM.CredentialIdentity;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class CredentialIdentityTest {

    @Test
    public void testCreation() {
        HashMap<String, String> map = new HashMap<String, String>(
                Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
        CredentialIdentity test = new CredentialIdentity("name", "type", map);
        assertTrue(test.getName().equals("name"));
        assertTrue(test.getType().equals("type"));
        assertTrue(test.getFields() == map);
    }

}
