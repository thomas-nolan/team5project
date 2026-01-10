package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import java.io.IOException;

/**
 * Represents the Game Over screen, shown when the player either wins or loses.
 * Displays the appropriate background, score, and time information.
 * The player can return to the main menu by pressing the ESC key.
 */
public class GameOverScreen implements Screen {

  private final EscapeGame game;
  private final UIController uiController;
  private final boolean isWon;
  private final Timer timer;
  private final ScoreManager scoreManager;
  private final AchievementManager achievementManager;
  private final EventSystem eventSystem;

  private final BitmapFont font;
  private final Texture winScreen;
  private final Texture loseScreen;

  private boolean leaderboardsSaved = false;

  /**
   * EXTENDED ON FROM ASSESSMENT 1.
   * Constructs a new GameOVerScreen.
   * 
   * @param game  The main game instance.
   * @param isWon Whether the player has won or lost.
   * @param timer The timer used to track playtime.
   * @param scoreManager  The score manager which calculates the final score.
   */
  public GameOverScreen(final EscapeGame game, UIController uiController, 
      boolean isWon, Timer timer, ScoreManager scoreManager, 
      AchievementManager achievementManager, EventSystem eventSystem) {

    // NEW FOR ASSESSMENT 2 - Sets up the core systems and managers used for the game over screen
    this.game = game;
    this.uiController = uiController;
    this.isWon = isWon;
    this.timer = timer;
    this.scoreManager = scoreManager;
    this.achievementManager = achievementManager;
    this.eventSystem = eventSystem;

    // NEW FOR ASSESSMENT 2 - Loads in the win and loose screens.
    this.font = game.font;
    this.winScreen = new Texture("WinScreen.png");
    this.loseScreen = new Texture("LoseScreen.png");
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * This renders the end game screen and handles its logic
   */
  @Override
  public void render(float delta) {
    // Return to main menu if ESC pressed.
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      // NEW FOR ASSESSMENT 2 - used to reset the achievements and show main menu
      achievementManager.reset();
      uiController.showMainMenu();
      dispose();
      return;
    }

    ScreenUtils.clear(Color.BLACK);
    game.uiViewport.apply();
    game.batch.setProjectionMatrix(game.uiCamera.combined);
    game.batch.begin();

    // Renders the screen depending on if the player has won
    if (isWon) {
      try {
        renderWinScreen();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      renderLoseScreen();
    }
    game.batch.end();
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Renders the winning screen, showing the win background, final score and time elapsed.
   */
  private void renderWinScreen() throws IOException {
    game.batch.draw(winScreen, 0, 0, game.uiViewport.getWorldWidth(), 
        game.uiViewport.getWorldHeight());
    final String timeText = "Time Elapsed: " + timer.getTimeSeconds();

    // NEW FOR ASSESSMENT 2
    // Calculates the score and saves it into the leaderboard
    checkAchievements(timer.getTimeLeftSeconds());
    int finalScore = scoreManager.CalculateFinalScore(timer.getTimeLeftSeconds(), 
        achievementManager);
    if (leaderboardsSaved == false) {
      scoreManager.leaderboards(finalScore);
      leaderboardsSaved = true;
    }
    final String scoreText = "Score: " + finalScore;

    game.font.setColor(Color.BLACK);
    GlyphLayout layout = new GlyphLayout();

    float uiWidth = game.uiViewport.getWorldWidth();
    float uiHeight = game.uiViewport.getWorldHeight();

    // Draw time elapsed text
    layout.setText(game.font, timeText);
    float timeX = (uiWidth - layout.width) / 2f;
    float timeY = uiHeight * 0.35f;
    game.font.draw(game.batch, layout, timeX, timeY);

    // Draw score text
    layout.setText(game.font, scoreText);
    float scoreX = (uiWidth - layout.width) / 2f;
    float scoreY = uiHeight * 0.3f;
    font.draw(game.batch, scoreText, scoreX, scoreY);

    displayAchievements();

  }

  /**
   * Renders the losing screen with the lose background.
   * Doesn't display the score or time.
   */
  private void renderLoseScreen() {
    game.batch.draw(loseScreen, 0, 0, 
        game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This checks the achievements completed and gives a new achievement if the time
   * completed is short enought.
   * 
   * @param timeLeftSeconds The time left on the timer after completing the game
   */
  public void checkAchievements(int timeLeftSeconds) {

    if (timeLeftSeconds > 270) {
      achievementManager.addAchievement("Speedrun");
    }

    // Adds 'All positive events' achievement
    if (eventSystem.getTriggered(EventType.POSITIVE) >= eventSystem.getMax(EventType.POSITIVE)) {
      achievementManager.addAchievement("All positive events");
    }

    // Adds 'All negative events' achievement
    if (eventSystem.getTriggered(EventType.NEGATIVE) >= eventSystem.getMax(EventType.NEGATIVE)) {
      achievementManager.addAchievement("All negative events");
    }

    // Adds 'All hidden events' achievement
    if (eventSystem.getTriggered(EventType.HIDDEN) >= eventSystem.getMax(EventType.HIDDEN)) {
      achievementManager.addAchievement("All hidden events");
    }


  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This displays all the Achievements completed
   */
  public void displayAchievements() {

    // NEW FOR ASSESSMENT 2 
    // Gets the coordinates for the placement of the Achievements on the end screen
    float achievementY = game.uiViewport.getWorldHeight() - 20;
    String[] achievements = achievementManager.getAchievements();
    font.draw(game.batch, "Achievements: ", 20, achievementY);

    // NEW FOR ASSESSMENT 2 - places each achievement below each other.
    for (int i = 0; i < achievements.length; i++) {
      achievementY -= 50;
      font.draw(game.batch, achievements[i], 20, achievementY);        
    }
  }

  @Override public void show() {}

  @Override public void resize(int width, int height) {}

  @Override public void pause() {}

  @Override public void resume() {}

  @Override public void hide() {}

  /**
   * Dispose of textures used by the screen.
   */
  @Override 
  public void dispose() {
    winScreen.dispose();
    loseScreen.dispose();
  }
}
