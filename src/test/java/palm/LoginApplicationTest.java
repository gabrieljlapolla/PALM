package palm;

import org.junit.Test;
import palm.LoginApplication;

import static org.junit.Assert.assertEquals;

public class LoginApplicationTest {

    @Test
    public void testCreation() {
        LoginApplication test = new LoginApplication("name", "username", "password", "app name");
        assertEquals("name", test.getName());
        assertEquals("username", test.getUsername());
        assertEquals("password", test.getPassword());
        assertEquals("app name", test.getAppName());
    }

}
