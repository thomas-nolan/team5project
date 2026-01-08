package io.github.team10.escapefromuni;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {

    private ScoreManager scoreManager;

    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
    }

    @Test
    void calculateFinalScore_calculatesCorrectScore() throws Exception {
        AchievementManager achievementManager = new AchievementManager();

        scoreManager.increaseScore(10);
        achievementManager.addAchievement("Complete game");
        achievementManager.addAchievement("Speedrun");

        int achievementScore = achievementManager.getTotalAchievements();
        int correctScore = (50 * 30) + (200 * achievementScore) + scoreManager.getScore();

        assertEquals(correctScore, scoreManager.CalculateFinalScore(30, achievementManager));
    }
    
    @Test
    void increaseScore_addsToScore() {
        scoreManager.increaseScore(10);
        assertEquals(10, scoreManager.getScore());

        scoreManager.increaseScore(20);
        assertEquals(30, scoreManager.getScore());
    }

    @Test
    void reset_setsScoreToZero() {
        scoreManager.increaseScore(10);
        scoreManager.reset();

        assertEquals(0, scoreManager.getScore());
    }

    @Test
    void isDuplicate_returnsTrueIfDuplicate() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Tom", 69);

        assertTrue(scoreManager.isDuplicate(map, "Tom"));
        assertFalse(scoreManager.isDuplicate(map, "Will"));
    }


}
