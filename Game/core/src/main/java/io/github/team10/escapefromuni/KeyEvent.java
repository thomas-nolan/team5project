package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * New for Assessment 2
 * A new event that locks the door leading to the exit and requires a key
 * to open.
 * This key is located in the Dean's room and is guarded by him.
 */
public class KeyEvent implements IEvent {

  private final Player player;
  private final EscapeGame game;

  private Texture keyTexture;
  private Sprite keySprite;
  private final EventSystem eventSystem;

  private boolean isFinished = false;
  private boolean collected = false;

  private BitmapFont font = new BitmapFont();

  /**
   * Constructor for the KeyEvent.
   * 
   * @param player the player controlled by the user
   * @param game the main libGDX game instance
   * @param eventSystem the central logic for events
   */
  public KeyEvent(Player player, EscapeGame game, EventSystem eventSystem) {
    this.player = player;
    this.game = game;
    this.eventSystem = eventSystem;

    font.setColor(Color.RED);
  }

  @Override
  public EventType getType() {
    return EventType.POSITIVE;
  }

  @Override
  public void startEvent() {
    keyTexture = new Texture("keycard1.png");
    keySprite = new Sprite(keyTexture);
    keySprite.setSize(1f, 1f);
    keySprite.setPosition(7f, 4f);
  }

  @Override
  public void endEvent() {
    keyTexture.dispose();
  }

  /**
   * Update runs each frame and checks for player collision with the key.
   * 
   * @param delta The time between frames
   */
  @Override
  public void update(float delta) {
    if (isFinished) {
      return;
    }

    if (!collected && player.checkCollision(keySprite)) {
      player.giveKey();
      collected = true;
      isFinished = true;
      eventSystem.registerEvent(EventType.POSITIVE);
    }
  }

  @Override
  public void draw() {
    if (!isFinished) {
      keySprite.draw(game.batch);
    }
  }

  @Override
  public void drawUI() {}

  @Override
  public boolean IsFinished() {
    return isFinished;
  }
}
