package io.github.team10.escapefromuni;

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
    final EscapeGame game;
    Player player;
    RoomManager roomManager;

    public GameScreen(final EscapeGame game)
    {
        this.game = game;
        player = new Player(3f, 1f, 1f, game);
        roomManager = new RoomManager(game, player);
        roomManager.initialiseMap();
    }

    @Override
    public void render(float delta) {
		update(delta);
		draw();
	}

    /**
     * Performs game logic each frame. 
     * 
     * Always called before drawing textures.
     * @param delta float representing the time since the last frame.
     */
    private void update(float delta)
    {
        player.update(delta);
        roomManager.update(delta);
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
        roomManager.drawMap();
        player.draw();
        game.batch.end();

        // UI Rendering
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        roomManager.drawEventUI();

        game.batch.end();
    }

    @Override
    public void dispose()
    {
        roomManager.dispose();
        player.dispose();
    }
}
