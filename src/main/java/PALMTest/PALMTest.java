package PALMTest;

import static org.junit.Assert.assertTrue;

import PALM.LoginDevice;
import org.junit.Before;
import org.junit.Test;
import PALM.CredentialCustom;

import java.util.HashMap;
import java.util.Map;

import PALM.PALM;
import PALM.CredentialCard;
import PALM.CredentialIdentity;
import PALM.Item;
import PALM.ItemManager;
import PALM.LoginApplication;
import PALM.LoginWebSite;
import PALM.Note;

class PALMTest {

	static LoginApplication testLoginApp;
	static LoginDevice testLoginDevice;
	static LoginWebSite testLoginWebsite;
	static CredentialCard testCredentialCard;
	static CredentialCustom testCredentialCustom;
	static CredentialIdentity testCredentialIdentity;
	static Note testNote;

	@Before
	static void init() {
		ItemManager<Item> items = new ItemManager<Item>();

		testLoginApp = new LoginApplication("test app", "username", "password", "Discord");
		items.add(testLoginApp);
		testLoginDevice = new LoginDevice("test device", "username", "password", "iPhone");
		items.add(testLoginDevice);
		testLoginWebsite = new LoginWebSite("test website", "username", "password", "www.google.com");
		items.add(testLoginWebsite);
		testCredentialCard = new CredentialCard("test card", "john doe", 1234567890, 123, "1/1/2000", new HashMap<>());
		items.add(testCredentialCard);
		testCredentialCustom = new CredentialCustom("test custom",
				new HashMap<String, String>(Map.of("custom1", "1", "custom2", "2", "custom3", "3")));
		items.add(testCredentialCustom);
		testCredentialIdentity = new CredentialIdentity("test identity", "drivers license",
				new HashMap<String, String>(Map.of("custom1", "1", "custom2", "2", "custom3", "3")));
		items.add(testCredentialIdentity);
		testNote = new Note("test note", "lineone\nline2\n");
		items.add(testNote);
		PALM.initTesting(items);
	}

	@Test
	void testSearchItem() {
		assertTrue(PALM.searchItem(testLoginApp.getName(), 5).findFirst().orElse(null) == testLoginApp);
		assertTrue(PALM.searchItem(testLoginDevice.getName(), 5).findFirst().orElse(null) == testLoginDevice);
		assertTrue(PALM.searchItem(testLoginWebsite.getName(), 5).findFirst().orElse(null) == testLoginWebsite);
		assertTrue(PALM.searchItem(testCredentialCard.getName(), 5).findFirst().orElse(null) == testCredentialCard);
		assertTrue(PALM.searchItem(testCredentialCustom.getName(), 5).findFirst().orElse(null) == testCredentialCustom);
		assertTrue(PALM.searchItem(testCredentialIdentity.getName(), 5).findFirst()
				.orElse(null) == testCredentialIdentity);
		assertTrue(PALM.searchItem(testNote.getName(), 5).findFirst().orElse(null) == testNote);
	}

}
