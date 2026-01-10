package io.github.team10.escapefromuni;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * NEW TO ASSESSMENT 2
 * Class to represent the main LibGDX game instance.
 * Instantiated by the Launcher and is responsible for initialising core
 * game systems such as rendering SpriteBatch batch and the initial screen.
 * Similar to the Drop class in https://libgdx.com/wiki/start/simple-game-extended.
 */
public class EscapeGame extends Game {
  public SpriteBatch batch;
  public BitmapFont font;

  // World rendering
  public FitViewport viewport;

  // UI rendering
  public OrthographicCamera uiCamera;
  public FitViewport uiViewport;

  public GameController gameController;
  public UIController uiController;

  /**
   * Called when the game is first created.
   * Initialises rendering components, viewport, controllers
   * as well as loading the main menu
   */
  @Override
  public void create() {
    batch = new SpriteBatch();

    // UI and world rendering set up
    viewport = new FitViewport(16, 9);
    uiCamera = new OrthographicCamera();
    uiViewport = new FitViewport(1920, 1080, uiCamera);

    // Generates the default font
    generateFont();

    // Initialises the controllers
    this.uiController = new UIController(this, null);
    this.gameController = new GameController(this, uiController);
    uiController.setGameController(gameController);

    // Shows the main menu
    uiController.showMainMenu();
  }

  /**
   * Initialise the game's default font.
   */
  private void generateFont() {

    // Loads the game's main UI font from internal assets
    final FreeTypeFontGenerator generator =
        new FreeTypeFontGenerator(
        Gdx.files.internal("Kenney Mini.ttf"));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();

    // Sets the font size and colour
    parameter.size = 48;
    parameter.color = Color.WHITE;

    parameter.magFilter = Texture.TextureFilter.Nearest;
    parameter.minFilter = Texture.TextureFilter.Nearest;

    // Sets the bitmap font using the configured parameters
    font = generator.generateFont(parameter);
    generator.dispose();
  }

  /**
   * This method is called when the game window is resized.
   * It updates both the viewport and the uiViewport to maintain correct scaling
   */
  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
    uiViewport.update(width, height, true);
  }

  /**
   * Called every frame to render the game.
   */
  @Override
  public void render() {
    super.render();
  }

  /**
   * Cleans up the shared resources when the game exits.
   */
  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
    AudioManager.getInstance().dispose();
  }
}
