package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class UIController {
    private final EscapeGame game;
    private GameController gameController;

    public UIController(EscapeGame game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void showMainMenu() {
        AudioManager.getInstance().playMenuMusic();
        game.setScreen(new MainMenu(game, this));
    }

    public void startGame() {
        AudioManager.getInstance().playClickSound();

        if(gameController != null){
            gameController.resetGame();
        }
        
        game.setScreen(new GameScreen(game, this, gameController));
    }

    public void showTutorial() {
        AudioManager.getInstance().playClickSound();
        game.setScreen(new TutorialPage(game, this));
    }

    public void showSettings(Screen previousScreen) {
        AudioManager.getInstance().playClickSound();
        game.setScreen(new SettingsPage(game, this, previousScreen));
    }

    public void pauseGame(GameScreen gameScreen, int pausedTime) {
        AudioManager.getInstance().playMenuMusic();
        game.setScreen(new PauseMenu(game, this, gameScreen, pausedTime));
    }

    public void resumeGame(GameScreen gameScreen) {
        AudioManager.getInstance().playClickSound();
        game.setScreen(gameScreen);
    }

    public void showGameOver(boolean win, Timer timer, ScoreManager scores) {
        AudioManager.getInstance().playMenuMusic();
        game.setScreen(new GameOverScreen(game, this, win, timer, scores));
    }

    public void exitGame() {
        AudioManager.getInstance().playClickSound();
        Gdx.app.exit();
    }

    public void returnToPreviousScreen(Screen previousScreen){
        if(previousScreen != null){
            AudioManager.getInstance().playClickSound();
            game.setScreen(previousScreen);
        }
    }
}
