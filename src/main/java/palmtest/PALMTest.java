package palmtest;

import org.junit.Before;
import org.junit.Test;
import palm.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertSame;

public class PALMTest {

    static LoginApplication testLoginApp;
    static LoginDevice testLoginDevice;
    static LoginWebSite testLoginWebsite;
    static CredentialCard testCredentialCard;
    static CredentialCustom testCredentialCustom;
    static CredentialIdentity testCredentialIdentity;
    static Note testNote;

    @Before
    public void init() {
        ItemManager<Item> items = new ItemManager<>();

        testLoginApp = new LoginApplication("test app", "username", "password", "Discord");
        items.add(testLoginApp);
        testLoginDevice = new LoginDevice("test device", "username", "password", "iPhone");
        items.add(testLoginDevice);
        testLoginWebsite = new LoginWebSite("test website", "username", "password", "www.google.com");
        items.add(testLoginWebsite);
        testCredentialCard = new CredentialCard("test card", "john doe", 1234567890, 123, "1/1/2000", new HashMap<>());
        items.add(testCredentialCard);
        testCredentialCustom = new CredentialCustom("test custom",
                new HashMap<>(Map.of("custom1", "1", "custom2", "2", "custom3", "3")));
        items.add(testCredentialCustom);
        testCredentialIdentity = new CredentialIdentity("test identity", "drivers license",
                new HashMap<>(Map.of("custom1", "1", "custom2", "2", "custom3", "3")));
        items.add(testCredentialIdentity);
        testNote = new Note("test note", "line 1\nline 2\n");
        items.add(testNote);
        PALM.initTesting(items);
    }

    @Test
    public void testSearchItem() {
        assertSame(PALM.searchItem(testLoginApp.getName(), 5).findFirst().orElse(null), testLoginApp);
        assertSame(PALM.searchItem(testLoginDevice.getName(), 5).findFirst().orElse(null), testLoginDevice);
        assertSame(PALM.searchItem(testLoginWebsite.getName(), 5).findFirst().orElse(null), testLoginWebsite);
        assertSame(PALM.searchItem(testCredentialCard.getName(), 5).findFirst().orElse(null), testCredentialCard);
        assertSame(PALM.searchItem(testCredentialCustom.getName(), 5).findFirst().orElse(null), testCredentialCustom);
        assertSame(PALM.searchItem(testCredentialIdentity.getName(), 5).findFirst()
                .orElse(null), testCredentialIdentity);
        assertSame(PALM.searchItem(testNote.getName(), 5).findFirst().orElse(null), testNote);
    }

}
