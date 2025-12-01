package io.github.team10.escapefromuni;

public class GameplayStateManager {
    public static void triggerWin(EscapeGame game, UIController uiController, Timer timer, ScoreManager scoreManager){
        game.setScreen(new GameOverScreen(game, uiController, true, timer, scoreManager));
    }
    public static void triggerLose(EscapeGame game, UIController uiController, Timer timer, ScoreManager scoreManager){
        game.setScreen(new GameOverScreen(game, uiController, false, timer, scoreManager));
    }

    
}

