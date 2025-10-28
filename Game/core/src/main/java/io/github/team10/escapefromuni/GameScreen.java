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

    private void update(float delta)
    {
        player.update(delta);
        roomManager.checkDoorCollision();
        
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.batch.begin();

        roomManager.drawMap();

        player.draw();

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose()
    {
        roomManager.dispose();
        player.dispose();
    }
}
