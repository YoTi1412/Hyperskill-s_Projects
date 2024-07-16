package contacts.contact.properties;

import java.io.Serializable;

public class Surname implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String surname;

    public Surname(final String surname) {
        if (!surname.matches(".*\\w.*")) throw new IllegalArgumentException(surname);
        this.surname = surname;
    }

    @Override
    public String toString() {
        return surname;
    }
}
