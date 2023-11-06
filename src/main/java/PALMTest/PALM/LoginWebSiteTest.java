package PALM;

import PALM.LoginWebSite;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LoginWebSiteTest {

    @Test
    public void testCreation() {
        LoginWebSite test = new LoginWebSite("name", "username", "password", "url");
        assertTrue(test.getName().equals("name"));
        assertTrue(test.getUsername().equals("username"));
        assertTrue(test.getPassword().equals("password"));
        assertTrue(test.getUrl().equals("url"));
    }

}
