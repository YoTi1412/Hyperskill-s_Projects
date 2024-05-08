package minesweeper;

import java.util.Scanner;

public class Minesweeper {

    Minesweeper() {
        int size = 9;
        Minefield minefield = new Minefield(size);

        System.out.println("How many mines do you want on the field?");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            minefield.setAmountOfMines(scanner.nextInt());
            if (minefield.getAmountOfMines() > 0 && minefield.getAmountOfMines() < (minefield.getSize() * minefield.getSize())) {
                break;
            }
            System.out.println("Invalid amount! Try again:");
        }

        minefield.printMinefield();
        while (true) {
            minefield.turn();
            minefield.printMinefield();
        }
    }
}