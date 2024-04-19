package asciimirror;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Request file path from the user
        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();

        // Attempt to read the file and print its content
        try {
            printFileContent(filePath);
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }

    public static void printFileContent(String filePath) throws IOException {
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists() || file.isDirectory()) {
            throw new IOException();
        }

        // Read the file content line by line and store it in a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Find the length of the longest string
        int maxLength = 0;
        for (String line : lines) {
            maxLength = Math.max(maxLength, line.length());
        }

        // Print the modified lines
        for (String line : lines) {
            StringBuilder modifiedLine = new StringBuilder(String.format("%-" + maxLength + "s", line));

            // Reverse the line and replace characters with their symmetrical ones
            StringBuilder reversedLine = new StringBuilder();
            for (int i = modifiedLine.length() - 1; i >= 0; i--) {
                char c = modifiedLine.charAt(i);
                char reversedChar;
                switch (c) {
                    case '>':
                        reversedChar = '<';
                        break;
                    case '<':
                        reversedChar = '>';
                        break;
                    case '[':
                        reversedChar = ']';
                        break;
                    case ']':
                        reversedChar = '[';
                        break;
                    case '{':
                        reversedChar = '}';
                        break;
                    case '}':
                        reversedChar = '{';
                        break;
                    case '(':
                        reversedChar = ')';
                        break;
                    case ')':
                        reversedChar = '(';
                        break;
                    case '/':
                        reversedChar = '\\';
                        break;
                    case '\\':
                        reversedChar = '/';
                        break;
                    default:
                        reversedChar = c;
                        break;
                }
                reversedLine.append(reversedChar);
            }

            System.out.println(modifiedLine + " | " + reversedLine);
        }
    }
}
