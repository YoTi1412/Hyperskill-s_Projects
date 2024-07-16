package contacts.contact.properties;

import java.io.Serializable;
import java.util.regex.Pattern;

public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Pattern phoneNumberPattern =
            Pattern.compile("\\+?(\\w+[- ](\\(\\w{2,})\\)|\\w+|(\\(\\w+)\\))([- ]\\w{2,})*");

    private final String number;

    public PhoneNumber(String number) {
        if (!isValidPhoneNumber(number)) throw new IllegalArgumentException(number);
        this.number = number;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumberPattern.matcher(phoneNumber).matches();
    }

    @Override
    public String toString() {
        return number;
    }
}
