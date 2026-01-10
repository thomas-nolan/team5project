package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * EXTENDED FROM ASSESSMENT 1.
 * This class will deal with the 'main' game logic.
 * For example, creating the player, calling initialiseMap() on the RoomManager.
 * Will handle rendering of game textures using the SpriteBatch stored in EscapeGame.
 */
public class GameScreen extends ScreenAdapter {

  // Initialises the new ASSESSMENT 2 systems
  private final EscapeGame game;
  private final GameController controller;
  private final UIController uiController;

  private boolean isPaused = false;

  /**
   * EXTENDED FROM ASSESSMENT 1 - to use new systems.
   * Creates a new game screen
   *
   * @param game the main LibGDX game instance
   * @param uiController the UI manager controlling the games screens
   * @param controller the main game controller
   */
  public GameScreen(EscapeGame game, UIController uiController, GameController controller) {
    this.game = game;
    this.uiController = uiController;
    this.controller = controller;
  }

  /**
   * This method checks if the player has run out of time and lost.
   */
  public void CheckLose() {
    if (controller.getTimer().hasReached(300)) { // 300 seconds = 5 minutes
      uiController.showGameOver(false, controller.getTimer(), controller.getScoreManager());
    }
  }

  /**
   * The main render loop called every frame.
   */
  @Override
  public void render(float delta) {
    // Check for ESC key to pause
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      pauseGame();
      return;
    }

    // Only updates the game logic when not paused
    if (!isPaused) {
      controller.update(delta);
      CheckLose();
    }
    draw();
  }

  /**
   * Pauses the game and switches the pause menu.
   */
  private void pauseGame() {
    isPaused = true;
    int pausedTime = controller.getTimer().getTimeSeconds();
    uiController.pauseGame(this, pausedTime);
  }

  /**
   * Resumes the game as well as timer for the game.
   */
  public void resumeGame() {
    isPaused = false;
  }

  /**
   * Draw textures to the screen each frame using the {@link EscapeGame}'s SpriteBatch.
   */
  private void draw() {
    ScreenUtils.clear(Color.BLACK);
    game.viewport.apply();
    game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
    game.batch.begin();

    // World Rendering
    controller.drawWorld();
    game.batch.end();

    // UI Rendering
    game.uiViewport.apply();
    game.batch.setProjectionMatrix(game.uiCamera.combined);
    game.batch.begin();
    controller.drawUI(game);
    game.batch.end();
  }

  @Override
  public void dispose() {
    controller.dispose();
  }

  @Override
  public void show() {
    AudioManager.getInstance().playGameMusic();
    isPaused = false;
  }

  @Override public void resize(int width, int height) {}

  @Override public void pause() {}

  @Override public void resume() {}

  @Override public void hide() {}
}

