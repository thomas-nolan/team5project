package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * NEW FOR ASSESSMENT 2.
 * This class handles the set up and storing for game's Difficulty.
 * It reads and writes difficulty modifiers to a local json file
 * Allows for certain game paramters to be edited
 */
public class DifficultySetup {

  private String fileName;
  private Difficulty difficulty;

  /**
   * NEW FOR ASSESSMENT 2.
   * Defualt constructor used to the Json filename used to save difficulty modifiers.
   */
  public DifficultySetup() {
    this.fileName = "difficulty.json";
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Sets the game difficulty and writes it to the file
   * 
   * @param difficulty the desired difficulty
   */
  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
    writeDifficulty();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Writes the difficulty for the current difficulty to the file
   * Different difficulties correspond to different modifiers
   */
  private void writeDifficulty() {
    DifficultyModifiers modifiers;

    if (difficulty == Difficulty.HARD) {
      modifiers = new DifficultyModifiers(0.8f, 1.2f, 0.5f, 0.5f, true);
      // modifiers = new DifficultyModifiers(0f,2f); // TEST
    } else if (difficulty == Difficulty.EASY) {
      modifiers = new DifficultyModifiers(1.2f, 0.75f, 1.25f, 1.25f, false);
      // modifiers = new DifficultyModifiers(2f,0f); // TEST
    } else {
      modifiers = new DifficultyModifiers(1f, 1f, 1f, 1f, false);
    }

    // creates a new json file and writes the modifiers to it
    Json json = new Json();
    FileHandle file = Gdx.files.local(fileName);
    file.writeString(json.prettyPrint(modifiers), false);
    // System.out.println(file.file().getAbsolutePath());
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Reads the difficulty modifiers currenlty saved in the json file.
   * 
   * @return the object representing the current difficulty settings
   */
  public DifficultyModifiers readDifficulty() {
    Json json = new Json();
    FileHandle file = Gdx.files.local(fileName);
    DifficultyModifiers difficulty = json.fromJson(DifficultyModifiers.class, file);
    return difficulty;
  }

}
