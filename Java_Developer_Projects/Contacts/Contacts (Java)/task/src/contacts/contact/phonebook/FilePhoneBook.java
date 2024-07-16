package contacts.phonebook;

import contacts.contact.records.Record;
import contacts.exceptions.LoadPhoneBookException;
import contacts.exceptions.UploadPhoneBookException;

import java.io.*;

public class FilePhoneBook implements PhoneBook {
    private final File file;
    private PhoneBook phoneBook;

    private FilePhoneBook(File file) {
        this.file = file;
    }

    public static PhoneBook createAndLoad(File file) {
        FilePhoneBook filePhoneBook = new FilePhoneBook(file);
        filePhoneBook.loadPhoneBook();
        return filePhoneBook;
    }

    private void loadPhoneBook() {
        try {
            tryLoadPhoneBook();
        } catch (EOFException e) {
            phoneBook = new SimplePhoneBook();
        } catch (IOException | ClassNotFoundException e) {
            throw new LoadPhoneBookException(e);
        }
    }

    private void tryLoadPhoneBook() throws IOException, ClassNotFoundException {
        ObjectInputStream ois =
                new ObjectInputStream(
                        new BufferedInputStream(
                                new FileInputStream(this.file)));
        try (ois) {
            phoneBook = (PhoneBook) ois.readObject();
        }
    }

    public static PhoneBook createEmpty(File file) {
        FilePhoneBook filePhoneBook = new FilePhoneBook(file);
        filePhoneBook.phoneBook = new SimplePhoneBook();
        return filePhoneBook;
    }

    @Override
    public void add(Record contact) {
        phoneBook.add(contact);
        uploadPhoneBook();
    }

    private void uploadPhoneBook() {
        try {
            tryUploadPhoneBook();
        } catch (IOException e) {
            throw new UploadPhoneBookException(e);
        }
    }

    private void tryUploadPhoneBook() throws IOException {
        ObjectOutputStream oos =
                new ObjectOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(this.file)));
        try (oos) {
            oos.writeObject(phoneBook);
        }
    }

    @Override
    public void removeAt(int index) {
        phoneBook.removeAt(index);
        uploadPhoneBook();
    }

    @Override
    public Record get(int index) {
        return phoneBook.get(index);
    }

    @Override
    public int size() {
        return phoneBook.size();
    }

    @Override
    public boolean isEmpty() {
        return phoneBook.isEmpty();
    }

    @Override
    public String toString() {
        return phoneBook.toString();
    }
}
