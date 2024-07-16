package contacts.contact;

import contacts.contact.properties.PhoneNumber;

import java.time.LocalDateTime;

public interface Contact {
    LocalDateTime getCreationDate();

    LocalDateTime getEditDate();

    PhoneNumber getPhoneNumber();

    void setPhoneNumber(PhoneNumber phoneNumber);

    boolean hasNumber();

    String fullInfo();
}
