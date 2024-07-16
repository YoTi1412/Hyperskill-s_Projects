package contacts.contact.properties;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String address;

    public Address(final String address) {
        if (!address.matches(".*\\w.*")) throw new IllegalArgumentException(address);
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
