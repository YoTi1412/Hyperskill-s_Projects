package contacts.contact;


import contacts.contact.properties.Address;
import contacts.contact.properties.OrganisationName;

import java.io.Serializable;
import java.util.Optional;

public class OrganisationContact extends AbstractContact implements Serializable {
    private static final long serialVersionUID = 1L;
    private OrganisationName organizationName;
    private Address address;

    public OrganisationContact(OrganisationName organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        return getOrganizationName().toString();
    }

    public OrganisationName getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(OrganisationName organizationName) {
        this.organizationName = organizationName;
        updateEditDate();
    }

    @Override
    public String fullInfo() {
        return "Organization name: " + getOrganizationName() +
                "\nAddress: " + orNoDataMessage(address) +
                "\nNumber: " + (hasNumber() ? getPhoneNumber() : noDataStub) +
                "\nTime created: " + getCreationDate() +
                "\nTime last edit: " + getEditDate();
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
        updateEditDate();
    }
}
