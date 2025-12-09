package io.github.team10.escapefromuni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ScoreManager {

    private int score;
    public ScoreManager() {
        this.score = 0;
    }

    public void increaseScore(int scoreIncrease)
    {
        score += scoreIncrease;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }

    public int CalculateFinalScore(int timeLeftSeconds, AchievementManager achievementManager) throws IOException {
        int timeScore = 50 * timeLeftSeconds;
        int achievementScore = 200 * achievementManager.getTotalAchievements();
        int finalScore = timeScore + score + achievementScore;
        leaderboards(finalScore);
        return finalScore;
    }

    public void leaderboards(int playerScore) throws IOException {
        String[] files = {};
        Map<String, Integer> scores = new HashMap<String, Integer>();

        try (BufferedReader reader = new BufferedReader(new FileReader("scores.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                scores.put(parts[0], Integer.valueOf(parts[1]));
                test(scores); // TEST
            }
        } catch (IOException e) {
            System.out.println("READ ERROR");
            e.printStackTrace();
        }

        scores.put("Player", playerScore);
        scores = sortMap(scores);
        //test(scores); // TEST


        try (PrintWriter writer = new PrintWriter(new FileWriter("scores.csv"))) {
            Set<String> keys = scores.keySet();
            int count = 0;
            for (String key : keys) {
                String line = key + "," + scores.get(key);
                writer.println(line);
                count++;
                if (count == 5) { // Reads the first 5
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("WRITE ERROR");
            e.printStackTrace();
        }
    }

    Map<String, Integer> sortMap(Map<String,Integer> map) {
        Map<String, Integer> sortedMap = map.entrySet()
            .stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()) // Descending order
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));
        return sortedMap;
    }


    // FOR TESTING DELETE LATER
    void test(Map<String,Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
