package PALM;

import java.io.Serializable;

public abstract class Item implements AES.Encryptable, Serializable {
    protected String name;

    protected Item(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
