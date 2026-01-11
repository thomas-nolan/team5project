package io.github.team10.escapefromuni;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * NEW FOR ASSESSMENT 2.
 * This class tests the functionality of the AchievementManager class
 */
public class AchievementManagerTest {

  private AchievementManager achievementManager;

  /**
   * Creates a fresh AchievementManager instance before each test.
   */
  @BeforeEach
  void setUp() {
    achievementManager = new AchievementManager();
  }

  /**
   * Tests that the constructor sets the correct default achievements.
   */
  @Test
  void contrutor_setsCorrectDefault() {
    assertEquals(1, achievementManager.getTotalAchievements());

    String[] achievements = achievementManager.getAchievements();
    assertEquals(1, achievements.length);
    assertEquals("No incorrect answers", achievements[0]);
  }

  /**
   * Tests when adding an achievement it increases the achievement total.
   * It also marks the achievement as True.
   */
  @Test
  void addAchievement_increasesAchievementTotal_markAchievementAsTrue() {
    achievementManager.addAchievement("Complete game");
    assertEquals(2, achievementManager.getTotalAchievements());

    List<String> trueAchievements = Arrays.asList(achievementManager.getAchievements());
    assertTrue(trueAchievements.contains("Complete game"));
  }

  /**
   * Tests when removing an achievement it decreases the achievement total.
   * Also tests that it marks the achievement as False
   */
  @Test
  void removeAchievement_drecreaseAchievementTotal_markAchiementAsFalse() {
    achievementManager.removeAchievement("No incorrect answers");
    assertEquals(0, achievementManager.getTotalAchievements());
    assertEquals(0, achievementManager.getAchievements().length);
  }

  /**
   * Tests that resetting the achievements restores it back to default.
   */
  @Test
  void reset_setsDefaultAchievements() {
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
