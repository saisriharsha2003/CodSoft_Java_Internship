import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class word_counter {

    // Define common stop words to be ignored
    private static final Set<String> stopWords = new HashSet<>(
            Arrays.asList("a", "an", "the", "in", "on", "of", "and", "is", "are", "was", "were"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText = "";

        // Prompt the user to enter text or provide a file
        System.out.println("Enter '1' to input text or '2' to provide a file:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        switch (choice) {
            case 1:
                // Input text directly
                System.out.println("Enter the text:");
                inputText = scanner.nextLine();
                break;
            case 2:
                // Provide a file and read its content
                System.out.println("Enter the file path:");
                String filePath = scanner.nextLine();
                try {
                    inputText = readFromFile(filePath);
                } catch (IOException e) {
                    System.out.println("Error reading the file. Please make sure the file exists and try again.");
                    System.exit(1);
                }
                break;
            default:
                System.out.println("Invalid choice. Exiting the program.");
                System.exit(1);
        }

        // Word counting and statistics
        Map<String, Integer> wordFrequency = new HashMap<>();
        String[] words = inputText.split("[\\s.,?!:;()\"']+");

        int totalWords = 0;
        for (String word : words) {
            if (!word.isEmpty()) {
                totalWords++;
                String cleanedWord = word.toLowerCase();
                if (!stopWords.contains(cleanedWord)) {
                    wordFrequency.put(cleanedWord, wordFrequency.getOrDefault(cleanedWord, 0) + 1);
                }
            }
        }

        // Display results
        System.out.println("Total words: " + totalWords);
        System.out.println("Unique words: " + wordFrequency.size());
        System.out.println("Word frequency:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static String readFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
