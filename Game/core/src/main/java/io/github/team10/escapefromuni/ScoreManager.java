package io.github.team10.escapefromuni;

import java.io.*;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreManager {

    private int score;
    private String playerName;
    private String[] files = {"scores.csv", "previous_score.csv", "player_name.txt"};
    FileManager fileManager = new FileManager();

    public ScoreManager() {
        this.score = 0;
        this.playerName = fileManager.readFileString(files[2]);
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
        return finalScore;
    }

    public void leaderboards(int playerScore) throws IOException {

        Map<String, Integer> scores = fileManager.readFile(files[0]);
        this.playerName = fileManager.readFileString(files[2]);

        saveCurrentScore(playerName, playerScore);

        if (!isDuplicate(scores,playerName)) {
            scores.put(playerName, playerScore);
        }
        else {
            scores.put(playerName + "1" ,playerScore);
        }
        scores = fileManager.sortMap(scores);

        fileManager.writeFile(files[0], scores);
    }

    // Used to display the score achieved. Updates each time
    public void saveCurrentScore(String name, int score) {
        Map<String, Integer> currentScore = new HashMap<>();
        currentScore.putIfAbsent(name, score);
        fileManager.writeFile(files[1], currentScore);
    }

    // IMPROVE LATER
    public boolean isDuplicate(Map<String,Integer> map, String playerName) {
        Set<String> names = map.keySet();
        if (names.contains(playerName)) {
            return true;
        }
        return false;
    }
}
