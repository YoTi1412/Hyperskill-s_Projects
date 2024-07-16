package contacts.contact.properties;

import java.io.Serializable;

public class Name implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;

    public Name(final String name) {
        if (!name.matches(".*\\w.*")) throw new IllegalArgumentException(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
