package contacts.contact.records;

import contacts.contact.OrganisationContact;
import contacts.contact.properties.Address;
import contacts.contact.properties.OrganisationName;

import java.io.Serializable;
import java.util.List;

public class OrganisationRecord extends AbstractRecord implements Serializable {
    public static final String name = "name";
    public static final String address = "address";
    private static final long serialVersionUID = 2L;

    public OrganisationRecord(OrganisationContact contact) {
        super(contact);
    }

    @Override
    protected List<String> properties() {
        return List.of(name, address);
    }

    @Override
    public String retrieveProperty(String field) {
        switch (field) {
            case name:
                return contact().getOrganizationName().toString();
            case address:
                return contact().getAddress().toString();
            default:
                return super.retrieveProperty(field);
        }
    }

    @Override
    protected OrganisationContact contact() {
        return (OrganisationContact) super.contact();
    }

    @Override
    public void updateProperty(String field, String value) {
        switch (field) {
            case name:
                contact().setOrganizationName(new OrganisationName(value));
                break;
            case address:
                contact().setAddress(new Address(value));
                break;
            default:
                super.updateProperty(field, value);
        }
    }
}