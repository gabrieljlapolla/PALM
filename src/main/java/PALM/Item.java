package PALM;

import java.io.Serializable;

public abstract class Item implements Encrypt.Encryptable, Serializable {
    protected String name;

    protected Item(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
