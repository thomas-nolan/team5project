package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * NEW FOR ASSESSMENT 2.
 * A class responsible for managing the UI of the game.
 * Loads and opens new menus and allows the player to pause and exit the game
 */
public class UIController {
  private final EscapeGame game;
  private GameController gameController;

  /**
   * Constructor for the UIController.
   * 
   * @param game the main LibGDX game instance
   * @param gameController the controller in charge of game logic
   */
  public UIController(EscapeGame game, GameController gameController) {
    this.game = game;
    this.gameController = gameController;
  }

  /**
   * Setter method for GameController.
   * 
   *@param gameController the controller in charge of game logic
   */
  public void setGameController(GameController gameController) {
    this.gameController = gameController;
  }

  /**
   *  Displays the main menu and plays the related music.
   */
  public void showMainMenu() {
    AudioManager.getInstance().playMenuMusic();
    game.setScreen(new MainMenu(game, this));
  }

  /**
   *  Loads the main game screen and the appropriate music.
   */
  public void startGame() {
    AudioManager.getInstance().playClickSound();

    if (gameController != null) {
      gameController.resetGame();
    }

    game.setScreen(new GameScreen(game, this, gameController));
  }

  /**
   * Displays the tutorial menu (can be found in the main menu)
   * Plays an associated click sound when clicking a button.
   */
  public void showTutorial() {
    AudioManager.getInstance().playClickSound();
    game.setScreen(new TutorialPage(game, this));
  }

  /**
   * Shows the settings menu (can be found from the main menu).
   * Plays click sound
   */
  public void showSettings(Screen previousScreen) {
    AudioManager.getInstance().playClickSound();
    game.setScreen(new SettingsPage(game, this, previousScreen));
  }

  /**
   * Opens the pause menu.
   * 
   * @param gameScreen - the main screen for the game
   * @param pausedTime - The time remaining when the game was paused
   */
  public void pauseGame(GameScreen gameScreen, int pausedTime) {
    AudioManager.getInstance().playMenuMusic();
    game.setScreen(new PauseMenu(game, this, gameScreen, pausedTime));
  }

  /**
   * Resumes the game if it has been paused.
   * 
   * @param gameScreen - the main screen for the game
   */
  public void resumeGame(GameScreen gameScreen) {
    AudioManager.getInstance().playClickSound();
    game.setScreen(gameScreen);
  }

  /**
   * Displays the appropriate game over screen (win or lose screen).
   * 
   * @param win - True if player won, False if not.
   * @param timer - The player's remaining time
   * @param scores - The ScoreManager
   */
  public void showGameOver(boolean win, Timer timer, ScoreManager scores) {
    AudioManager.getInstance().playMenuMusic();
    game.setScreen(new GameOverScreen(game, this, win, timer, scores, 
        gameController.getAchievementManager(), gameController.getEventSystem()));
  }

  /**
   * Exits the game fully.
   */
  public void exitGame() {
    AudioManager.getInstance().playClickSound();
    Gdx.app.exit();
  }

  /**
   * Returns the user to their previous screen.
   * 
   * @param previousScreen the previous screen used by the user
   */  
  public void returnToPreviousScreen(Screen previousScreen) {
    if (previousScreen != null) {
      AudioManager.getInstance().playClickSound();
      game.setScreen(previousScreen);
    }
  }
}
