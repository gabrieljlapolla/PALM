package PALM;

import java.io.Serializable;

public class SaltHash implements Serializable {
    public final String username;
    public final byte[] salt;
    public final String hash;

    public SaltHash(String username, byte[] salt, String hash) {
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    @Override
    public String toString() {
        return String.format("User: %s\nSalt: %s\nHash: %s", this.username, this.salt, this.hash);
    }
}
