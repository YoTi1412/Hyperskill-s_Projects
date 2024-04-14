package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        String data = "";
        String inputFile = "";
        String outputFile = "";
        String algorithm = "shift"; // Default algorithm

        // Parse command-line arguments
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                case "-in":
                    inputFile = args[i + 1];
                    break;
                case "-out":
                    outputFile = args[i + 1];
                    break;
                case "-alg":
                    algorithm = args[i + 1];
                    break;
            }
        }

        if (!inputFile.isEmpty()) {
            try {
                data = readFile(inputFile);
            } catch (FileNotFoundException e) {
                System.out.println("Error: Input file not found.");
                return;
            }
        }

        String result;
        if ("shift".equals(algorithm)) {
            if (mode.equals("enc")) {
                result = shiftCharacters(data, key);
            } else if (mode.equals("dec")) {
                result = shiftCharacters(data, -key);
            } else {
                System.out.println("Error: Invalid mode.");
                return;
            }
        } else if ("unicode".equals(algorithm)) {
            if (mode.equals("enc")) {
                result = unicodeCharacters(data, key);
            } else if (mode.equals("dec")) {
                result = unicodeCharacters(data, -key);
            } else {
                System.out.println("Error: Invalid mode.");
                return;
            }
        } else {
            System.out.println("Error: Invalid algorithm.");
            return;
        }

        if (!outputFile.isEmpty()) {
            try {
                writeFile(outputFile, result);
            } catch (IOException e) {
                System.out.println("Error: Unable to write to output file.");
            }
        } else {
            System.out.println(result);
        }
    }

    // Shifts the characters of the given text by the provided key
    private static String shiftCharacters(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                int base = Character.isUpperCase(ch) ? 'A' : 'a';
                result.append((char) (((ch - base + key) % 26 + 26) % 26 + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Shifts the Unicode value of characters by the provided key
    private static String unicodeCharacters(String text, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            result.append((char) (ch + key));
        }
        return result.toString();
    }

    // Reads data from a file
    private static String readFile(String filename) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return result.toString();
    }

    // Writes data to a file
    private static void writeFile(String filename, String data) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(data);
        writer.close();
    }
}
