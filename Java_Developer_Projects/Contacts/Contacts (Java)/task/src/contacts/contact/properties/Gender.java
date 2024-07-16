package contacts.contact.properties;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class Gender implements Serializable {
    public static final List<String> genderList = List.of("M", "F");
    private static final long serialVersionUID = 1L;
    private final String gender;

    public Gender(String gender) {
        gender = gender.toUpperCase(Locale.ROOT);
        if (!genderList.contains(gender)) throw new IllegalArgumentException(gender);
        this.gender = gender;
    }

    @Override
    public String toString() {
        return gender;
    }
}
