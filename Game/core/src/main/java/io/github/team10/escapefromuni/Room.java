package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Represents a single Room.
 * A room can be connected to up to 4 adjacent rooms, and may contain an event.
 */
public class Room {

  private final ObjectMap<DoorDirection, Boolean> lockedDoors = new ObjectMap<>();
  private IEvent event;
  private final Room[] adjacentRooms = new Room[4];
  private final Texture roomTexture;
  private final boolean isExit;

  /**
   * Initialises a new room.
   * 
   * @param roomTexture The texture for this room.
   * @param isExit boolean representing whether reaching this room means the player wins.
   */
  public Room(Texture roomTexture, boolean isExit) {
    this.roomTexture = roomTexture;
    this.isExit = isExit;
  }

  /**
   * Initialises a new room, with isExit set to false (the case for most rooms).
   * 
   * @param texture The texture for this room.
   */
  public Room(Texture texture) {
    this(texture, false);
  }

  public Texture getRoomTexture() {
    return roomTexture;
  }

  public boolean isExit() {
    return isExit;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Sets a door to locked blocking the player from going through it.
   * 
   * @param dir direction of door to lock
   * @param locked boolean stating if its locked or not
   */
  public void setLocked(DoorDirection dir, boolean locked) {
    lockedDoors.put(dir, locked);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * States if a door is locked or not.
   * 
   * @param dir direction of the door
   * @return returns boolean value of if the door is locked
   */
  public boolean isLocked(DoorDirection dir) {
    return lockedDoors.get(dir, false);
  }

  /**
   * Adds a connection to an adjacent room, given a direction.
   * 
   * @param adjacentRoom The adjacent room to connect to.
   * @param direction Direction of the new room in relation to this room.
   */
  public void addAdjacent(Room adjacentRoom, DoorDirection direction) {
    adjacentRooms[direction.ordinal()] = adjacentRoom;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Removes a door from the room in the given direction.
   * 
   * @param room the room to remove the direction from
   * @param direction the direction of door to remove
   */
  public void removeAdjacent(Room room, DoorDirection direction) {
    if (adjacentRooms[direction.ordinal()] == room) {
      adjacentRooms[direction.ordinal()] = null;
    }
  }

  /**
   * Return a specific adjacent room based on a direction.
   *
   * @param direction Direction of the adjacent room to return.
   * @return Room representing adjacentRoom if it exists, null otherwise.
   */
  public Room getAdjacent(DoorDirection direction) {
    return adjacentRooms[direction.ordinal()];
  }

  /**
   * Returns the array storing adjacent rooms.
   * If a room doesn't exist in that direction null is stored instead.
   * 
   * @return Array of size 4, with order {NORTH, EAST, SOUTH, WEST}.
   */
  public Room[] getAllAdjacent() {
    return adjacentRooms;
  }

  public void setEvent(IEvent event) {
    this.event = event;
  }

  public IEvent getEvent() {
    return event;
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Gets event type for a room.
   * 
   * @return - Returns the correct event type, or null if there is no event for the given room
   */
  public EventType getEventType() {
    if (event != null) {
      return event.getType();
    }
    return EventType.NONE;
  }
}
