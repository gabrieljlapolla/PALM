package PALM;

public abstract class Login extends Item {
    protected String username;
    protected String password;

    public Login(String name, String username, String password) {
        super(name);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s+%s", this.getClass().getSimpleName(), name, username, password),
                key);
    }

    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nUsername: %s\nPassword: %s\n", this.name, this.username, this.password);
    }

}
