package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Main menu screen displayed on game launch with menu options:
 * Start Game, Tutorial,Exit.
 */
public class MainMenu implements Screen {

    private final EscapeGame game;
    private final UIController ui;
    private Texture backgroundImage;
    private Texture buttonTexture;
    private BitmapFont font;
    private GlyphLayout layout;

    // buttons
    private Rectangle startButton;
    private Rectangle tutorialButton;
    private Rectangle settingsButton;
    private Rectangle exitButton;

    // hover states for nuttons
    private boolean startHovered;
    private boolean tutorialHovered;
    private boolean settingsHovered;
    private boolean exitHovered;

    public MainMenu(EscapeGame game, UIController ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public void show() {
        // backgrouns
        backgroundImage = new Texture(Gdx.files.internal("mainmenu_background.png"));
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));

        font = game.font;
        layout = new GlyphLayout();

        // button sizes
        float buttonWidth = 400f;
        float buttonHeight = 80f;

        // alignment; To be Fixed
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;

        // main menu button positions
        startButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f + 150f, buttonWidth, buttonHeight);
        tutorialButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f + 50f, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f - 50f, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f - 150f, buttonWidth, buttonHeight);

        //menu music 
        AudioManager.getInstance().playMenuMusic();
    }

    // Draws the main menu UI
    public void display() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();

        // draw all main menu buttons
        drawButton(startButton, "Start Game", startHovered);
        drawButton(tutorialButton, "Tutorial", tutorialHovered);
        drawButton(settingsButton, "Settings", settingsHovered);
        drawButton(exitButton, "Exit", exitHovered);

        game.batch.end();
    }

    //the buttons
    private void drawButton(Rectangle button, String text, boolean hovered) {

        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f);
        } else {
            game.batch.setColor(Color.WHITE);
        }

        //button bg,size etc
        game.batch.draw(buttonTexture, button.x, button.y, button.width, button.height);
        game.batch.setColor(Color.WHITE);

        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;

        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }

    private boolean isButtonClicked(Rectangle button) {
        // click detector
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.uiViewport.unproject(touchPos);

            if (button.contains(touchPos.x, touchPos.y)) {
                // play click audio
                AudioManager.getInstance().playClickSound();
                return true;
            }
        }
        return false;
    }

    private boolean isButtonHovered(Rectangle button) {
        // detect mouse hover in UI coordinates
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        game.uiViewport.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }

    public void onStartGame() {
        // switch to main gameplay
        System.out.println("Starting game...");
        ui.startGame();
        dispose();
    }

    public void onTutorial() {
        // open tutorial page
        System.out.println("Opening tutorial...");
        ui.showTutorial();
        dispose();
    }

    public void onSettings() {
        // open settings page
        System.out.println("Opening settings...");
        ui.showSettings(this);
        dispose();
    }

    public void onExit() {
        // quit game
        System.out.println("Exiting game...");
        ui.exitGame();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startHovered = isButtonHovered(startButton);
        tutorialHovered = isButtonHovered(tutorialButton);
        settingsHovered = isButtonHovered(settingsButton);
        exitHovered = isButtonHovered(exitButton);


        if (isButtonClicked(startButton)) {
            onStartGame();
        } else if (isButtonClicked(tutorialButton)) {
            onTutorial();
        } else if (isButtonClicked(settingsButton)) {
            onSettings();
        } else if (isButtonClicked(exitButton)) {
            onExit();
        }

        // draw everything
        display();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        game.uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        
        backgroundImage.dispose();
        buttonTexture.dispose();
    }
}
