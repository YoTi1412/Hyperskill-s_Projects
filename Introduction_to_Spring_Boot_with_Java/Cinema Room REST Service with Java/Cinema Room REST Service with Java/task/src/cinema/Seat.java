package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
public class Seat {
    @JsonIgnore
    private Token token;
    @JsonProperty(index = 2)
    private int row;
    @JsonProperty(index = 3)
    private int column;
    @JsonProperty(index = 4)
    private int price;
    @JsonIgnore
    private boolean reserved;
    public Seat() {
    }
    public Seat(Token token, int row, int column) {
        this.token = token;
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
        this.reserved = false;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}