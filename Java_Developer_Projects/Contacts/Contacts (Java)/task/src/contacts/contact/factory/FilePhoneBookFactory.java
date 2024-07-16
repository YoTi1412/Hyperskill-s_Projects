package contacts.factory;

import contacts.exceptions.CreateFileException;
import contacts.phonebook.FilePhoneBook;
import contacts.phonebook.PhoneBook;

import java.io.File;
import java.io.IOException;

public class FilePhoneBookFactory implements PhoneBookFactory {
    private final File file;

    public FilePhoneBookFactory(File file) {
        this.file = file;
    }

    @Override
    public PhoneBook createPhoneBook() {
        if (!file.isFile()) {
            try {
                if (!file.createNewFile()) {
                    throw new CreateFileException("File not created!");
                }
                return FilePhoneBook.createEmpty(file);
            } catch (IOException e) {
                throw new CreateFileException(e);
            }
        }
        return FilePhoneBook.createAndLoad(file);
    }
}
