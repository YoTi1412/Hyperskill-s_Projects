package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;


public class Token {

    private UUID token;

    public Token() {
    }

    public Token(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}