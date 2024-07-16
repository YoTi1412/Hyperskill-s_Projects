package contacts.searchphonebook;

import contacts.contact.records.Record;
import contacts.phonebook.PhoneBook;

import java.util.List;
import java.util.regex.Pattern;

public interface SearchPhoneBook {
    static SearchPhoneBook of(PhoneBook phoneBook) {
        return new SimpleSearchPhoneBook(phoneBook);
    }

    List<Record> searchByRegex(Pattern pattern);
}
