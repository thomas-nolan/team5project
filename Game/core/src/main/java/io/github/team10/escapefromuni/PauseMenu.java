package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
/**
 * Pause menu displayed during gameplay.
 * triggered by ESC key. shows paused timer and the options; Resume, Exit.
 */

public class PauseMenu implements Screen {
    
    private final EscapeGame game;
    private final UIController uiController;
    private final GameScreen gameScreen;
    private BitmapFont font;
    private GlyphLayout layout;
    private int pausedTime;
    private Texture backgroundImage;
    private Texture buttonTexture;
    
    // Button rectangles
    private Rectangle resumeButton;
    private Rectangle settingsButton;
    private Rectangle exitButton;
    
    // Button states
    private boolean resumeHovered;
    private boolean settingsHovered;
    private boolean exitHovered;
    
    public PauseMenu(EscapeGame game, UIController uiController, GameScreen gameScreen, int pausedTime) {
        this.game = game;
        this.uiController = uiController;
        this.gameScreen = gameScreen;
        this.pausedTime = pausedTime;
    }
    
    @Override
    public void show() {
        //button designings
        backgroundImage = new Texture(Gdx.files.internal("pausemenu_background.png"));
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));
        
        font = game.font;
        layout = new GlyphLayout();
        
        float buttonWidth = 600f;
        float buttonHeight = 100f;
        float screenWidth = game.uiViewport.getWorldWidth();
        float centerX = screenWidth / 2f;
        
        //buttons positioning
        resumeButton = new Rectangle(centerX - buttonWidth / 2f, 500f, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX - buttonWidth / 2f, 350f, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX - buttonWidth / 2f, 200f, buttonWidth, buttonHeight);

        AudioManager.getInstance().playMenuMusic();
    }
    
    ////displays  pause menu
    public void display() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
        
        //UI
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        
        String title = "You pulled out 'Exceptional Circumstances' card on dean";
        layout.setText(font, title);
        float titleX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float titleY = 750f; 
        font.setColor(Color.YELLOW);
        font.draw(game.batch, layout, titleX, titleY);
        
        //pause menu timer
        showPausedTimer(pausedTime);
        int minutes = pausedTime / 60;
        int seconds = pausedTime % 60;
        String timeText = String.format("Time: %02d:%02d", minutes, seconds);
        layout.setText(font, timeText);
        float timeX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float timeY = 650f;
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, timeX, timeY);

        //buttons
        drawButton(resumeButton, "Resume", resumeHovered);
        drawButton(settingsButton, "Settings", settingsHovered);
        drawButton(exitButton, "Exit to Menu", exitHovered);
        
        game.batch.end();
    }
    
    private void drawButton(Rectangle button, String text, boolean hovered) {
        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f);
        } else {
            game.batch.setColor(Color.WHITE);
        }
        game.batch.draw(buttonTexture, button.x, button.y, button.width, button.height);
        game.batch.setColor(Color.WHITE);
        
        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;
        
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }
    
    //click detection
    private boolean isButtonClicked(Rectangle button) {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.uiCamera.unproject(touchPos);
            if (button.contains(touchPos.x, touchPos.y)) {
                AudioManager.getInstance().playClickSound();
                return true;
            }
        }
        return false;
    }
    
    //hover detection
    private boolean isButtonHovered(Rectangle button) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.uiCamera.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }
    
    // shows paused timer with ELAPSED TIME
    public void showPausedTimer(int time) {
        this.pausedTime = time;
    }
    
    
    public void onResume() {
        System.out.println("Resuming game...");
        uiController.resumeGame(gameScreen);
        dispose();
    }
    
    public void onSettings() {
        System.out.println("Opening settings from pause menu...");
        uiController.showSettings(this);
        dispose();
    }
    
    public void onExit() {
        System.out.println("Returning to main menu...");
        gameScreen.dispose();
        uiController.showMainMenu();
        dispose();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        resumeHovered = isButtonHovered(resumeButton);
        settingsHovered = isButtonHovered(settingsButton);
        exitHovered = isButtonHovered(exitButton);
        
        if (isButtonClicked(resumeButton)) {
            onResume();
        } else if (isButtonClicked(settingsButton)) {
            onSettings();
        } else if (isButtonClicked(exitButton)) {
            onExit();
        }
        
        display();
    }
    
    @Override
    public void resize(int width, int height) {
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