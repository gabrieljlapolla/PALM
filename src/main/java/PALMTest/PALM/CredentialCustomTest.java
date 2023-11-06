package PALM;

import PALM.CredentialCustom;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class CredentialCustomTest {

    @Test
    public void testCreation() {
        HashMap<String, String> map = new HashMap<String, String>(
                Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
        CredentialCustom test = new CredentialCustom("name", map);
        assertTrue(test.getName().equals("name"));
        assertTrue(test.getFields() == map);
    }

}
