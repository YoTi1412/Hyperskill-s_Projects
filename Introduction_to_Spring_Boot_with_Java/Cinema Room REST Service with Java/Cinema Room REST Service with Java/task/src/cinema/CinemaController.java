package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CinemaController {
    Cinema cinema = new Cinema();
    Ticket ticket = new Ticket();
    Statistics stats = new Statistics();
    @GetMapping("/seats")
    public Cinema getCinema() {
        return cinema;
    }
    @PostMapping("/purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > cinema.getRows() ||
                seat.getColumn() < 1 || seat.getColumn() > cinema.getColumns()) {
            return ResponseEntity.badRequest().body(
                    new ConcurrentHashMap<>(Map.of("error", "The number of a row or a column is out of bounds!")));
        }

        for (Seat seat1 : cinema.getSeats()) {
            if (seat.getRow() == seat1.getRow() && seat.getColumn() == seat1.getColumn() && !seat1.isReserved()) {
                seat1.setReserved(true);
                ticket.setSeat(seat1);
                ticket.setToken(seat1.getToken());
                stats.substractAvSeat(1);
                stats.addToBoughtTicket(1);
                stats.addToIncome(seat1.getPrice());
                //stats.setIncome(seat1.getPrice());
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.badRequest().body(
                new ConcurrentHashMap<>(Map.of("error", "The ticket has been already purchased!")));
    }

    @PostMapping("/return")
    public ResponseEntity refundTicket(@RequestBody Token tokenForRefund) {
        for (Seat seat : cinema.getSeats()) {
            if(Objects.equals(seat.getToken().getToken(), tokenForRefund.getToken()) && seat.isReserved())
            {
                seat.setReserved(false);
                stats.substractAvSeat(-1);
                stats.addToBoughtTicket(-1);
                stats.addToIncome(-seat.getPrice());
                return ResponseEntity.ok().body(new ConcurrentHashMap<>(Map.of("ticket", seat)));
            }
        }
        return ResponseEntity.badRequest().body(
                new ConcurrentHashMap<>(Map.of("error", "Wrong token!"))
        );
    }
    @GetMapping("/stats")
    public ResponseEntity<?> obtainStats(@RequestParam("password") Optional<String> password) {
        if (password.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(
                    new ConcurrentHashMap<>(Map.of("error", "The password is wrong!")));
        } else if (password.get().equals("super_secret")) {
            return ResponseEntity.ok().body(stats);
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(
                new ConcurrentHashMap<>(Map.of("error", "The password is wrong!")));
    }
}