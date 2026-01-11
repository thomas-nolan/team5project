package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;

/**
 * New for Assessment 2
 * This class manages the new teleport event that teleports
 * the player between rooms in the map.
 */
public class TeleportEvent implements IEvent {

  private final Player player;
  private final RoomFlowManager roomFlow;
  private final EscapeGame game;
  private final EventSystem eventSystem;

  private Sprite teleportSprite;
  private Texture teleportTexture;

  private boolean isFinished = false;

  /**
   * NEW FOR ASSESSMENT 2.
   * Constructor for the teleport.
   * 
   * @param player - The player character
   * @param roomFlow - The RoomFlowManager
   * @param game - The EscapeGame
   * @param eventSystem - The EventSystem
   */
  public TeleportEvent(Player player, RoomFlowManager roomFlow, 
      EscapeGame game, EventSystem eventSystem) {
    this.player = player;
    this.roomFlow = roomFlow;
    this.game = game;
    this.eventSystem = eventSystem;
  }

  /**
   * Getter for the event type.
   * 
   * @return - The EventType for the TeleportEvent (HIDDEN)
   */
  @Override
  public EventType getType() {
    return EventType.HIDDEN;
  }

  /**
   * Initialises the event's texture and its position in the map.
   * The event resembles a map (map.png)
   */
  @Override
  public void startEvent() {
    teleportTexture = new Texture("map.png");
    teleportSprite = new Sprite(teleportTexture);
    teleportSprite.setSize(3.5f, 3.5f);

    float width = game.viewport.getWorldWidth();
    float height = game.viewport.getWorldHeight();
    teleportSprite.setCenter(width - teleportSprite.getWidth() - 0.5f, 
        height / 2f - teleportSprite.getHeight() / 2f + 1.5f);

  }

  /**
   * Disposes of the texture when it is no longer needed.
   */
  @Override
  public void endEvent() {
    teleportTexture.dispose();
  }

  /**
   * Checks if the event is collided with by the player.
   * 
   * @param delta The time elapsed since the last frame in seconds.
   */
  @Override
  public void update(float delta) {
    if (isFinished) {
      return;
    } 

    if (player.checkCollision(teleportSprite) && Gdx.input.isKeyJustPressed(Input.Keys.F)) {

      Room destination = roomFlow.getRandomRoom(roomFlow.getRoomByName("hiddenBookshelfRoom"));

      if (destination != null) {
        eventSystem.registerEvent(EventType.HIDDEN);
        isFinished = true;
        roomFlow.changeRoomTo(destination);
        
      }
    }
  }

  /**
   * Draws the event sprite on the map.
   */
  @Override
  public void draw() {
    if (!isFinished) {
      teleportSprite.draw(game.batch);
    }
  }

  /**
   * Displays the appropriate message for this event.
   * when the player is near it.
   */
  @Override
  public void drawUI() {
    if (!isFinished && player.checkCollision(teleportSprite)) {
      String message = "It appears you are lost, interact with this map to get back to campus!!!";

      float uiWidth = game.uiViewport.getWorldWidth();
      float wrapWidth = uiWidth * 0.8f;


      game.font.getData().setScale(1.1f);
      game.font.setColor(Color.WHITE);

      GlyphLayout layout = new GlyphLayout(game.font, message, 
          game.font.getColor(), wrapWidth, Align.center, true);

      float textX = (uiWidth - wrapWidth) / 2f;
      float textY = 50f + layout.height;

      game.font.draw(game.batch, layout, textX, textY);

      game.font.getData().setScale(1.1f);
      game.font.setColor(Color.WHITE);
    }
  }

  /**
   * Returns if the event has finished.
   * 
   * @return - True if the event is finished, false if not.
   */
  @Override
  public boolean IsFinished() {
    return isFinished;
  }
}

