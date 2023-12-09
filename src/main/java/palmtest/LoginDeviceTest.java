package palmtest;

import org.junit.Test;
import palm.LoginDevice;

import static org.junit.Assert.assertEquals;

public class LoginDeviceTest {

    @Test
    public void testCreation() {
        LoginDevice test = new LoginDevice("name", "username", "password", "device");
        assertEquals("name", test.getName());
        assertEquals("username", test.getUsername());
        assertEquals("password", test.getPassword());
        assertEquals("device", test.getDevice());
    }

}
