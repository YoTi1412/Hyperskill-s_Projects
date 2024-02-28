package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;

public class Main {
    public static void main(String[] args) {
        // Check if a filename is provided as a command line argument
        if (args.length == 0) {
            System.out.println("Please provide a filename as a command line argument.");
            return;
        }

        // Retrieve the filename from command line arguments
        String filename = args[0];

        try {
            // Read the content of the file
            String text = readFile(filename);

            // Display the input text
            System.out.println("The text is:\n" + text);

            // Calculate word, sentence, and character counts
            int words = countWords(text);
            int sentences = countSentences(text);
            int characters = countCharacters(text);

            // Calculate Automated Readability Index (ARI) score
            double score = calculateARI(words, sentences, characters);

            // Get the age range based on the ARI score
            String ageRange = getAgeBracket(score);

            // Display the calculated values
            System.out.println("Words: " + words);
            System.out.println("Sentences: " + sentences);
            System.out.println("Characters: " + characters);
            System.out.println("The score is: " + Math.round(score));
            System.out.println("This text should be understood by " + ageRange + " year-olds.");

        } catch (IOException e) {
            // Handle IOException when reading the file
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Read the content of a file and return it as a string
    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line and append it to the content
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Count the number of words in a text
    private static int countWords(String text) {
        return text.split("\\s+").length;
    }

    // Count the number of sentences in a text
    private static int countSentences(String text) {
        // Use BreakIterator to find sentence boundaries
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);

        int sentenceCount = 0;
        int start = iterator.first();
        // Iterate through sentences and count non-empty sentences
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = text.substring(start, end).trim();
            if (!sentence.isEmpty()) {
                sentenceCount++;
            }
        }

        return sentenceCount;
    }

    // Count the number of characters in a text
    private static int countCharacters(String text) {
        // Remove whitespaces, newlines, and tabs and return the length
        return text.replaceAll("[\\s\\n\\t]", "").length();
    }

    // Calculate Automated Readability Index (ARI) score
    private static double calculateARI(int words, int sentences, int characters) {
        return 4.71 * (characters / (double) words) + 0.5 * (words / (double) sentences) - 21.43;
    }

    // Get the corresponding age range based on the ARI score
    private static String getAgeBracket(double score) {
        // Define age ranges
        final String[] ageRanges = {"5-6", "6-7", "7-8", "8-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-22"};
        int intScore = (int) Math.ceil(score);
        // Ensure the index is within the valid range
        if (intScore > ageRanges.length) {
            intScore = ageRanges.length;
        }
        // Return the corresponding age range
        return ageRanges[intScore - 1];
    }
}
