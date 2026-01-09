package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * NEW FOR ASSESSMENT 2.
 * This class is a hidden event
 * It moves the player into another room by interacting with it
 */
public class BookshelfEvent implements IEvent {
  private final Player player;
  private final RoomFlowManager roomFlow;
  private boolean isFinished = false;
  private Sprite bookshelfSprite;
  private Texture bookshelfTexture;
  private final EscapeGame game;
  private final Room destinationRoom;
  private final EventSystem eventSystem;

  /**
   * NEW FOR ASSESSMENT 2.
   * This is the class constructor for BookShelfEvent
   * It initalises the aspects of the game needed for the event
   * 
   * @param player the player controlled by the user
   * @param roomFlow the manager for room transitions
   * @param destinationRoom the disired room to head to
   * @param game the main LibGDX game instance
   * @param eventSystem the central events manager
   */
  public BookshelfEvent(Player player, RoomFlowManager roomFlow, 
      Room destinationRoom, EscapeGame game, EventSystem eventSystem) {
    this.player = player;
    this.roomFlow = roomFlow;
    this.game = game;
    this.destinationRoom = destinationRoom;
    this.eventSystem = eventSystem;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This returns the type of event, hidden
   */
  @Override
  public EventType getType() {
    return EventType.HIDDEN;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This starts the event in the game
   * It loads the texture and sprite as well as setting the position of it
   */
  @Override
  public void startEvent() {
    // creates the sprit and loads in the events texture
    bookshelfTexture = new Texture("GreggsSausageRoll.png");
    bookshelfSprite = new Sprite(bookshelfTexture);

    // sets the sprite to invinsible
    bookshelfSprite.setAlpha(0f);

    // calculates the position for the sprite as well as its size
    bookshelfSprite.setSize(1f, 2f);
    float roomWidth = game.viewport.getWorldWidth();
    float roomHeight = game.viewport.getWorldHeight();
    bookshelfSprite.setPosition(roomWidth - bookshelfSprite.getWidth(), 
        roomHeight / 2f - bookshelfSprite.getHeight() / 2f);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This ends the event by disposing of its texture
   */
  @Override
  public void endEvent() {
    bookshelfTexture.dispose();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This handles the events logic
   * It transports the player to the given room chosen
   */
  @Override
  public void update(float delta) {
    if (isFinished) {
      return;
    }

    // checks the player is interacting with the bookshelf and it touching it
    if (player.checkCollision(bookshelfSprite) && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
      eventSystem.registerEvent(EventType.HIDDEN);
      // changes the room to desired destination
      roomFlow.changeRoomTo(destinationRoom);
      isFinished = true;
        
      // removes the door to the bookshelf room upon visiting it
      // this means players do not get stuck in the room
      if (!roomFlow.bookshelfRoomVisited()) {
        Room bookshelfRoom = roomFlow.getRoomByName("hiddenBookshelfRoom");
        Room room4 = roomFlow.getRoomByName("room4");
        // removes the enterence to the room
        roomFlow.removeEnterence(bookshelfRoom, room4, DoorDirection.EAST);
      }

    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This draws the sprite into the game
   */
  @Override
  public void draw() {
    if (!isFinished) {
      bookshelfSprite.draw(game.batch);
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This draws the UI for the event
   * Displays a hint to help user find event
   */
  @Override
  public void drawUI() {
    if (!isFinished && player.checkCollision(bookshelfSprite)) {
    
      // sets the font and size of the text
      game.font.getData().setScale(1f);
      game.font.setColor(Color.BLACK);

      String message = "Something looks off here...";

      // Calculates text layout for propper positioning
      GlyphLayout layout = new GlyphLayout(game.font, message);
            
      float uiWidth = game.uiViewport.getWorldWidth();
      float uiHeight = game.uiViewport.getWorldHeight();

      // centres the text horizontally and positions it to the top of the screen
      float textX = (uiWidth - layout.width) / 2f;
      float textY = uiHeight * 0.9f;

      // Draws the message
      game.font.draw(game.batch, layout, textX, textY);

      // Resets the ui for other messages
      game.font.getData().setScale(1.1f);
      game.font.setColor(Color.WHITE);

    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This returns if the game is finished or not
   */
  @Override
  public boolean IsFinished() {
    return isFinished;
  }

}


