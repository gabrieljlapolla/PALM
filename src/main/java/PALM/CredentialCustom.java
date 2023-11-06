package PALM;

import java.util.HashMap;
import java.util.Map;

public class CredentialCustom extends Item {
    protected HashMap<String, String> fields;

    public CredentialCustom(String name, HashMap<String, String> fields) {
        super(name);
        this.fields = fields;
    }

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public String encrypt(String key) {
        return AES.encrypt(String.format("%s+%s+%s", this.getClass().getSimpleName(), name, fields.toString()), key);
    }

    public String decrypt(String encryptedData, String key) {
        return AES.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s\n", this.name));
        // Iterate over fields
        if (this.fields != null) {
            for (Map.Entry<String, String> field : fields.entrySet()) {
                sb.append(String.format("%s: %s\n", field.getKey(), field.getValue()));
            }
        }
        return sb.toString();
    }
}
