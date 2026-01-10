package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Positive event where the player encounters a Greggs sausage roll.
 * When the player runs into the sausage roll, they gain a speed increase.
 */
public class EventGreggs implements IEvent {

  private final Player player;
  private final EscapeGame game;
  private final EventType type;
  private final EventSystem eventSystem;

  private boolean eventFinished = false;

  private final Texture greggsTexture;
  private Sprite greggsSprite;

  private boolean used = false;

  // NEW FOR ASSESSMENT 2 - used to modify the speed boost
  private DifficultySetup diffSetup = new DifficultySetup();

  /**
   * EXTENDS ON FROM ASSESSMENT 1.
   * Creates a new EventGreggs and sets up the new systems created in assessment 2.
   * Also sets up the other classes needed in the event.
   */
  public EventGreggs(Player player, EscapeGame game, EventSystem eventSystem) {
    this.player = player;
    this.game = game;
    this.type = EventType.POSITIVE;
    greggsTexture = new Texture("GreggsSausageRoll.png");
    this.eventSystem = eventSystem;
  }

  /**
   * Returns the type for the event.
   */
  @Override
  public EventType getType() {
    return type;
  }

  /**
   * Returns boolean value to say if the event has finished or not.
   */
  @Override
  public boolean IsFinished() {
    return eventFinished;
  }

  /**
   * Starts the event, and sets up the sprite.
   */
  @Override
  public void startEvent() {
    if (eventFinished) {
      return;
    } 

    // Creates the greggs sprite, setting its size and position
    // Also gets the instance of its sound
    greggsSprite = new Sprite(greggsTexture);
    greggsSprite.setSize(3f, 2f);
    greggsSprite.setPosition(6.5f, 3.5f);
    AudioManager.getInstance().playEventSound(this.type);
  }

  /**
   * Cleans up the event resources once the pickup has been collected.
   */
  @Override
  public void endEvent() {
    if (!eventFinished && used) {
      eventFinished = true;
      greggsTexture.dispose();
    }
  }

  /**
   * Updates the event logic.
   */
  @Override
  public void update(float delta) {
    if (!used) {
      // Gets the distance to the Greggs sprite and picks it up if the distance is less than 1
      float playerDist = getPlayerGreggsDist();
      if (playerDist < 1f) {
        pickupGreggs();
      }
    }
  }

  /**
   * Calculates the distance between the center of the player and the Greggs sprite.
   *
   * @return the distance between the player and the Greggs sprite.
   */
  private float getPlayerGreggsDist() {
    Vector2 playerPos = player.getCenter();

    // Gets the coordinates of the greggs sprite
    float greggsX = greggsSprite.getX() + greggsSprite.getWidth() / 2f;
    float greggsY = greggsSprite.getY() + greggsSprite.getHeight() / 2f;
    Vector2 greggsCenter = new Vector2(greggsX, greggsY);
    return greggsCenter.dst(playerPos);
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Handles applying the effects (speed increase) of collecting the Greggs sausage roll.
   */
  private void pickupGreggs() {
    used = true;
    // NEW FOR ASSESSMENT 2 - Registers the event as positive.
    eventSystem.registerEvent(EventType.POSITIVE);
    // NEW FOR ASSESSMENT 2 - Modifies the players speed depending on the difficulty
    player.increaseSpeed(2f * diffSetup.readDifficulty().speedBoostModifier);
  }

  /**
   * Renders the Greggs pickup whilst its not picked it.
   */
  @Override
  public void draw() {
    if (!used) {
      greggsSprite.draw(game.batch);
    }
  }

  /**
   * Draws the UI elements, there are none currently.
   */
  @Override
  public void drawUI() {}

}
