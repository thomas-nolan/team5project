package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * This class will deal with the 'main' game logic.
 * 
 * For example, creating the player, calling initialiseMap() on the RoomManager.
 * Will handle rendering of game textures using the SpriteBatch stored in EscapeGame. 
 */
public class GameScreen extends ScreenAdapter {
    private final EscapeGame game;
    private final GameController controller;
    private final UIController uiController;
    private boolean isPaused = false;

    public GameScreen(EscapeGame game, UIController uiController, GameController controller){
        this.game = game;
        this.uiController = uiController;
        this.controller = controller;
        
    }

    public void CheckLose(){   
        if (controller.getTimer().hasReached(300)) { // 300 seconds = 5 minutes
            uiController.showGameOver(false, controller.getTimer(), controller.getScoreManager());
        }
    }

    @Override
    public void render(float delta) {
    // Check for ESC key to pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseGame();
            return;
        }
    
        if (!isPaused) {
            controller.update(delta);
            CheckLose();
        }
        draw();
    }

    private void pauseGame(){
        isPaused = true;
        int pausedTime = controller.getTimer().getTimeSeconds();
        uiController.pauseGame(this, pausedTime);
    }

    //timer resuming form pause menu
    public void resumeGame() {
        isPaused = false;
    }

    /**
     * Draw textures to the screen each frame using the {@link EscapeGame}'s SpriteBatch.
     */
    private void draw()
    {
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
    public void dispose()
    {
        controller.dispose();
    }

    @Override public void show() {
        AudioManager.getInstance().playGameMusic();
        isPaused = false;
    } 
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

