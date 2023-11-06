package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import PALM.LoginApplication;

class LoginApplicationTest {

	@Test
	void testCreation() {
		LoginApplication test = new LoginApplication("name", "username", "password", "app name");
		assertTrue(test.getName().equals("name"));
		assertTrue(test.getUsername().equals("username"));
		assertTrue(test.getPassword().equals("password"));
		assertTrue(test.getAppName().equals("app name"));
	}

}
