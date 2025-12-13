package io.github.team10.escapefromuni;

import java.io.*;

import java.util.*;
import java.util.stream.Collectors;


public class ScoreManager {

    private int score;
    private String playerName;
    private String[] files = {"scores.csv", "previous_score.csv"};

    public ScoreManager() {
        this.score = 0;
        this.playerName = "Harry"; // Change later
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
        //leaderboards(finalScore);
        return finalScore;
    }

    public void leaderboards(int playerScore) throws IOException {

        FileManager fileManager = new FileManager();

        Map<String, Integer> scores = fileManager.readFile(files[0]);

        if (!isDuplicate(scores,playerName)) {
            scores.put(playerName, playerScore);
        }
        else {
            scores.put(playerName + "1" ,playerScore);
        }
        scores = sortMap(scores);

        fileManager.writeFile(files[0], scores);
    }

    // IMPROVE LATER
    public boolean isDuplicate(Map<String,Integer> map, String playerName) {
        Set<String> names = map.keySet();
        if (names.contains(playerName)) {
            return true;
        }
        return false;
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
