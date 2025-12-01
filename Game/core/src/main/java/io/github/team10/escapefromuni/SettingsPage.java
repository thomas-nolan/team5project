package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Settings page for adjusting game audio.
 */
public class SettingsPage implements Screen {
    
    private final EscapeGame game;
    private final UIController uiController;
    private final Screen previousScreen;
    private Texture backgroundImage;
    private Texture buttonTexture;
    private Texture sliderBarTexture;
    private Texture sliderKnobTexture;
    private BitmapFont font;
    private GlyphLayout layout;
    
    // UI elements
    private Rectangle backButton;
    private Rectangle sliderBar;
    private Rectangle sliderKnob;
    
    // States
    private boolean backHovered;
    private boolean isDraggingSlider;
    private int audioLevel;
    
    public SettingsPage(EscapeGame game, UIController uiController, Screen previousScreen) {
        this.game = game;
        this.uiController = uiController;
        this.previousScreen = previousScreen;
        this.audioLevel = (int)(AudioManager.getInstance().getVolume() * 100);
    }
    
    @Override
    public void show() {
        // Load textures
        backgroundImage = new Texture(Gdx.files.internal("Settings_Background.png"));
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));
        sliderBarTexture = new Texture(Gdx.files.internal("SliderBar.png"));
        sliderKnobTexture = new Texture(Gdx.files.internal("SliderKnob.png"));
        
        font = game.font;
        layout = new GlyphLayout();
        
        // Initialize UI
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;
        
        // Back button
        float buttonWidth = 400f;
        float buttonHeight = 80f;
        backButton = new Rectangle(centerX - buttonWidth / 2f, 200f, buttonWidth, buttonHeight);
        
        // Slider bar and knob to change volume
        float sliderWidth = 600f;
        float sliderHeight = 40f;
        sliderBar = new Rectangle(centerX - sliderWidth / 2f, screenHeight / 2f - 50f, sliderWidth, sliderHeight);
        
        float knobSize = 60f;
        float knobX = sliderBar.x + (sliderBar.width * audioLevel / 100f) - knobSize / 2f;
        sliderKnob = new Rectangle(knobX, sliderBar.y - 10f, knobSize, knobSize);
        
        isDraggingSlider = false;
        
        AudioManager.getInstance().playMenuMusic();
    }
    
    public void display() {
         
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
        
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        
        // Settings title
        String title = "SETTINGS";
        layout.setText(font, title);
        float titleX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float titleY = game.uiViewport.getWorldHeight() - 150f;
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, titleX, titleY);
        
        // Draw audio level label
        String volumeText = "Volume: " + audioLevel + "%";
        layout.setText(font, volumeText);
        float volumeX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float volumeY = sliderBar.y + 120f;
        font.setColor(Color.YELLOW);
        font.draw(game.batch, layout, volumeX, volumeY);
        
        // Draw slider bar
        game.batch.setColor(Color.WHITE);
        game.batch.draw(sliderBarTexture, sliderBar.x, sliderBar.y, sliderBar.width, sliderBar.height);
        
        // Draw slider knob
        game.batch.draw(sliderKnobTexture, sliderKnob.x, sliderKnob.y, sliderKnob.width, sliderKnob.height);
        
        // Draw back button
        drawButton(backButton, "Go Back", backHovered);
        
        game.batch.end();
    }
    
    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Draw button background texture
        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f);
        } else {
            game.batch.setColor(Color.WHITE);
        }
        game.batch.draw(buttonTexture, button.x, button.y, button.width, button.height);
        game.batch.setColor(Color.WHITE);
        
        // Draw button text
        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;
        
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }
    
    private void updateSlider() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.uiCamera.unproject(mousePos);
        
        if (Gdx.input.isTouched()) {
            if (sliderBar.contains(mousePos.x, mousePos.y) || isDraggingSlider) {
                isDraggingSlider = true;
                
                float relativeX = mousePos.x - sliderBar.x;
                relativeX = Math.max(0, Math.min(relativeX, sliderBar.width));
                
                audioLevel = (int)((relativeX / sliderBar.width) * 100);
                adjustAudioLevel(audioLevel);
                
                sliderKnob.x = sliderBar.x + (sliderBar.width * audioLevel / 100f) - sliderKnob.width / 2f;
            }
        } else {
            isDraggingSlider = false;
        }
    }
    
    public void adjustAudioLevel(int level) {
        if (level >= 0 && level <= 100) {
            this.audioLevel = level;
            AudioManager.getInstance().setVolume(level / 100f);
        }
    }
    
    public int getAudioLevel() {
        return audioLevel;
    }
    
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
    
    private boolean isButtonHovered(Rectangle button) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.uiCamera.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }
    
    public void onBack() {
        uiController.returnToPreviousScreen(previousScreen);
        dispose();
    }
    
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onBack();
        }
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        updateSlider();
        
        backHovered = isButtonHovered(backButton);
        
        if (isButtonClicked(backButton)) {
            onBack();
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
        sliderBarTexture.dispose();
        sliderKnobTexture.dispose();
    }
}