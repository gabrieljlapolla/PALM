package PALM;

public class LoginWebSite extends Login {
    private String url;

    public LoginWebSite(String name, String username, String password, String url) {
        super(name, username, password);
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public String encrypt(String key) {
        return AES.encrypt(
                String.format("%s+%s+%s+%s+%s", this.getClass().getSimpleName(), name, username, password, url), key);
    }

    public String decrypt(String encryptedData, String key) {
        return AES.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nUsername: %s\nPassword: %s\nURL: %s\n", this.name, this.username, this.password,
                this.url);
    }
}
