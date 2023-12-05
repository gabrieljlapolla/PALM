package PALM;

public class LoginApplication extends Login {
    private String appName;

    public LoginApplication(String name, String username, String password, String appName) {
        super(name, username, password);
        this.appName = appName;
    }

    public String getAppName() {
        return this.appName;
    }

    public String encrypt(String key) {
        return Encrypt.encrypt(
                String.format("%s+%s+%s+%s+%s", this.getClass().getSimpleName(), name, username, password, appName),
                key);
    }

    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nUsername: %s\nPassword: %s\nApplication: %s\n", this.name, this.username,
                this.password, this.appName);
    }
}
