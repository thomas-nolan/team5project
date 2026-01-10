package io.github.team10.escapefromuni;

import java.io.*;
import java.util.*;

/**
 * Updated from Assessment 1
 * This class manages the player's score
 */
public class ScoreManager {

    private int score;
    private String playerName;
    private String[] files = {"scores.csv", "previous_score.csv", "player_name.txt"}; // Files to store player data
    FileManager fileManager = new FileManager();

    /**
     * Initialises player score at game start
     * Reads player name from text file
     */
    public ScoreManager() {
        this.score = 0;
        this.playerName = fileManager.readFileString(files[2]);
    }

    /**
     * Increases the player score
     * @param scoreIncrease - The amount to add to the score
     */
    public void increaseScore(int scoreIncrease)
    {
        score += scoreIncrease;
    }

    /**
     * Getter for score
     * @return - The player score
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the player score to 0
     */
    public void reset() {
        score = 0;
    }

    /**
     * Calculates the final score of the player at the end of the game
     * Factors in the time left and achievements earned
     * @param timeLeftSeconds - The player's remaining time
     * @param achievementManager - The AchievementManager
     * @return - The player's calculated final score
     */
    public int CalculateFinalScore(int timeLeftSeconds, AchievementManager achievementManager) {
        int timeScore = 50 * timeLeftSeconds;
        int achievementScore = 200 * achievementManager.getTotalAchievements();
        int finalScore = timeScore + score + achievementScore;
        return finalScore;
    }

    /**
     * Manages the player leaderboards displayed on the main menu
     * Saves current score to previous_score.csv each time
     * Saves score to leaderboards if it is in top 5.
     * Appends a "1" to the player name if it is a duplicate
     * @param playerScore - The player score
     */
    public void leaderboards(int playerScore) {

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

    /**
     * Write score to leaderboards file
     * @param name - Player Name
     * @param score - Player Score
     */
    // Used to display the score achieved. Updates each time
    public void saveCurrentScore(String name, int score) {
        Map<String, Integer> currentScore = new HashMap<>();
        currentScore.putIfAbsent(name, score);
        fileManager.writeFile(files[1], currentScore);
    }

    /**
     * Checks if a name is a duplicate
     * @param map - Hashmap of the leaderboards
     * @param playerName - Name of the current player
     * @return - True if name is duplicate, False if not
     */
    public boolean isDuplicate(Map<String,Integer> map, String playerName) {
        Set<String> names = map.keySet();
        if (names.contains(playerName)) {
            return true;
        }
        return false;
    }
}
