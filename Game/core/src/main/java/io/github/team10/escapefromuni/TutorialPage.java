package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Tutorial page displaying game instructions.
 * 
 * Shows tutorial image. ESC key returns to main menu.
 */
public class TutorialPage implements Screen {
    
    private final EscapeGame game;
    private final UIController uiController;
    private Texture tutorialImage;
    
    /**
     * Creates a new TutorialPage instance.
     * @param game Reference to the main {@link EscapeGame} instance.
     */
    public TutorialPage(EscapeGame game, UIController uiController) {
        this.game = game;
        this.uiController = uiController;
    }
    
    @Override
    public void show() {
        // Load tutorial image
        tutorialImage = new Texture(Gdx.files.internal("tutorial1.png"));
    }
    
    /**
     * Displays the tutorial image.
     */
    public void display() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(tutorialImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
    }
    
    /**
     * Handles ESC key press.
     * Returns to main menu.
     */
    public void onEscPress() {
        uiController.showMainMenu();
        dispose();
    }
    
    @Override
    public void render(float delta) {
        // Check for ESC key press
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onEscPress();
        }
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        display();
    }
    
    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        tutorialImage.dispose();
    }
}