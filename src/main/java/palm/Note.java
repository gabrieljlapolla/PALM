package palm;

public class Note extends Item {
    private final String notes;

    public Note(String name, String notes) {
        super(name);
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    @Override
    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s", this.getClass().getSimpleName(), name, notes), key);
    }

    @Override
    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        return String.format("Name: %s%n%s%n", this.name, this.notes);
    }
}
