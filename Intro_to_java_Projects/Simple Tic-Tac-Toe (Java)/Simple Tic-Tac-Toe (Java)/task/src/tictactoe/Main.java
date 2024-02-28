package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] grid = createEmptyGrid();

        printGrid(grid);

        boolean gameFinished = false;
        char currentPlayer = 'X';

        while (!gameFinished) {
            makeMove(grid, scanner, currentPlayer);
            printGrid(grid);

            if (checkWinner(grid, currentPlayer)) {
                System.out.println(currentPlayer + " wins");
                gameFinished = true;
            } else if (!hasEmptyCells(grid)) {
                System.out.println("Draw");
                gameFinished = true;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    // Function to create an empty grid
    public static char[][] createEmptyGrid() {
        return new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    }

    // Function to print the grid
    public static void printGrid(char[][] grid) {
        System.out.println("---------");
        for (char[] row : grid) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    // Function to prompt user for a move and update the grid
    public static void makeMove(char[][] grid, Scanner scanner, char currentPlayer) {
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the coordinates: ");
            if (scanner.hasNextInt()) {
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (row < 1 || row > 3 || col < 1 || col > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (grid[row - 1][col - 1] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    grid[row - 1][col - 1] = currentPlayer;
                    validInput = true;
                }
            } else {
                System.out.println("You should enter numbers!");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    // Function to check if a player has won
    public static boolean checkWinner(char[][] grid, char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((grid[i][0] == symbol && grid[i][1] == symbol && grid[i][2] == symbol) ||
                    (grid[0][i] == symbol && grid[1][i] == symbol && grid[2][i] == symbol)) {
                return true;
            }
        }
        return (grid[0][0] == symbol && grid[1][1] == symbol && grid[2][2] == symbol) ||
                (grid[0][2] == symbol && grid[1][1] == symbol && grid[2][0] == symbol);
    }

    // Function to check if there are empty cells on the grid
    public static boolean hasEmptyCells(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == ' ') {
                    return true;
                }
            }
        }
        return false;
    }
}
