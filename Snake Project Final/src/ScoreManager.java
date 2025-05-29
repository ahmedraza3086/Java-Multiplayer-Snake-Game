import java.io.*;
import java.util.*;

public class
ScoreManager {
    private static final String FILE_NAME = "scores.txt";

    public static void saveScore(String name, int score) {
        List<String> scores = loadScores();
        scores.add(name + ": " + score);
        scores.sort((s1, s2) -> {
            int score1 = Integer.parseInt(s1.split(": ")[1]);
            int score2 = Integer.parseInt(s2.split(": ")[1]);
            return Integer.compare(score2, score1);  // Sort in descending order
        });

        // Keep top 5 scores
        if (scores.size() > 5) {
            scores = scores.subList(0, 5);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String scoree : scores) {
                writer.write(scoree);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
