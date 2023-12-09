package palmtest;

import org.junit.Test;
import palm.CredentialCustom;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CredentialCustomTest {

    @Test
    public void testCreation() {
        HashMap<String, String> map = new HashMap<>(
                Map.of("key1", "item1", "key2", "item2", "key3", "item3"));
        CredentialCustom test = new CredentialCustom("name", map);
        assertEquals("name", test.getName());
        assertSame(test.getFields(), map);
    }

}
