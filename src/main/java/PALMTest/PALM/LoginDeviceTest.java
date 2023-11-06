package PALM;

import PALM.LoginDevice;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LoginDeviceTest {

    @Test
    public void testCreation() {
        LoginDevice test = new LoginDevice("name", "username", "password", "device");
        assertTrue(test.getName().equals("name"));
        assertTrue(test.getUsername().equals("username"));
        assertTrue(test.getPassword().equals("password"));
        assertTrue(test.getDevice().equals("device"));
    }

}
