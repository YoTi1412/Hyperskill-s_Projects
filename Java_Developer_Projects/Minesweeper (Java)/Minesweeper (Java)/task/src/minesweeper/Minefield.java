package minesweeper;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Minefield {
    private final int size;
    private int amountOfMines;
    private final Cell[][] minefield;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final int[][] offsets = new int[][]{{1, -1}, {1, 0}, {1, 1}, {0, 1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

    Minefield(int size) {
        this.size = size;
        this.minefield = new Cell[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                minefield[i][j] = new Cell();
            }
        }
    }

    public void setAmountOfMines(int amountOfMines) {
        this.amountOfMines = amountOfMines;
    }

    public int getAmountOfMines() {
        return amountOfMines;
    }

    public int getSize() {
        return size;
    }

    public void printMinefield() {
        System.out.print(" |");
        for (int k = 1; k <= size; k++) {
            System.out.print(k);
        }
        System.out.println("|\n-|---------|");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < size; j++) {
                var c = minefield[i][j];
                if (c.isHasMarker() && !c.isExplored()) {
                    System.out.print("*");
                } else if (c.getAdjacentMines() > 0 && c.isExplored()) {
                    System.out.print(c.getAdjacentMines());
                } else if (c.getAdjacentMines() == 0 && c.isExplored()) {
                    System.out.print("/");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }


    public void turn() {
        System.out.println("\nSet/unset mines marks or claim a cell as free:");
        int row;
        int col;
        String freeOrMine;
        while (true) {
            col = scanner.nextInt() - 1;
            row = scanner.nextInt() - 1;
            freeOrMine = scanner.next();
            if ((Objects.equals(freeOrMine, "free") || (Objects.equals(freeOrMine, "mine"))) && col >= 0 && col < size && row >= 0 && row < size) {
                break;
            }
            System.out.println("Invalid input! Try again:");
        }
        var c = minefield[row][col];
        if (Objects.equals(freeOrMine, "mine")) {
            c.setHasMarker(!c.isHasMarker());
            if (compareMinesAndMarkers()) {
                printMinefield();
                System.out.print("Congratulations! You found all the mines!\n");
                System.exit(0);
            }
        } else if (checkForExplored()) explore(row, col);
        else {
            for (int i = 0; i < amountOfMines; ) {
                int randomRow = random.nextInt(9);
                int randomCol = random.nextInt(9);
                if (randomCol != col || randomRow != row) {
                    minefield[randomRow][randomCol].setHasMine(true);
                    i++;
                }
            }
            calculateAdjacentMines();
            explore(row, col);
        }
    }

    public boolean compareMinesAndMarkers() {
        int unexploredMarkers = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minefield[i][j].isHasMarker() && !minefield[i][j].isExplored()) {
                    unexploredMarkers++;
                }
            }
        }
        return unexploredMarkers == amountOfMines;
    }

    public void explore(int row, int col) {
        var c = minefield[row][col];
        if (c.isHasMine()) {
            System.out.println("You stepped on a mine and failed!");
            System.exit(0);
        }
        if (c.isExplored()) return;
        c.setExplored(true);
        if (c.getAdjacentMines() == 0) {
            for (int[] offset : offsets) {
                try {
                    explore((row + offset[0]), (col + offset[1]));
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }
            }
        }
    }

    public void calculateAdjacentMines() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int mineCounter = 0;
                for (int[] offset : offsets) {
                    try {
                        if (minefield[row + offset[0]][col + offset[1]].isHasMine()) {
                            mineCounter++;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignore) {
                    }
                }
                minefield[row][col].setAdjacentMines(mineCounter);
            }


        }

    }

    public boolean checkForExplored() {
        int amountExplored = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minefield[i][j].isExplored()) amountExplored++;
            }
        }
        return amountExplored > 0;
    }
}