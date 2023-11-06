package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import PALM.LoginWebSite;

class LoginWebSiteTest {

	@Test
	void testCreation() {
		LoginWebSite test = new LoginWebSite("name", "username", "password", "url");
		assertTrue(test.getName().equals("name"));
		assertTrue(test.getUsername().equals("username"));
		assertTrue(test.getPassword().equals("password"));
		assertTrue(test.getUrl().equals("url"));
	}

}
