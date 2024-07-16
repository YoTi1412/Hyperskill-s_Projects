package contacts.contact.records;

import contacts.contact.Contact;
import contacts.contact.properties.PhoneNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractRecord implements Record, Serializable {
    private static final long serialVersionUID = 2L;
    public static String number = "number";
    private final Contact contact;

    public AbstractRecord(Contact contact) {
        this.contact = contact;
    }

    protected Contact contact() {
        return contact;
    }

    @Override
    public List<String> fieldNames() {
        List<String> properties = new ArrayList<>(properties());
        properties.add(number);
        return properties;
    }

    abstract protected List<String> properties();

    @Override
    public String retrieveProperty(String field) {
        if (number.equals(field)) {
            return contact.getPhoneNumber().toString();
        }
        throw new IllegalArgumentException(field);
    }

    @Override
    public void updateProperty(String field, String value) {
        if (number.equals(field)) {
            contact.setPhoneNumber(new PhoneNumber(value));
        } else {
            throw new IllegalArgumentException(field);
        }
    }

    @Override
    public String fullInfo() {
        return contact.fullInfo();
    }

    @Override
    public String toString() {
        return contact.toString();
    }
}
