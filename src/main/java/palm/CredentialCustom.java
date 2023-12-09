package palm;

import java.util.Map;

public class CredentialCustom extends Item {
    private final Map<String, String> fields;

    public CredentialCustom(String name, Map<String, String> fields) {
        super(name);
        this.fields = fields;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String encrypt(String key) {
        return Encrypt.encrypt(String.format("%s+%s+%s", this.getClass().getSimpleName(), name, fields.toString()), key);
    }

    @Override
    public String decrypt(String encryptedData, String key) {
        return Encrypt.decrypt(encryptedData, key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s%n", this.name));
        // Iterate over fields
        if (this.fields != null) {
            for (Map.Entry<String, String> field : fields.entrySet()) {
                sb.append(String.format("%s: %s%n", field.getKey(), field.getValue()));
            }
        }
        return sb.toString();
    }
}
