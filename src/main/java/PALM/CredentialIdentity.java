package PALM;

import java.util.HashMap;
import java.util.Map;

public class CredentialIdentity extends Item {
    protected String type;
    protected HashMap<String, String> fields;

    public CredentialIdentity(String name, String type, HashMap<String, String> fields) {
        super(name);
        this.type = type;
        this.fields = fields;
    }

    public String getType() {
        return this.type;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s+%s", this.getClass().getSimpleName(), name, type, fields.toString()),
                key);
    }

    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s\nType: %s\n", this.name, this.type));
        // Iterate over fields
        if (this.fields != null) {
            for (Map.Entry<String, String> field : fields.entrySet()) {
                sb.append(String.format("%s: %s\n", field.getKey(), field.getValue()));
            }
        }
        return sb.toString();
    }
}
