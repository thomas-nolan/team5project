package io.github.team10.escapefromuni;

/**
 * NEW FOR ASSESSMENT 2.
 * This class handles the modifications each difficulty makes
 * It changes the speed of the enemy, player as well as the speed boost.
 * It also modifies the freeze event as well as gives harder questions
 */
public class DifficultyModifiers {
  public float playerSpeedModifier;
  public float enemySpeedModifier;
  public float speedBoostModifier; 
  public float freezeModifier;
  public boolean hardQuestions;
  // Add any others later

  /**
   * NEW FOR ASSESSMENT 2.
   * Intentionally empty constructor
   * Used to allow instantiation of the class without intialising values
   */
  public DifficultyModifiers() {
    // Empty constructor. Do not remove
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This modifies the below mentioned paramaters to the desired value
   * 
   * @param playerSpeedModifier The value to modify the player's speed
   * @param enemySpeedModifier The value to modify the enemy's speed
   * @param speedBoostModifier The value to modify the speedBoost event
   * @param freezeModifier The value to change the length of the freeze to
   * @param hardQuestions The boolean to change the questions to harder
   */
  public DifficultyModifiers(float playerSpeedModifier, float enemySpeedModifier, 
       float speedBoostModifier, float freezeModifier, boolean hardQuestions) {
    this.playerSpeedModifier = playerSpeedModifier;
    this.enemySpeedModifier = enemySpeedModifier;
    this.speedBoostModifier = speedBoostModifier;
    this.freezeModifier = freezeModifier;
    this.hardQuestions = hardQuestions;
  }
}
