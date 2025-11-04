package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Manages all {@link Room} and {@link Door} instances in the game.
 * 
 * Responsible for initialising the pre-defined game map, handling transitions between rooms, drawing room and door 
 * textures, and disposing of those textures when no longer needed.
 */
public class RoomManager {
    public EscapeGame game;

    private final Player player;
    private Room currentRoom;
    private Door[] doors = new Door[4];
    private final ObjectMap<String, Texture> roomTextures = new ObjectMap<>();
    private Texture[] indicatorTextures = new Texture[4];
    private Texture positiveIndicator;
    private Texture negativeIndicator;
    

    /**
     * Initialises a RoomManager.
     */
    public RoomManager(EscapeGame game, Player player)
    {
        this.game = game;
        this.player = player;
    }

    /**
     * Initialise the game map, which will be the same every time.
     * 
     * Initialise 4 doors, create rooms and create room connections. Room textures are stored in a list for easy 
     * access and disposal. Also loads the first room.
     */
    public void initialiseMap() {
        initialiseDoors();

        // Store room textures in list for easy access and disposal.
        roomTextures.put("room1", new Texture("RoomsTemp1.png"));
        roomTextures.put("room2", new Texture("RoomsTemp2.png"));
        roomTextures.put("room3", new Texture("RoomsTemp3.png"));

        // Iniitalise all the rooms
        Room room1 = new Room(roomTextures.get("room1"));
        Room room2 = new Room(roomTextures.get("room2"));
        Room room3 = new Room(roomTextures.get("room3"));

        // Initialise connections - remember both ways.
        room1.addAdjacent(room2, DoorDirection.EAST);
        room2.addAdjacent(room1, DoorDirection.WEST);
        room2.addAdjacent(room3, DoorDirection.NORTH);
        room3.addAdjacent(room2, DoorDirection.SOUTH);

        // Initialise Events
        room2.setEvent(new EventLongboi(player, game));

        currentRoom = room1;
        updateDoors(currentRoom);

        positiveIndicator = new Texture("PositiveIndicator.png");
        negativeIndicator = new Texture("NegativeIndicator.png");

        updateEventIndicators();
    }

    /**
     * Will change the current room, given the direction of the new room in relation to the current room.
     * 
     * Updates the active doors, and repositions the player to the appropriate entrance location in the new room.
     * @param direction The direction of the new room.
     */
    public void changeRoom(DoorDirection direction) {
        // Will unload current room and load next room
        // Will update which doors are visible. Note that only 4 door objects are used -
        // they can be made visible or invisible.
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().endEvent();
        }

        Room newRoom = currentRoom.getAdjacent(direction);
        updateDoors(newRoom);

        currentRoom = newRoom;
        updatePlayerPosition(direction);
        updateEventIndicators();

        if (newRoom.getEventType() != EventType.NONE)
        {
            newRoom.getEvent().startEvent();
        }
    }

    /**
     * Updates the player's position when changing rooms, to prevent the player colliding with the door the next frame.
     */
    private void updatePlayerPosition(DoorDirection direction)
    {
        float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
        if(direction == DoorDirection.NORTH) player.setCenter(worldWidth / 2, 2);
        if(direction == DoorDirection.EAST) player.setCenter(2f, worldHeight / 2);
        if(direction == DoorDirection.SOUTH) player.setCenter(worldWidth / 2, worldHeight - 2);
        if(direction == DoorDirection.WEST) player.setCenter(worldWidth - 2, worldHeight / 2);
    }

    /**
     * Draw the current room and it's active doors.
     */
    public void drawMap()
    {
        drawCurrentRoom();
        drawCurrentRoomEvent();
        drawDoors();
        drawIndicators();
    }

    private void drawCurrentRoom()
    {
        Texture roomTexture = currentRoom.getRoomTexture();
        float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
        game.batch.draw(roomTexture, 0, 0, worldWidth, worldHeight);
    }

    private void drawCurrentRoomEvent()
    {
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().draw();
        }
    }

    private void drawDoors()
    {
        for (Door door : doors)
        {
            door.draw();
        }
    }

    /**
     * Will draw event indicator textures, if required, by each door.
     */
    private void drawIndicators()
    {
        if (indicatorTextures[0] != null)
        {
            game.batch.draw(indicatorTextures[0], 7.5f, 8f, 1f, 1f);
        }
        if (indicatorTextures[1] != null)
        {
            game.batch.draw(indicatorTextures[1], 15f, 4f, 1f, 1f);
        }
        if (indicatorTextures[2] != null)
        {
            game.batch.draw(indicatorTextures[2], 7.5f, 0f, 1f, 1f);
        }
        if (indicatorTextures[3] != null)
        {
            game.batch.draw(indicatorTextures[3], 0f, 4f, 1f, 1f);
        }
    }

    /**
     * Draw the UI associated with any active events.
     */
    public void drawEventUI()
    {
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().drawUI();
        }
    }

    private void initialiseDoors() {
        // Will create the four doors at the start of the game.
        // Assumes a viewport size of width 16 and height 9.
        Door northDoor = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        Door eastDoor = new Door(this, DoorDirection.EAST, 15f, 4f);
        Door southDoor = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        Door westDoor = new Door(this, DoorDirection.WEST, 0f, 4f);

        doors = new Door[]{northDoor, eastDoor, southDoor, westDoor};
    }

    /**
     * Will update which doors are visible based on the new room
     */
    private void updateDoors(Room newRoom) {
        Room[] allAdjacent = newRoom.getAllAdjacent();
        for (int i = 0; i < 4; i++)
        {
            doors[i].setActive(allAdjacent[i] != null);
        }
    }

    /**
     * Update the event type indicators by the doors.
     */
    private void updateEventIndicators() {
        Room[] rooms = currentRoom.getAllAdjacent();
        for (int i = 0; i < 4; i++)
        {
            indicatorTextures[i] = null;

            // Check Room actually exists before trying to access event type.
            if (rooms[i] == null) continue;

            if (rooms[i].getEventType() == EventType.POSITIVE)
            {
                indicatorTextures[i] = positiveIndicator;
            }
            else if (rooms[i].getEventType() == EventType.NEGATIVE)
            {
                indicatorTextures[i] = negativeIndicator;
            }
        }
    }

    public void update(float delta)
    {
        checkDoorCollision();
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().update(delta);
        }
    }

    /**
     * Check whether the player is colliding with any of the doors.
     */
    public void checkDoorCollision()
    {
        for (Door door : doors)
        {
            if (door.isActive && player.checkCollision(door.doorSprite))
            {
                changeRoom(door.direction);
                return;
            }
        }
    }

    /**
     * Dispose of all room and door textures.
     */
    public void dispose()
    {
        for (Texture t : roomTextures.values()) {
            t.dispose();
        }

        for (Door door : doors)
        {
            door.dispose();
        }

        positiveIndicator.dispose();
        negativeIndicator.dispose();
    }
}
