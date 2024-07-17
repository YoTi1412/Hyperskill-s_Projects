package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Ticket {
    @JsonUnwrapped
    private Token token;
    @JsonProperty(value = "ticket")
    private Seat seat;

    public Ticket() {
    }

    public Ticket(Token token, Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    public Token getToken() {
        return token;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}