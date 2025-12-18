package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;

/**
 * Represents the Game Over screen, shown when the player either wins or loses.
 * Displays the appropriate background, score, and time information.
 * The player can return to the main menu by pressing the ESC key.
 */
public class GameOverScreen implements Screen {

    private final EscapeGame game;
    private final UIController uiController;
    private final boolean isWon;
    private final Timer timer;
    private final ScoreManager scoreManager;
    private final AchievementManager achievementManager;

    private final BitmapFont font;
    private final Texture winScreen;
    private final Texture loseScreen;

    private boolean leaderboardsSaved = false;

    /**
     * Constructs a new GameOVerScreen.
     * @param game  The main game instance.
     * @param isWon Whether the player has won or lost.
     * @param timer The timer used to track playtime.
     * @param scoreManager  The score manager which calculates the final score.
     */
    public GameOverScreen(final EscapeGame game, UIController uiController, boolean isWon, Timer timer, ScoreManager scoreManager, AchievementManager achievementManager) {
        this.game = game;
        this.uiController = uiController;
        this.isWon = isWon;
        this.timer = timer;
        this.scoreManager = scoreManager;
        this.achievementManager = achievementManager;

        this.font = game.font;
        this.winScreen = new Texture("WinScreen.png");
        this.loseScreen = new Texture("LoseScreen.png");
    }

    @Override
    public void render(float delta) {
        // Return to main menu if ESC pressed.
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            uiController.showMainMenu();
            dispose();
            return;
        }

        ScreenUtils.clear(Color.BLACK);
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();

        if(isWon){
            try {
                renderWinScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            renderLoseScreen();
        }
        game.batch.end();
    }

    /**
     * Renders the winning screen, showing the win background, final score and time elapsed.
     */
    private void renderWinScreen() throws IOException {
        game.batch.draw(winScreen, 0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
        String timeText = "Time Elapsed: " + timer.getTimeSeconds();
        checkAchievements(timer.getTimeLeftSeconds());
        int finalScore = scoreManager.CalculateFinalScore(timer.getTimeLeftSeconds(), achievementManager);
        if (leaderboardsSaved == false) {
            scoreManager.leaderboards(finalScore);
            leaderboardsSaved = true;
        }
        String scoreText = "Score: " + finalScore;

        game.font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout();

        float uiWidth = game.uiViewport.getWorldWidth();
        float uiHeight = game.uiViewport.getWorldHeight();

        // Draw time elapsed text
        layout.setText(game.font, timeText);
        float timeX = (uiWidth - layout.width) / 2f;
        float timeY = uiHeight * 0.35f;
        game.font.draw(game.batch, layout, timeX, timeY);

        // Draw score text
        layout.setText(game.font, scoreText);
        float scoreX = (uiWidth - layout.width) / 2f;
        float scoreY = uiHeight * 0.3f;
        font.draw(game.batch, scoreText, scoreX, scoreY);

        displayAchievements();

    }

    /**
     * Renders the losing screen with the lose background.
     *
     * Doesn't display the score or time.
     */
    private void renderLoseScreen(){
        game.batch.draw(loseScreen, 0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
    }

    public void checkAchievements(int timeLeftSeconds){

        if (timeLeftSeconds > 260){
            achievementManager.addAchievement("Speedrun");
        }

    }

    public void displayAchievements(){

        float achievementY = game.uiViewport.getWorldHeight() - 20;
        String[] achievements = achievementManager.getAchievements();
        font.draw(game.batch, "Achievements: ", 20, achievementY);

        for (int i = 0; i < achievements.length; i++){

            achievementY -= 50;
            font.draw(game.batch, achievements[i], 20, achievementY);
            
        }

    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    /**
     * Dispose of textures used by the screen.
     */
    @Override public void dispose() {
        winScreen.dispose();
        loseScreen.dispose();
    }
}
