package palm;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class PALMDBTest {
    static PALMDB PDB = new PALMDB();

    @FuzzTest
    public void completeFuzzTest(FuzzedDataProvider fdp) {
        ItemManager<Item> items = new ItemManager<>();
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        String hash = fdp.consumeRemainingAsString();
        SaltHash sh = new SaltHash("username", null, hash);
        String uuid = fdp.consumeRemainingAsString();
        String totpsid = fdp.consumeRemainingAsString();

        // Check before addition to database
        PDB.checkUser(username, password);
        PDB.readUserUuid(username);
        PDB.readUserTotpSid(username);
        PDB.readItems(username, password, items);

        PDB.writeUser(username, sh, uuid, totpsid);
        PDB.writeItems(username, password, items);

        // Check after addition to database
        PDB.checkUser(username, password);
        PDB.readUserUuid(username);
        PDB.readUserTotpSid(username);
        PDB.readItems(username, password, items);
    }

    @FuzzTest
    public void fuzzWriteUser(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String hash = fdp.consumeRemainingAsString();
        SaltHash sh = new SaltHash("username", null, hash);
        String uuid = fdp.consumeRemainingAsString();
        String totpsid = fdp.consumeRemainingAsString();
        PDB.writeUser(username, sh, uuid, totpsid);
    }

    @FuzzTest
    public void fuzzCheckUser(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        PDB.checkUser(username, password);
    }

    @FuzzTest
    public void fuzzReadUserUUID(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        PDB.readUserUuid(username);
    }

    @FuzzTest
    public void fuzzReadUserTotpSid(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        PDB.readUserTotpSid(username);
    }

    @FuzzTest
    public void fuzzReadItems(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        PDB.readItems(username, password, new ItemManager<>());
    }

    @FuzzTest
    public void fuzzWriteItems(FuzzedDataProvider fdp) {
        String username = fdp.consumeRemainingAsString();
        String password = fdp.consumeRemainingAsString();
        PDB.writeItems(username, password, new ItemManager<>());
    }
}
