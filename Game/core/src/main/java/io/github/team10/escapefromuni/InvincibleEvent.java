package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * NEW FOR ASSESSMENT 2.
 * This is a positive event that gives the player immunity
 * to certain negative events such as the dean and the trap.
 */
public class InvincibleEvent implements IEvent {
  private final Player player;
  private final EscapeGame game;
  private boolean isFinished = false;
  private boolean collected = false;
  private Texture texture;
  private Sprite sprite;
  private final EventSystem eventSystem;

  /**
   * NEW FOR ASSESSMENT 2.
   * Creates a new Invincible event and sets up the systems and textures
   * needed.
   * 
   * @param game the main LibGDX game instance
   * @param eventSystem the system that handles the central logic for events
   */
  public InvincibleEvent(Player player, EscapeGame game, EventSystem eventSystem) {

    // Initialises the player, game and eventSystem used for the event
    this.player = player;
    this.game = game;
    this.eventSystem = eventSystem;

    // Sets the texture as well as the sprite
    texture = new Texture("shield.png");
    sprite = new Sprite(texture);
    sprite.setSize(1.5f, 1.5f);

    // Calculates and sets the position of the pick up
    float width = game.viewport.getWorldWidth();
    float height = game.viewport.getWorldHeight();
    sprite.setPosition(width / 2f - sprite.getWidth(), height / 2f - sprite.getHeight());
  }

  @Override
  public EventType getType() {
    return EventType.POSITIVE;
  }

  @Override
  public void startEvent() {}

  @Override
  public void endEvent() {
    texture.dispose();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This updates the logic each frame for the event
   */
  @Override
  public void update(float delta) {
    if (isFinished) {
      return;
    }
    if (!collected) {
      // Checks to see it the player picks up the event and adds it to the event counter
      if (player.checkCollision(sprite)) {
        collected = true;
        eventSystem.registerEvent(EventType.POSITIVE);
        player.setInvincible(10f);
      } 
    }
  }

  @Override
  public void draw() {
    if (!collected) {
      sprite.draw(game.batch);
    }
  }

  @Override
  public void drawUI() {}

  @Override
  public boolean IsFinished() {
    return isFinished;
  }
    
}
