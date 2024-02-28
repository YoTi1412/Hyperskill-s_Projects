package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int codeLength = getInput("Input the length of the secret code:", scanner);
        if (codeLength == -1) {
            return;
        }

        int symbols = getInput("Input the number of possible symbols in the code:", scanner);
        if (symbols == -1) {
            return;
        }

        if (codeLength < 1 || codeLength > 36 || symbols < codeLength || symbols > 36) {
            System.out.println("Error: Invalid input. Ensure the length is between 1 and 36, and the symbols are within the range of the code length.");
            return;
        }

        System.out.println("The secret is prepared: " + "*".repeat(codeLength) + " (" + generateSymbolRange(symbols) + ").");
        System.out.println("Okay, let's start a game!");

        String secretCode = generateSecretCode(codeLength, symbols);

        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            String userInput = scanner.nextLine();

            if (userInput.length() != codeLength) {
                System.out.println("Please enter a " + codeLength + "-character code.");
                continue;
            }

            String grade = gradeGuess(userInput, secretCode);
            System.out.println("Grade: " + grade);

            if (grade.equals(codeLength + " bulls and 0 cows")) {
                System.out.println("Congratulations! You guessed the secret code.");
                return; // Exit the game when the correct code is guessed
            }

            turn++;
        }
    }

    private static int getInput(String message, Scanner scanner) {
        System.out.println(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a valid number.");
            return -1;
        }
    }

    // Method to generate a pseudo-random secret code of a given length and symbols
    private static String generateSecretCode(int length, int symbols) {
        StringBuilder code = new StringBuilder();
        Set<Character> uniqueSymbols = new HashSet<>();
        Random random = new Random();

        while (code.length() < length) {
            int randomIndex = random.nextInt(symbols);
            char symbol = getSymbolByIndex(randomIndex);

            if (code.length() == 0 && symbol == '0') {
                continue; // Skip leading zero for the first symbol
            }

            if (uniqueSymbols.add(symbol)) {
                code.append(symbol);
            }
        }

        return code.toString();
    }

    // Method to get the symbol based on its index
    private static char getSymbolByIndex(int index) {
        if (index < 10) {
            return (char) ('0' + index);
        } else {
            return (char) ('a' + index - 10);
        }
    }

    // Method to generate the range of symbols used in the secret code
    private static String generateSymbolRange(int symbols) {
        StringBuilder range = new StringBuilder();

        range.append("0-9");

        if (symbols > 10) {
            range.append(", ");
            char startChar = 'a';
            char endChar = (char) ('a' + symbols - 11);
            range.append(startChar).append("-").append(endChar);
        }

        return range.toString();
    }


    // Method to grade the user's guess against the secret code
    private static String gradeGuess(String guess, String secretCode) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            char guessedSymbol = guess.charAt(i);
            char secretSymbol = secretCode.charAt(i);

            if (guessedSymbol == secretSymbol) {
                bulls++;
            } else if (secretCode.indexOf(guessedSymbol) != -1) {
                cows++;
            }
        }

        return bulls + " bulls and " + cows + " cows";
    }
}
