package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * NEW FOR ASSESSMENT 2.
 * This class is another negative event
 * It freezes the player in place for 5 seconds
 */
public class BearTrapEvent implements IEvent {

  private static final float FREEZE_TIME = 5f;
  private final Texture trapTexture;
  private Sprite trapSprite;

  private final Player player;
  private final Timer timer;
  private final EscapeGame game;
  
  private float remaining = FREEZE_TIME;
  private boolean finished = false;
  private boolean triggered = false;
  private final EventSystem eventSystem;

  private BitmapFont font = new BitmapFont();

  /**
   * NEW FOR ASSESSMENT 2
   * This is a contructor for the event.
   * It intailises the game, player, timer and eventSystem
   * Also loads the texture and font for the event
   * 
   * @param game the main LibGDX game instance
   * @param player the player character controlled by the user
   * @param timer the games timer
   * @param eventSystem the central events manager
   */
  public BearTrapEvent(EscapeGame game, Player player, Timer timer, EventSystem eventSystem) {

    this.player = player;
    this.timer = timer;
    this.game = game;
    this.eventSystem = eventSystem;

    this.trapTexture = new Texture("trap.png");
    font.setColor(Color.RED);

  }

  /**
   * NEW FOR ASSESSMENT 2
   * This returns the event type which is negative.
   */
  @Override 
  public EventType getType() {
    return EventType.NEGATIVE;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This method starts the event by creating the sprite 
   * establishing its placement, texture as well as size
   */
  @Override
  public void startEvent() {
    trapSprite = new Sprite(trapTexture);
    trapSprite.setSize(2f, 2f);
    trapSprite.setPosition(7f, 4f);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This ends the event by disposing of the font and texture
   */
  @Override
  public void endEvent() {
    trapTexture.dispose();
    font.dispose();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Handles the logic of triggering the event
   * Checks if the player has colides and triggers the event if so 
   * Also adds to the events counter
   */
  @Override
  public void update(float delta) {
    if (finished) {
      return;
    }
    if (!triggered) {
      // checks if the player touches the sprite
      if (player.checkCollision(trapSprite) && !player.isInvincible()) {
        // sets triggered to true and disables players moment
        // also adds to the negative event counter
        triggered = true;
        eventSystem.registerEvent(EventType.NEGATIVE);
        player.enableMovement(false);
      }
      return;
    }
 
    remaining -= delta;
    
    // enables movement after the set time has elapsed
    if (remaining <= 0) {
      player.enableMovement(true);
      finished = true;
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This draws the sprite into the game
   * Does not draw it once triggered
   */
  @Override
  public void draw() {
    if (!triggered) {
      trapSprite.draw(game.batch);
    } 
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This draws the ui for the event displaying whilst triggered
   * It displays information about the event
   * It also displays the time left frozen
   */
  @Override
  public void drawUI() {
    if (!finished && triggered) {
      // Increases the font for visibitly
      font.getData().setScale(4f);

      String message = "Wet floor! Frozen for " + (int) Math.ceil(remaining) + "s";
     
      // Calculates layout dimensions to centre the ui on the screen
      GlyphLayout layout = new GlyphLayout(font, message);

      float uiWidth = game.uiViewport.getWorldWidth();
      float uiHeight = game.uiViewport.getWorldHeight();

      // Centers the text horizontally and places it near the top of the screen
      float textX = (uiWidth - layout.width) / 2f;
      float textY = uiHeight * 0.9f;

      font.setColor(Color.RED);
      font.draw(game.batch, layout, textX, textY);
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This returns if the game is finished or not
   */
  @Override
  public boolean IsFinished() {
    return finished;
  }
    
}
