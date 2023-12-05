package PALM;

import java.util.HashMap;
import java.util.Map;

public class CredentialCard extends Item {
    private String holderName;
    private int number;
    private int cvv;
    private String expiration;
    private HashMap<String, String> fields;

    public CredentialCard(String name, String holderName, int number, int cvv, String expiration,
                          HashMap<String, String> fields) {
        super(name);
        this.holderName = holderName;
        this.number = number;
        this.cvv = cvv;
        this.expiration = expiration;
        this.fields = fields;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public String getExpiration() {
        return expiration;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s+%d+%d+%s+%s", this.getClass().getSimpleName(), name, holderName,
                number, cvv, expiration, fields.toString()), key);
    }

    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s\nHolder: %s\nNumber: %d CVV: %d Expiration: %s\n", this.name, this.holderName,
                this.number, this.cvv, this.expiration));
        // Iterate over fields
        if (this.fields != null) {
            for (Map.Entry<String, String> field : fields.entrySet()) {
                sb.append(String.format("%s: %s\n", field.getKey(), field.getValue()));
            }
        }
        return sb.toString();
    }
}
