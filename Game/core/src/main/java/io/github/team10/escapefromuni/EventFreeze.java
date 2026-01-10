package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * NEW FOR ASSESSMENT 2.
 * Class for positive event.
 * This event freezes the timer and the dean for a fixed period of time.
 * It is activated by collecting the freeze pickup.
 */
public class EventFreeze implements IEvent {

  // Texture and sprite used to render the freeze event
  private final Texture freezeTexture;
  private Sprite freezeSprite;

  // References to core game systems required for the event
  private Timer timer;
  private boolean eventFinished;
  private final EventType type;
  private Player player;
  private EscapeGame game;

  // Tracks the events state
  private final EventSystem eventSystem;
  private boolean used = false;

  /**
   * NEW FOR ASSESSMENT 2.
   * Constructor used to create a new freeze event.
   *
   * @param player the player controlled by the user
   * @param game the main LibGDX game instance
   * @param timer the games timer
   * @param eventSystem the central events manager
   */
  public EventFreeze(Player player, EscapeGame game, Timer timer, EventSystem eventSystem) {
    this.player = player;
    this.game = game;
    this.type = EventType.POSITIVE;
    freezeTexture = new Texture("timer.png");
    this.timer = timer;
    this.eventFinished = false;
    this.eventSystem = eventSystem;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Returns the type of event
   */
  @Override
  public EventType getType() {
    return type;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Initialises the freeze event.
   */
  public void startEvent() {
    if (eventFinished) {
      return;
    }

    // Creates a new sprite setting its texture, size and position
    freezeSprite = new Sprite(freezeTexture);
    freezeSprite.setSize(2f, 1f);
    freezeSprite.setPosition(7.5f, 5.5f);
    // AudioManager.getInstance().playEventSound(this.type);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * End the event, cleaning up any resources once the event has been used.
   */
  public void endEvent() {
    if (!eventFinished && used) {
      eventFinished = true;
      freezeTexture.dispose();
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Updates the events logic each frame.
   */
  public void update(float delta) {
    if (!used) {
      float playerDist = getPlayerFreezeDist();
      if (playerDist < 1f) {
        pickupFreeze();
      }
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Calculates the distance between the player and the freeze event pickup.
   *
   * @return The distance between the player and the centre of the freeze pickup
   */
  private float getPlayerFreezeDist() {
    Vector2 playerPos = player.getCenter();
    float freezeX = freezeSprite.getX() + freezeSprite.getWidth() / 2f;
    float freezeY = freezeSprite.getY() + freezeSprite.getHeight() / 2f;
    Vector2 freezeCenter = new Vector2(freezeX, freezeY);
    return freezeCenter.dst(playerPos);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Handles the collection of the freeze pick up.
   * Registers the event type.
   */
  private void pickupFreeze() {
    used = true;
    // Implement here
    eventSystem.registerEvent(type);
    // Stops the timer upon pick up
    timer.setFrozen();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Renders the freeze pickup while it has not been used.
   */
  public void draw() {
    if (!used) {
      freezeSprite.draw(game.batch);
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Used to draw the UI for the event.
   * Not needed as there is no UI.
   */
  @Override
  public void drawUI() {}

  @Override
  public boolean IsFinished() {
    return eventFinished;
  }
}

