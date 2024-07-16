package contacts.contact;


import contacts.contact.properties.BirthDate;
import contacts.contact.properties.Gender;
import contacts.contact.properties.Name;
import contacts.contact.properties.Surname;

import java.io.Serializable;
import java.util.Optional;

public class PersonContact extends AbstractContact implements Serializable {
    private static final long serialVersionUID = 1L;
    private Name name;
    private Surname surname;
    private BirthDate birthDate;
    private Gender gender;

    public PersonContact(Name name, Surname surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
        updateEditDate();
    }

    public Surname getSurname() {
        return surname;
    }

    public void setSurname(Surname surname) {
        this.surname = surname;
        updateEditDate();
    }

    @Override
    public String fullInfo() {
        return "Name: " + getName() +
                "\nSurname: " + getSurname() +
                "\nBirth date: " + orNoDataMessage(birthDate) +
                "\nGender: " + orNoDataMessage(gender) +
                "\nNumber: " + (hasNumber() ? getPhoneNumber() : noDataStub) +
                "\nTime created: " + getCreationDate() +
                "\nTime last edit: " + getEditDate();
    }

    public Optional<BirthDate> getBirthDate() {
        return Optional.ofNullable(birthDate);
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
        updateEditDate();
    }

    public Optional<Gender> getGender() {
        return Optional.ofNullable(gender);
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        updateEditDate();
    }
}
