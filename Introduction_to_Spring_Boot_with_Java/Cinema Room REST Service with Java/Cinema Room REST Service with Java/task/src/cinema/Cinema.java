package cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cinema {
    private int rows = 9;
    private int columns = 9;
    private List<Seat> seats = new ArrayList<>();

    {
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.columns; j++) {
                this.seats.add(new Seat(new Token(UUID.randomUUID()), i, j));
            }
        }
    }
    public Cinema() {
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public List<Seat> getSeats() {
        return seats;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}