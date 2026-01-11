package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * NEW FOR ASSESSMENT 2.
 * This class contains the logic for the dean
 */
public class Dean extends Player {

  private boolean reached = false;
  private final float roomHeight;
  private final float roomWidth;
  private Texture deanTexture;

  private DifficultySetup difficultySetup = new DifficultySetup();

  /**
   * NEW FOR ASSESSMENT 2.
   * This constructor sets up the implementation of the dean
   *
   * @param speed the speed at which the dean moves
   * @param width the width of the dean
   * @param height the height of the dean
   * @param game the main LibGDX game instance
   * @param roomWidth the width of the room is in
   * @param roomHeight the height of the room the dean is in
   */
  public Dean(float speed, float width, float height,
       EscapeGame game, float roomWidth, float roomHeight) {
    super(speed, width, height, game);
    this.roomWidth = roomWidth;
    this.roomHeight = roomHeight;

    // creates the Dean's sprite and sets its texture and size
    deanTexture = new Texture("dean.png");
    playerSprite.setTexture(deanTexture);
    playerSprite.setSize(width, height);

    // Places the Dean in the centre of the room
    setCenter(roomWidth / 2f, roomHeight / 2f);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This controls the logic for the dean
   * Gets the dean to be constantly chasing the user
   */
  @Override
  public void update(float delta) {
    if (reached) {
      return;
    }
    moveToPlayer(delta);
    super.update(delta);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This handles the logic for the dean chasing the user
   * The Dean moves towards the player each frame
   *
   * @param delta the time elapsed since the last frame in seconds
   */
  private void moveToPlayer(float delta) {

    // Get current position of the Dean and the user
    Player player = game.gameController.getPlayer();
    Vector2 playerPos = player.getCenter();
    Vector2 deanPos = getCenter();

    // Calculates the direction vector of the player and the Dean
    float dx = playerPos.x - deanPos.x;
    float dy = playerPos.y - deanPos.y;

    float distance = deanPos.dst(playerPos);

    // Enables movement if the Dean is not on the player
    if (distance > 0) {
      float speedModifier = difficultySetup.readDifficulty().enemySpeedModifier;
      float moveX = (dx / distance) * speed * delta * speedModifier;
      float moveY = (dy / distance) * speed * delta * speedModifier;
      playerSprite.translateX(moveX);
      playerSprite.translateY(moveY);
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This is a simple check to see if the Dean has caught the player
   *
   * @param player the player controlled by the user
   * @return a boolean value stating if the player is caught
   */
  public boolean checkReached(Player player) {
    // Checks if the Dean and player is touching
    // Returns false if the player has the invincibility event triggered
    if (!reached && player.checkCollision(this.playerSprite) && !player.isInvincible()) {
      reached = true;
      return true;
    }
    return false;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This disposes of the Deans texture
   */
  @Override
  public void dispose() {
    super.dispose();
    if (deanTexture != null) {
      deanTexture.dispose();
    }
  }
}
