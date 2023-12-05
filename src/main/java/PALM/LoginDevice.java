package PALM;

public class LoginDevice extends Login {
    private String device;

    public LoginDevice(String name, String username, String password, String device) {
        super(name, username, password);
        this.device = device;
    }

    public String getDevice() {
        return this.device;
    }

    public String encrypt(String key) {
        return Encrypt.encrypt(
                String.format("%s+%s+%s+%s+%s", this.getClass().getSimpleName(), name, username, password, device),
                key);
    }

    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nUsername: %s\nPassword: %s\nDevice: %s\n", this.name, this.username,
                this.password, this.device);
    }
}
