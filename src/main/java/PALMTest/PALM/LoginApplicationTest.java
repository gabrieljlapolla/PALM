package PALM;

import PALM.LoginApplication;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LoginApplicationTest {

    @Test
    public void testCreation() {
        LoginApplication test = new LoginApplication("name", "username", "password", "app name");
        assertTrue(test.getName().equals("name"));
        assertTrue(test.getUsername().equals("username"));
        assertTrue(test.getPassword().equals("password"));
        assertTrue(test.getAppName().equals("app name"));
    }

}
