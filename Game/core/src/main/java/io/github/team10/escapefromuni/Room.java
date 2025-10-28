package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;

public class Room {
    public RoomManager roomManager;
    public Event event;
    private Room[] adjacentRooms = new Room[4];

    private Texture roomTexture;

    public Room(Texture roomTexture)
    {
        this.roomTexture = roomTexture;
    }

    public Texture getRoomTexture()
    {
        return roomTexture;
    }

    public void addAdjacent(Room adjacentRoom, DoorDirection direction)
    {
        System.out.println("Adding adjacent: " + direction + " -> " + adjacentRoom);
        if (direction == DoorDirection.NORTH) adjacentRooms[0] = adjacentRoom;
        if (direction == DoorDirection.EAST) adjacentRooms[1] = adjacentRoom;
        if (direction == DoorDirection.SOUTH) adjacentRooms[2] = adjacentRoom;
        if (direction == DoorDirection.WEST) adjacentRooms[3] = adjacentRoom;
    }

    public Room getAdjacent(DoorDirection direction)
    {
        if (direction == DoorDirection.NORTH) return adjacentRooms[0];
        if (direction == DoorDirection.EAST) return adjacentRooms[1];
        if (direction == DoorDirection.SOUTH) return adjacentRooms[2];
        if (direction == DoorDirection.WEST) return adjacentRooms[3];
        return null;
    }

    public Room[] getAllAdjacent()
    {
        return adjacentRooms;
    }

    /* 
    public EventType getEventType() {
        // Return the event type of event
    }

    
    public void start() {
        // Actions to perform when room is started i.e. play event
    }*/
}
