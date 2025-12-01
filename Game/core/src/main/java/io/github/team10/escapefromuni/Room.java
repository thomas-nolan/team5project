package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a single Room.
 * 
 * A room can be connected to up to 4 adjacent rooms, and may contain an event.
 */
public class Room {

    private IEvent event;
    private final Room[] adjacentRooms = new Room[4];
    private final Texture roomTexture;
    private final boolean isExit;

    /**
     * Initialises a new room.
     * @param roomTexture The texture for this room.
     * @param isExit boolean representing whether reaching this room means the player wins.
     */
    public Room(Texture roomTexture, boolean isExit)
    {
        this.roomTexture = roomTexture;
        this.isExit = isExit;
    }

    /**
     * Initialises a new room, with isExit set to false (the case for most rooms).
     * @param roomTexture The texture for this room.
     */
    public Room(Texture texture)
    {
        this(texture, false);
    }

    public Texture getRoomTexture()
    {
        return roomTexture;
    }

    public boolean isExit(){
        return isExit;
    }

    /**
     * Adds a connection to an adjacent room, given a direction. 
     * @param adjacentRoom The adjacent room to connect to.
     * @param direction Direction of the new room in relation to this room.
     */
    public void addAdjacent(Room adjacentRoom, DoorDirection direction)
    {
       adjacentRooms[direction.ordinal()] = adjacentRoom;
    }

    /**
     * Return a specific adjacent room based on a direction.
     * 
     * @param direction Direction of the adjacent room to return.
     * @return Room representing adjacentRoom if it exists, null otherwise.
     */
    public Room getAdjacent(DoorDirection direction)
    {
        return adjacentRooms[direction.ordinal()];

    }

    /**
     * Returns the array storing adjacent rooms.
     * 
     * If a room doesn't exist in that direction null is stored instead.
     * @return Array of size 4, with order {NORTH, EAST, SOUTH, WEST}.
     */
    public Room[] getAllAdjacent()
    {
        return adjacentRooms;
    }

    public void setEvent(IEvent event)
    {
        this.event = event;
    }

    public IEvent getEvent()
    {
        return event;
    }

    public EventType getEventType() {
        if (event != null)
        {
            return event.getType();
        }
        return EventType.NONE;
    }
}
