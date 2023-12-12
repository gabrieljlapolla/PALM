package palm;

import org.junit.Test;
import palm.CredentialIdentity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CredentialIdentityTest {

    @Test
    public void testCreation() {
        HashMap<String, String> map = new HashMap<>(
                Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
        CredentialIdentity test = new CredentialIdentity("name", "type", map);
        assertEquals("name", test.getName());
        assertEquals("type", test.getType());
        assertSame(test.getFields(), map);
    }

}
