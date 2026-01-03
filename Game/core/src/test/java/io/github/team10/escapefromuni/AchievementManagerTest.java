package io.github.team10.escapefromuni;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementManagerTest {

    private AchievementManager achievementManager;

    @BeforeEach
    void setUp() {
        achievementManager = new AchievementManager();
    }
    
    @Test
    void contrutor_setsCorrectDefault() {
        AchievementManager achievementManager = new AchievementManager();

        assertEquals(1, achievementManager.getTotalAchievements());

        String[] achievements = achievementManager.getAchievements();
        assertEquals(1, achievements.length);
        assertEquals("No incorrect answers", achievements[0]);
    }

    @Test
    void addAchievement_increasesAchievementTotal_markAchievementasTrue() {
        AchievementManager achievementManager = new AchievementManager();

        achievementManager.addAchievement("Complete game");
        assertEquals(2, achievementManager.getTotalAchievements());

        List<String> trueAchievements = Arrays.asList(achievementManager.getAchievements());
        assertTrue(trueAchievements.contains("Complete game"));
    }

    @Test
    void removeAchievement_drecreaseAchievementTotal_markAchiementasFalse() {
        AchievementManager achievementManager = new AchievementManager();

        achievementManager.removeAchievement("No incorrect answers");
        assertEquals(0, achievementManager.getTotalAchievements());
        assertEquals(0, achievementManager.getAchievements().length);
    }

    @Test
    void reset_setsDefaultAchievements() {
        AchievementManager achievementManager = new AchievementManager();

        achievementManager.addAchievement("Complete game");
        achievementManager.addAchievement("Speedrun");
        achievementManager.removeAchievement("No incorrect answers");

        achievementManager.reset();

        assertEquals(1, achievementManager.getTotalAchievements());
        String[] achievements = achievementManager.getAchievements();
        assertEquals(1, achievements.length);
        assertEquals("No incorrect answers", achievements[0]);
    }


}
