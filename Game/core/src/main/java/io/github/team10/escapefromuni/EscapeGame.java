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
 * Class to represent the game. Instantiated by the Launcher.
 * 
 * Similar to the Drop class in https://libgdx.com/wiki/start/simple-game-extended.
 * Loads the initial Screen. Has a SpriteBatch batch, to be used by each screen to render the game.
 */
public class EscapeGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    // World rendering
    public FitViewport viewport;

    // UI rendering
    public OrthographicCamera uiCamera;
    public FitViewport uiViewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        viewport = new FitViewport(16, 9);
        // 256 
        // 160/256 = 0.625
        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(1920, 1080, uiCamera);
        // 0.625 * 1920 = 1200
        // 1200 / 160 = 7.5

        generateFont();

        this.setScreen(new GameScreen(this));
    }

    /**
     * Will initialise the game's font.
     */
    private void generateFont()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Mini.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 48;
        parameter.color = Color.WHITE;

        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.minFilter = Texture.TextureFilter.Nearest;

        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void render() {
		super.render();
	}

    @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
