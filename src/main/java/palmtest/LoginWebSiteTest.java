package palmtest;

import org.junit.Test;
import palm.LoginWebSite;

import static org.junit.Assert.assertEquals;

public class LoginWebSiteTest {

    @Test
    public void testCreation() {
        LoginWebSite test = new LoginWebSite("name", "username", "password", "url");
        assertEquals("name", test.getName());
        assertEquals("username", test.getUsername());
        assertEquals("password", test.getPassword());
        assertEquals("url", test.getUrl());
    }

}
