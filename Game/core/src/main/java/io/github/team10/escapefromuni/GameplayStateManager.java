package io.github.team10.escapefromuni;

/**
 * NEW FOR ASSESSMENT 2.
 * This class handles transitions between the active game play and the end game states.
 */
public class GameplayStateManager {
  /**
   * NEW FOR ASSESSMENT 2.
   * This method triggers the win state.
   * 
   * @param game the main LibGDX game instance.
   * @param uiController the Controller for the game's screens
   * @param timer the internal timer the game uses
   * @param scoreManager the score manager instance
   */
  public static void triggerWin(EscapeGame game, UIController uiController, 
        Timer timer, ScoreManager scoreManager) {
    game.setScreen(new GameOverScreen(game, uiController, true, 
        timer, scoreManager, game.gameController.getAchievementManager(), 
        game.gameController.getEventSystem()));
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This method triggers the lose state.
   * 
   * @param game the main LibGDX game instance.
   * @param uiController the Controller for the game's screens
   * @param timer the internal timer the game uses
   * @param scoreManager the score manager instance
   */
  public static void triggerLose(EscapeGame game, UIController uiController, 
      Timer timer, ScoreManager scoreManager) {
    game.setScreen(new GameOverScreen(game, uiController, false, 
        timer, scoreManager, game.gameController.getAchievementManager(), 
        game.gameController.getEventSystem()));
  }
    
}

