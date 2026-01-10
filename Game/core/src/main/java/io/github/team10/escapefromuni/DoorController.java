package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * NEW FOR ASSESSMENT 2.
 * This class acts as a controller for the door class
 * It breaks up the logic from the old god class
 * Controls the doors within a room setting their directions as well as which room they lead to
 */
public class DoorController {

  private final EscapeGame game;
  private final PlayerController playerController;
  private RoomFlowManager roomFlowManager;
  private final EventSystem eventSystem;

  private Door[] doors = new Door[4];
  private final Texture positiveIndicator = new Texture("PositiveIndicator.png");
  private final Texture negativeIndicator = new Texture("NegativeIndicator.png");
  private Texture[] indicatorTextures = new Texture[4];
  private BitmapFont font = new BitmapFont();
  private boolean isTouchingDoor = false;
  private boolean addedEvent = false;

  /**
   * NEW FOR ASSESSMENT 2.
   * This is a constructor which initialises the variables needed for the room controller
   * It also sets the direction of the doors as well as the font colour for locked doors
   *
   * @param game the main LibGDX game instance
   * @param playerController the controller used handling the player
   * @param roomFlowManager  the manager for room transitions
   * @param eventSystem the central events manager
   */
  public DoorController(EscapeGame game, PlayerController playerController,
       RoomFlowManager roomFlowManager, EventSystem eventSystem) {
    this.game = game;
    this.playerController = playerController;
    this.roomFlowManager = roomFlowManager;
    this.eventSystem = eventSystem;

    // creates a new door in each direction as well setting the position of them in the room
    doors[0] = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
    doors[1] = new Door(this, DoorDirection.EAST, 15f, 4f);
    doors[2] = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
    doors[3] = new Door(this, DoorDirection.WEST, 0f, 4f);

    // NEW FOR ASSESSMENT 2 - sets the colour of the font for locked door message
    font.setColor(Color.RED);

  }

  /**
   * This method sets all the doors in the room to active.
   * It also draws the event indicators onto the door
   *
   * @param room the room to set active
   */
  public void updateForRoom(Room room) {

    // gets all adjacent rooms
    Room[] adj = room.getAllAdjacent();

    // loops through these rooms and if there is a room for the direction sets it to active
    for (int i = 0; i < 4; i++) {
      doors[i].setActive(adj[i] != null);

      // sets the textures to null
      indicatorTextures[i] = null;

      // checks if the room has an event type and then displays its texture
      if (adj[i] != null) {
        EventType type = adj[i].getEventType();
        if (type == EventType.POSITIVE) {
          indicatorTextures[i] = positiveIndicator;
        }
        if (type == EventType.NEGATIVE) {
          indicatorTextures[i] = negativeIndicator;
        }
      }
    }
  }

  /**
   * This method gets the door in the specified direction.
   *
   * @param direction The direction of the door to retrieve
   * @return The {@link Door} object in the specified direction
   */
  public Door getDoor(DoorDirection direction) {
    return doors[direction.ordinal()];
  }

  /**
   * Sets the {@link roomFlowManager} for this object.
   *
   * @param roomFlowManager The {@link roomFlowManager} to associate with this object
   */
  public void setRoomFlowManager(RoomFlowManager roomFlowManager) {
    this.roomFlowManager = roomFlowManager;
  }

  /**
   * Returns the main LibGDX game instance.
   *
   * @return The main {@link EscapeGame} instance
   */
  public EscapeGame getGame() {
    return game;
  }

  /**
   * This method handles the logic when a player passes through doors.
   * It checks the doors and changes the room in that direction
   * Handles what happens when a player encounters a locked door
   */
  public void update() {
    // gets the player
    Player player = playerController.getPlayer();
    isTouchingDoor = false;

    // Checks all the doors to see if the player is touching it
    // Changes the room in that direction
    // Ensures player moves to the next room when touching door
    for (Door d : doors) {

      if (d.isActive && player.checkCollision(d.doorSprite)) {
        Room currentRoom = roomFlowManager.getCurrentRoom();
        // NEW FOR ASSESSMENT 2
        // Checks if the door is locked and only lets the player through if they have a key
        if (currentRoom.isLocked(d.direction)) {
          if (player.hasKey()) {
            player.useKey();
            currentRoom.setLocked(d.direction, false);
          } else {
            isTouchingDoor = true;
            return;
          }
        }
        roomFlowManager.changeRoom(d.direction);
        return;
      }
    }
  }

  /**
   * This method handles drawing the doors and their event indicators.
   */
  public void draw() {
    // for all the doors draw them
    for (Door d : doors) {
      d.draw();
    }

    // This section adds an event indicator texture if the door has one
    if (indicatorTextures [0] != null) {
      game.batch.draw(indicatorTextures[0], 7.5f, 8f, 1f, 1f);
    }
    if (indicatorTextures [1] != null) {
      game.batch.draw(indicatorTextures[1], 15f, 4f, 1f, 1f);
    }
    if (indicatorTextures [2] != null) {
      game.batch.draw(indicatorTextures[2], 7.5f, 0f, 1f, 1f);
    }
    if (indicatorTextures [3] != null) {
      game.batch.draw(indicatorTextures[3], 0f, 4f, 1f, 1f);
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Draws the ui related to the door
   * If the player is touching the door and its locked, it display the message
   */
  public void drawUI() {
    if (isTouchingDoor) {
      // NEW FOR ASSESSMENT 2
      // Used to register the event as negative upon touching the door
      if (addedEvent == false) {
        eventSystem.registerEvent(EventType.NEGATIVE);
        addedEvent = true;
      }

      // sets the font scale, message and calculates text layout for propper positioning
      font.getData().setScale(3f);
      String message = "This door is Locked! Find a key to get through.";
      GlyphLayout layout = new GlyphLayout(font, message);

      float uiWidth = game.uiViewport.getWorldWidth();
      float uiHeight = game.uiViewport.getWorldHeight();

      // calculates the position of the text, centred horizontally and placed near the top
      float textX = (uiWidth - layout.width) / 2f;
      float textY = uiHeight * 0.9f;

      font.draw(game.batch, layout, textX, textY);

    }

  }

  /**
   * Cleans up the asset used in the class.
   */
  public void dispose() {
    for (Door d : doors) {
      d.dispose();
    }
    positiveIndicator.dispose();
    negativeIndicator.dispose();
  }
}
