package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class RoomManager {
    public EscapeGame game;

    private Player player;
    private Room currentRoom;
    private Door[] doors = new Door[4];
    private final ObjectMap<String, Texture> roomTextures = new ObjectMap<>();

    public RoomManager(EscapeGame game, Player player)
    {
        this.game = game;
        this.player = player;
    }


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

        // Initialise connections
        room1.addAdjacent(room2, DoorDirection.EAST);
        room2.addAdjacent(room1, DoorDirection.WEST);
        room2.addAdjacent(room3, DoorDirection.NORTH);
        room3.addAdjacent(room2, DoorDirection.SOUTH);

        currentRoom = room1;
        updateDoors(currentRoom);

        System.out.println("Room1 EAST = " + room1.getAdjacent(DoorDirection.EAST));
        System.out.println("Room2 WEST = " + room2.getAdjacent(DoorDirection.WEST));
        System.out.println("Room2 NORTH = " + room2.getAdjacent(DoorDirection.NORTH));
        System.out.println("Room3 SOUTH = " + room3.getAdjacent(DoorDirection.SOUTH));

    }

    public void changeRoom(DoorDirection direction) {
        // Will unload current room and load next room
        // Will update which doors are visible. Note that only 4 door objects are used -
        // they can be made visible or invisible.
        Room newRoom = currentRoom.getAdjacent(direction);
        updateDoors(newRoom);

        currentRoom = newRoom;
        updatePlayerPosition(direction);
    }

    private void updatePlayerPosition(DoorDirection direction)
    {
        float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
        if(direction == DoorDirection.NORTH) player.setCenter(worldWidth / 2, 2);
        if(direction == DoorDirection.EAST) player.setCenter(2f, worldHeight / 2);
        if(direction == DoorDirection.SOUTH) player.setCenter(worldWidth / 2, worldHeight - 2);
        if(direction == DoorDirection.WEST) player.setCenter(worldWidth - 2, worldHeight / 2);
    }

    public void drawMap()
    {
        drawCurrentRoom();
        drawDoors();
    }

    private void drawCurrentRoom()
    {
        Texture roomTexture = currentRoom.getRoomTexture();
        float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
        game.batch.draw(roomTexture, 0, 0, worldWidth, worldHeight);
    }

    private void drawDoors()
    {
        for (Door door : doors)
        {
            door.draw();
        }
    }

    private void initialiseDoors() {
        // Will create the four doors at the start of the game
        Door northDoor = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        Door eastDoor = new Door(this, DoorDirection.EAST, 15f, 4f);
        Door southDoor = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        Door westDoor = new Door(this, DoorDirection.WEST, 0f, 4f);

        doors = new Door[]{northDoor, eastDoor, southDoor, westDoor};
    }

    private void updateDoors(Room newRoom) {
        // Called from changeRoom()
        // Will update which doors are visible based on the new room
        Room[] allAdjacent = newRoom.getAllAdjacent();
        for (int i = 0; i < 4; i++)
        {
            doors[i].setActive(allAdjacent[i] != null);
        }
    }

    private void updateEventIndicators() {
        // Called from changeRoom()
        // Will update the event type indicators by the doors
    }

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

    public void dispose()
    {
        for (Texture t : roomTextures.values()) {
            t.dispose();
        }

        for (Door door : doors)
        {
            door.dispose();
        }
    }
}
