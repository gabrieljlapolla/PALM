package PALM;

public class Note extends Item {
    private String notes;

    public Note(String name, String notes) {
        super(name);
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n%s\n", this.name, this.notes);
    }

    public String encrypt(String key) {
        return AES.encrypt(String.format("%s+%s+%s", this.getClass().getSimpleName(), name, notes), key);
    }

    public String decrypt(String encryptedData, String key) {
        return AES.decrypt(encryptedData, key);
    }
}
