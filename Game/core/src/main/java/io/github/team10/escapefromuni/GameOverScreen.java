package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;

public class GameOverScreen implements Screen {

    private final EscapeGame game;
    private BitmapFont font;
    private boolean isWon;
    private Texture winScreen;
    private Texture loseScreen;

    public GameOverScreen(final EscapeGame game, boolean isWon) {
        this.game = game;
        this.isWon = isWon;
        font = new BitmapFont();
        winScreen = new Texture("WinScreen.png");
        loseScreen = new Texture("LoseScreen.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        font.draw(game.batch, "GAME OVER", 200, 250);
        font.draw(game.batch, "Press ESC or close window", 200, 200);
        game.batch.end();
        if(isWon){
            renderWinScreen();
        }
        else{
            renderLoseScreen();
        }
    }

    private void renderWinScreen(){
        game.batch.draw(winScreen, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
    }
    private void renderLoseScreen(){
        game.batch.draw(loseScreen, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { font.dispose(); }
}
