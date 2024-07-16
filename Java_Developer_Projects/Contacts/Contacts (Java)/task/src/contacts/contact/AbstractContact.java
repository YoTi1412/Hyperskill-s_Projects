package contacts.contact;

import contacts.contact.properties.PhoneNumber;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

abstract class AbstractContact implements Contact, Serializable {
    protected static final String noDataStub = "[no data]";
    private static final long serialVersionUID = 1L;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime editDate = creationDate;
    private PhoneNumber phoneNumber;

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime getEditDate() {
        return editDate;
    }

    @Override
    public PhoneNumber getPhoneNumber() {
        return Objects.requireNonNull(phoneNumber);
    }

    @Override
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateEditDate();
    }

    protected void updateEditDate() {
        this.editDate = LocalDateTime.now();
    }

    @Override
    public boolean hasNumber() {
        return phoneNumber != null;
    }

    protected String orNoDataMessage(Object obj) {
        return obj == null ? noDataStub : obj.toString();
    }
}
