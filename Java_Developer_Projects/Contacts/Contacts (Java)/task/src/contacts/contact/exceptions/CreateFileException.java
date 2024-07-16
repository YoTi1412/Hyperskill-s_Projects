package contacts.exceptions;

public class CreateFileException extends RuntimeException {
    public CreateFileException(Throwable cause) {
        super(cause);
    }

    public CreateFileException(String msg) {
        super(msg);
    }
}
