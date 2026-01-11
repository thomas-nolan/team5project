package io.github.team10.escapefromuni;

/**
 * New for Assessment 2.
 * A class to control the player behaviour
 */
public class PlayerController {

  public final EscapeGame game;
  public final Player player;

  /**
   * Constructor for the player controller.
   * 
   * @param game the main LibGDX game instance
   * @param player the player controlled by the user
   */
  public PlayerController(EscapeGame game, Player player) {
    this.game = game;
    this.player = player;
  }

  /**
   * Getter for the player.
   * 
   * @return - The player class
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Defines the player position after a room change.
   * 
   * @param direction - NORTH,EAST,SOUTH,WEST
   */
  public void positionAfterRoomChange(DoorDirection direction) {

    float w = game.viewport.getWorldWidth();
    float h = game.viewport.getWorldHeight();

    // Sets where the player will appear when entering a new room based on direction
    switch (direction) {
      case NORTH:
        player.setCenter(w / 2, 2);
        break;
      case EAST:
        player.setCenter(2f, h / 2);
        break;
      case SOUTH:
        player.setCenter(w / 2, h - 2);
        break;
      case WEST:
        player.setCenter(w - 2, h / 2);
        break;
    }
  }

  /**
   * Updates the player logic each frame.
   * 
   * @param delta the time elapsed between frames.
   */
  public void update(float delta) {
    player.update(delta);
  }

  /**
   * Draws the UI for the player.
   */
  public void drawPlayer() {
    player.draw();
  }

  /**
   * Disposes the players assests.
   */
  public void dispose() {
    player.dispose();
  }
}
