package contacts.searchphonebook;

import contacts.contact.records.Record;
import contacts.phonebook.PhoneBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

class SimpleSearchPhoneBook implements SearchPhoneBook {
    private final PhoneBook phoneBook;

    public SimpleSearchPhoneBook(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    @Override
    public List<Record> searchByRegex(Pattern pattern) {
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < phoneBook.size(); i++) {
            Record record = phoneBook.get(i);
            if (patternMatchesAnyProperty(pattern, record)) {
                records.add(record);
            }
        }
        return records;
    }

    private boolean patternMatchesAnyProperty(Pattern pattern, Record record) {
        for (String field : record.fieldNames()) {
            boolean matches =
                    pattern
                            .matcher(record.retrieveProperty(field).toLowerCase(Locale.ROOT))
                            .find();
            if (matches) {
                return true;
            }
        }
        return false;
    }
}
