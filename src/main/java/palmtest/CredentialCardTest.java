package palmtest;

import org.junit.Test;
import palm.CredentialCard;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CredentialCardTest {

    @Test
    public void testCreation() {
        HashMap<String, String> map = new HashMap<>(
                Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
        CredentialCard test = new CredentialCard("name", "holder", 1, 1, "exp", map);
        assertEquals("name", test.getName());
        assertEquals("holder", test.getHolderName());
        assertEquals(1, test.getNumber());
        assertEquals(1, test.getCvv());
        assertEquals("exp", test.getExpiration());
        assertSame(test.getFields(), map);
    }

}
