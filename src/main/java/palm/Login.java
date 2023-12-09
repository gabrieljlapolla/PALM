package palm;

public abstract class Login extends Item {
    protected String username;
    protected String password;

    protected Login(String name, String username, String password) {
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

    @Override
    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s+%s", this.getClass().getSimpleName(), name, username, password),
                key);
    }

    @Override
    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s%nUsername: %s%nPassword: %s%n", this.name, this.username, this.password);
    }

}
