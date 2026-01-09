package io.github.team10.escapefromuni;

import java.util.HashMap;

/**
 * NEW FOR ASSESSMENT 2
 * This is a class which manages the achievements in the game.
 */
public class AchievementManager {

  HashMap<String, Boolean> achievements = new HashMap<String, Boolean>();

  /**
   * NEW FOR ASSESSMENT 2
   * This constructor sets the achievements which you can get and sets them to false.
   */
  public AchievementManager() {

    // ones that are commented out arent done yet bc 
    //  they arent possible yet
    // 'No incorrect answers' is true by default, you lose the achievement
    //  if you fail a question

    achievements.put("Complete game", false);
    achievements.put("Speedrun", false);
    achievements.put("All positive events", false);
    achievements.put("All negative events", false);
    achievements.put("All hidden events", false);
    achievements.put("Find Long Boi", false);
    achievements.put("No incorrect answers", true);

  }
  
  /**
   * NEW FOR ASSESSMENT 2
   * This method adds achievements to the game.
   * 
   * @param achievementName The name of the achievement you wish to add
   */
  public void addAchievement(String achievementName) {
        
    achievements.put(achievementName, true);

  }

  /**
   * NEW FOR ASSESSMENT 2
   * This method is used to mark achievements as not completed.
   * 
   * @param achievementName The name of the achievement you wish to remove
   */
  public void removeAchievement(String achievementName) {
        
    achievements.put(achievementName, false);

  }

  /**
   * NEW FOR ASSESSMENT 2
   * Returns the number of completed achievements.
   * 
   * @return The number of achievements
   */
  public int getTotalAchievements() {

    int total = 0;

    for (Boolean i : achievements.values()) {
      if (i) {
        total += 1;
      }
    }

    return total;
  }

  /**
   * NEW FOR ASSESSMENT 2
   * Gets all the achievement the player has completed.
   * 
   * @return An array containing the completed achievements
   */
  public String[] getAchievements() {

    String[] currentAchievements = new String[getTotalAchievements()];

    int index = 0;
    for (String key : achievements.keySet()) {
      if (achievements.get(key)) {    
        currentAchievements[index] = key;
        index += 1;

      }
    }

    return currentAchievements;

  }

  /**
   * NEW FOR ASSESSMENT 2
   * This resets all the achievements marking them as incomplete.
   */
  public void reset() {

    achievements.put("Complete game", false);
    achievements.put("Speedrun", false);
    achievements.put("All positive events", false);
    achievements.put("All negative events", false);
    achievements.put("All hidden events", false);
    achievements.put("Find Long Boi", false);
    achievements.put("No incorrect answers", true);

  }

}
