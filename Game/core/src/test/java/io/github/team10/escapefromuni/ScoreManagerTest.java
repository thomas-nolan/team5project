package io.github.team10.escapefromuni;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NEW FOR ASSESSMENT 2
 * This class tests the functionality of the ScoreManager class
 */
public class ScoreManagerTest {

    private ScoreManager scoreManager;

    /**
     * Creates a fresh ScoreManager instance before each test
     */
    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
    }

    /**
     * Tests that calculate final score actually returns the
     * correct final score
     */
    @Test
    void calculateFinalScore_calculatesCorrectScore() {
        AchievementManager achievementManager = new AchievementManager();

        scoreManager.increaseScore(10);
        achievementManager.addAchievement("Complete game");
        achievementManager.addAchievement("Speedrun");

        int achievementScore = achievementManager.getTotalAchievements();
        int correctScore = (50 * 30) + (200 * achievementScore) + scoreManager.getScore();

        assertEquals(correctScore, scoreManager.CalculateFinalScore(30, achievementManager));
    }

    /**
     * Tests that increases Score actually increases the score
     */
    @Test
    void increaseScore_addsToScore() {
        scoreManager.increaseScore(10);
        assertEquals(10, scoreManager.getScore());

        scoreManager.increaseScore(20);
        assertEquals(30, scoreManager.getScore());
    }

    /**
     * Tests that reset sets the players score to 0
     */
    @Test
    void reset_setsScoreToZero() {
        scoreManager.increaseScore(10);
        scoreManager.reset();

        assertEquals(0, scoreManager.getScore());
    }

    /**
     * Tests is Duplicate returns true if and only if there is a duplicate
     */
    @Test
    void isDuplicate_returnsTrueIfDuplicate() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Tom", 67);

        assertTrue(scoreManager.isDuplicate(map, "Tom"));
        assertFalse(scoreManager.isDuplicate(map, "Will"));
    }


}
