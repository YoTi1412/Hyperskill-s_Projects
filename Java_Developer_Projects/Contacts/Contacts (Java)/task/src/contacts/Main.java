package contacts;

import contacts.contact.OrganisationContact;
import contacts.contact.PersonContact;
import contacts.contact.properties.*;
import contacts.contact.records.OrganisationRecord;
import contacts.contact.records.PersonRecord;
import contacts.contact.records.Record;
import contacts.factory.FilePhoneBookFactory;
import contacts.factory.PhoneBookFactory;
import contacts.factory.SimplePhoneBookFactory;
import contacts.phonebook.PhoneBook;
import contacts.searchphonebook.SearchPhoneBook;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private final PhoneBook phoneBook;
    private boolean continued = true;
    private final Map<String, Runnable> commands =
            Map.of(
                    "add", this::actionAdd,
                    "list", this::actionList,
                    "search", this::actionSearch,
                    "count", this::actionCount,
                    "exit", this::actionExit);
    private final String joinedCommands = String.join(", ", commands.keySet());

    public Main(PhoneBookFactory factory) {
        this.phoneBook = factory.createPhoneBook();
    }

    public static void main(String[] args) {
        Optional<String> fileNameOptional = new Args(args).get("open");
        PhoneBookFactory factory =
                fileNameOptional
                        .<PhoneBookFactory>map(s -> (PhoneBookFactory) new FilePhoneBookFactory(new File(s)))
                        .orElseGet(() -> (PhoneBookFactory) new SimplePhoneBookFactory());
        new Main(factory).run();
    }

    private void run() {
        while (continued) {
            String action = read("[menu] Enter action (" + joinedCommands + "): ");
            performAction(action);
        }
    }

    private String read(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private void performAction(String action) {
        Runnable command = commands.get(action);
        if (command == null) {
            System.out.println("Unknown action!\n");
        } else {
            command.run();
        }
    }

    private void actionList() {
        printPhoneBook();
        while (true) {
            String input = read("[list] Enter action ([number], back): ");
            if (input.equals("back")) {
                return;
            } else if (input.matches("\\d+")) {
                int n = Integer.parseInt(input);
                if (n > 0 && n <= phoneBook.size()) {
                    Record record = phoneBook.get(n - 1);
                    System.out.println(record.fullInfo() + "\n");
                    showRecordMenu(record);
                    return;
                }
            }
        }
    }

    private void printPhoneBook() {
        System.out.print(phoneBook);
        System.out.println();
    }

    private void showRecordMenu(Record record) {
        while (true) {
            String input = read("[record] Enter action (edit, delete, menu): ");
            switch (input) {
                case "edit":
                    showEditMenu(record);
                    break;
                case "delete":
                    deleteRecord(record);
                    return;
                case "menu":
                    System.out.println();
                    return;
            }
        }
    }

    private void showEditMenu(Record record) {
        String input = readFieldName(record);
        String value = readFieldValue(input);
        record.updateProperty(input, value);
        System.out.println("Saved");
        System.out.println(record.fullInfo());
        System.out.println();
    }

    private void deleteRecord(Record record) {
        for (int i = 0; i < phoneBook.size(); i++) {
            if (phoneBook.get(i).equals(record)) {
                phoneBook.removeAt(i);
                return;
            }
        }
    }

    private String readFieldName(Record record) {
        String field;
        List<String> elements;
        do {
            elements = record.fieldNames();
            String propertiesString = String.join(", ", elements);
            System.out.printf("Select a field (%s): ", propertiesString);
            field = scanner.nextLine();
        } while (!elements.contains(field));
        return field;
    }

    private String readFieldValue(String field) {
        System.out.print("Enter " + field + ": ");
        return scanner.nextLine();
    }

    private void actionSearch() {
        List<Record> records = searchQuery();
        showSearchMenu(records);
    }

    private List<Record> searchQuery() {
        String query = read("Enter search query: ");
        List<Record> records =
                SearchPhoneBook.of(phoneBook)
                        .searchByRegex(Pattern.compile(query));
        System.out.printf("Found %d results:%n", records.size());
        printRecordsList(records);
        return records;
    }

    private void showSearchMenu(List<Record> records) {
        while (true) {
            String input = read("[search] Enter action ([number], back, again): ");
            if (input.equals("again")) {
                records = searchQuery();
            } else if (input.equals("back")) {
                return;
            } else if (input.matches("\\d+")) {
                int n = Integer.parseInt(input);
                if (n > 0 && n <= records.size()) {
                    Record record = records.get(n - 1);
                    System.out.println(record.fullInfo() + "\n");
                    showRecordMenu(record);
                    return;
                }
            }
        }
    }

    private void printRecordsList(List<Record> records) {
        for (int i = 0; i < records.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, records.get(i).toString());
        }
        System.out.println();
    }

    private void actionExit() {
        continued = false;
    }

    private void actionAdd() {
        Record record = readRecord();
        phoneBook.add(record);
        System.out.println("The record added.\n");
    }

    private void actionRemove() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to remove!\n");
            return;
        }
        printPhoneBook();
        int contactIndex = readRecordIndex("Select a record: ");
        phoneBook.removeAt(contactIndex);
        System.out.println("The record removed!\n");
    }

    private int readRecordIndex(String message) {
        int index;
        do {
            System.out.print(message);
            index = Integer.parseInt(scanner.nextLine());
        } while (index <= 0 || index > phoneBook.size());
        return index - 1;
    }

    private void actionEdit() {
        if (phoneBook.isEmpty()) {
            System.out.println("No records to edit!\n");
            return;
        }
        printPhoneBook();
        int recordIndex = readRecordIndex("Select a record: ");
        Record record = phoneBook.get(recordIndex);
        String field = readFieldName(record);
        String newValue = readFieldValue(field);
        record.updateProperty(field, newValue);
        System.out.println("The record updated!\n");
    }

    private void actionCount() {
        System.out.println("The Phone Book has " + phoneBook.size() + " records.\n");
    }

    private void actionInfo() {
        printPhoneBook();
        int index = readRecordIndex("Enter index to show info: ");
        System.out.println(phoneBook.get(index).fullInfo() + "\n");
    }

    private Record readRecord() {
        while (true) {
            System.out.print("Enter the type (person, organization): ");
            String type = scanner.nextLine();
            switch (type) {
                case "person":
                    return readPersonRecord();
                case "organization":
                    return readOrganisationRecord();
                default:
                    System.out.println("Bad input!");
            }
        }
    }

    private PersonRecord readPersonRecord() {
        Name name = readRequire("name", Name::new);
        Surname surname = readRequire("surname", Surname::new);
        Optional<BirthDate> birthDateOptional = readOptional("birth date", BirthDate::new);
        Optional<Gender> genderOptional = readOptional("gender (M, F)", Gender::new);
        PhoneNumber number = readRequire("phone number", PhoneNumber::new);

        PersonContact contact = new PersonContact(name, surname);
        birthDateOptional.ifPresent(contact::setBirthDate);
        genderOptional.ifPresent(contact::setGender);
        contact.setPhoneNumber(number);

        return new PersonRecord(contact);
    }

    private OrganisationRecord readOrganisationRecord() {
        OrganisationName organizationName = readRequire("organization name", OrganisationName::new);
        Optional<Address> address = readOptional("address", Address::new);
        Optional<PhoneNumber> number = readOptional("number", PhoneNumber::new);

        OrganisationContact contact = new OrganisationContact(organizationName);
        address.ifPresent(contact::setAddress);
        number.ifPresent(contact::setPhoneNumber);

        return new OrganisationRecord(contact);
    }

    private <T> T readRequire(String messageSuffix, Function<String, T> function) {
        while (true) {
            Optional<T> input = readOptional(messageSuffix, function);
            if (input.isPresent()) {
                return input.get();
            }
        }
    }

    private <T> Optional<T> readOptional(String messageSuffix, Function<String, T> function) {
        try {
            System.out.print("Enter the " + messageSuffix + ": ");
            return Optional.of(
                    function.apply(
                            scanner.nextLine()));
        } catch (IllegalArgumentException ignore) {
            System.out.println("Bad " + messageSuffix + "!");
            return Optional.empty();
        }
    }
}
