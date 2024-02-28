package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        char[][] cinemaSeats = initializeSeats(rows, seatsPerRow);

        int purchasedTickets = 0;
        int currentIncome = 0;

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printCinemaSeats(cinemaSeats);
                    break;
                case 2:
                    int[] ticketInfo = buyTicket(scanner, cinemaSeats, rows, seatsPerRow, purchasedTickets, currentIncome);
                    purchasedTickets = ticketInfo[0];
                    currentIncome = ticketInfo[1];
                    break;
                case 3:
                    displayStatistics(cinemaSeats, rows, seatsPerRow, purchasedTickets, currentIncome);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public static char[][] initializeSeats(int rows, int seatsPerRow) {
        char[][] cinemaSeats = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinemaSeats[i][j] = 'S';
            }
        }
        return cinemaSeats;
    }

    public static void printCinemaSeats(char[][] cinemaSeats) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int seat = 1; seat <= cinemaSeats[0].length; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();
        for (int i = 0; i < cinemaSeats.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinemaSeats[0].length; j++) {
                System.out.print(cinemaSeats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void displayMenu() {
        System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
        System.out.println("Choose an option:");
    }

    public static int[] buyTicket(Scanner scanner, char[][] cinemaSeats, int rows, int seatsPerRow, int purchasedTickets, int currentIncome) {
        int chosenRow;
        int chosenSeat;
        boolean validSeat;

        do {
            System.out.println("Enter a row number:");
            chosenRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            chosenSeat = scanner.nextInt();

            validSeat = isValidSeat(chosenRow, chosenSeat, rows, seatsPerRow);

            if (!validSeat) {
                System.out.println("Wrong input! Please enter valid seat coordinates.");
            } else if (cinemaSeats[chosenRow - 1][chosenSeat - 1] == 'B') {
                System.out.println("That ticket has already been purchased! Please select another seat.");
                validSeat = false;
            }
        } while (!validSeat);

        int ticketPrice = calculateTicketPrice(rows, chosenRow, seatsPerRow);
        System.out.println("Ticket price: $" + ticketPrice);

        cinemaSeats[chosenRow - 1][chosenSeat - 1] = 'B';
        purchasedTickets++;
        currentIncome += ticketPrice;

        System.out.println("Ticket purchased for seat " + chosenRow + "-" + chosenSeat + ".");

        return new int[]{purchasedTickets, currentIncome};
    }

    public static boolean isValidSeat(int chosenRow, int chosenSeat, int rows, int seatsPerRow) {
        return chosenRow >= 1 && chosenRow <= rows && chosenSeat >= 1 && chosenSeat <= seatsPerRow;
    }

    public static int calculateTicketPrice(int rows, int chosenRow, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return 10;
        } else {
            int frontHalf = rows / 2;
            return chosenRow <= frontHalf ? 10 : 8;
        }
    }

    public static void displayStatistics(char[][] cinemaSeats, int rows, int seatsPerRow, int purchasedTickets, int currentIncome) {
        int totalSeats = rows * seatsPerRow;
        double percentage = ((double) purchasedTickets / totalSeats) * 100;

        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + calculateTotalIncome(rows, seatsPerRow));
    }

    public static int calculateTotalIncome(int rows, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            return (frontHalf * seatsPerRow * 10) + (backHalf * seatsPerRow * 8);
        }
    }
}