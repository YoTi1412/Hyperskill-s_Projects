package contacts.contact.records;

import contacts.contact.PersonContact;
import contacts.contact.properties.BirthDate;
import contacts.contact.properties.Gender;
import contacts.contact.properties.Name;
import contacts.contact.properties.Surname;

import java.io.Serializable;
import java.util.List;

public class PersonRecord extends AbstractRecord implements Serializable {
    public static final String name = "name";
    public static final String surname = "surname";
    public static final String birth = "birth";
    public static final String gender = "gender";
    private static final long serialVersionUID = 2L;

    public PersonRecord(PersonContact contact) {
        super(contact);
    }

    @Override
    protected List<String> properties() {
        return List.of(name, surname, birth, gender);
    }

    @Override
    public String retrieveProperty(String field) {
        switch (field) {
            case name:
                return contact().getName().toString();
            case surname:
                return contact().getSurname().toString();
            case birth:
                return contact().getBirthDate().toString();
            case gender:
                return contact().getGender().toString();
            default:
                return super.retrieveProperty(field);
        }
    }

    @Override
    protected PersonContact contact() {
        return (PersonContact) super.contact();
    }

    @Override
    public void updateProperty(String field, String value) {
        switch (field) {
            case name:
                contact().setName(new Name(value));
                break;
            case surname:
                contact().setSurname(new Surname(value));
                break;
            case birth:
                contact().setBirthDate(new BirthDate(value));
                break;
            case gender:
                contact().setGender(new Gender(value));
                break;
            default:
                super.updateProperty(field, value);
        }
    }
}
