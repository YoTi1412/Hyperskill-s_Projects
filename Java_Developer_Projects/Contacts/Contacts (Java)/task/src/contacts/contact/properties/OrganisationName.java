package contacts.contact.properties;

import java.io.Serializable;

public class OrganisationName implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String organisationName;

    public OrganisationName(final String organisationName) {
        if (!organisationName.matches(".*\\w.*")) throw new IllegalArgumentException(organisationName);
        this.organisationName = organisationName;
    }

    @Override
    public String toString() {
        return organisationName;
    }
}
