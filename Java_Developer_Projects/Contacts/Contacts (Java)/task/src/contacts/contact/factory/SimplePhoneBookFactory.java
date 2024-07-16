package contacts.factory;

import contacts.phonebook.PhoneBook;
import contacts.phonebook.SimplePhoneBook;

public class SimplePhoneBookFactory implements PhoneBookFactory {
    @Override
    public PhoneBook createPhoneBook() {
        return new SimplePhoneBook();
    }
}
