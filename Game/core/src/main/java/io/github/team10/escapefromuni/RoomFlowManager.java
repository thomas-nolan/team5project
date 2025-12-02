package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class RoomFlowManager {

    private final EscapeGame game;
    private final UIController uiController;
    private final PlayerController playerController;
    private DoorController doorController;
    private final EventSystem eventSystem;
    private final ScoreManager scoreManager;
    private final Timer timer;
    private final ObjectMap<String, Texture> roomTextures = new ObjectMap<>();
    private Room currentRoom;

    public RoomFlowManager(EscapeGame game, UIController uiController, PlayerController playerController,
        DoorController doorController, EventSystem eventSystem, ScoreManager scoreManager, Timer timer){

        this.game = game;
        this.uiController = uiController;
        this.playerController = playerController;
        this.doorController = doorController;
        this.eventSystem = eventSystem;
        this.scoreManager = scoreManager;
        this.timer = timer;
    }

    public void initialiseMap() {

        // Store room textures in list for easy access and disposal.
        roomTextures.put("room1", new Texture("Room1.png"));
        roomTextures.put("room2", new Texture("Room3.png"));
        roomTextures.put("room3", new Texture("Room4.png"));
        roomTextures.put("room4", new Texture("Room7.png"));
        roomTextures.put("room5", new Texture("Room6.png"));
        roomTextures.put("room6", new Texture("Room8.png"));
        roomTextures.put("room7", new Texture("Room5.png"));
        roomTextures.put("room8", new Texture("Room9.png"));
        roomTextures.put("room9", new Texture("Room10.png"));

        // Iniitalise all the rooms
        // TODO: Update room textures, and add more rooms.
        Room room1 = new Room(roomTextures.get("room1"));
        Room room2 = new Room(roomTextures.get("room2"));
        Room room3 = new Room(roomTextures.get("room3"));
        Room room4 = new Room(roomTextures.get("room4"));
        Room room5 = new Room(roomTextures.get("room5"));
        Room room6 = new Room(roomTextures.get("room6"));
        Room room7 = new Room(roomTextures.get("room7"));
        Room room8 = new Room(roomTextures.get("room8"));
        Room room9 = new Room(roomTextures.get("room9"));

        // Exit room is not actually displayed - game ends as soon as player steps inside.
        Room exit = new Room(roomTextures.get("room1"), true);


        // Initialise connections - remember both ways.
        room1.addAdjacent(room2, DoorDirection.EAST);
        room2.addAdjacent(room1, DoorDirection.WEST);

        room2.addAdjacent(room3, DoorDirection.NORTH);
        room3.addAdjacent(room2, DoorDirection.SOUTH);
        room2.addAdjacent(room4, DoorDirection.EAST);
        room4.addAdjacent(room2, DoorDirection.WEST);
        room2.addAdjacent(room6, DoorDirection.SOUTH);
        room6.addAdjacent(room2, DoorDirection.NORTH);

        room4.addAdjacent(room5, DoorDirection.SOUTH);
        room5.addAdjacent(room4, DoorDirection.NORTH);

        room5.addAdjacent(room6, DoorDirection.WEST);
        room6.addAdjacent(room5, DoorDirection.EAST);
        room5.addAdjacent(room7, DoorDirection.SOUTH);
        room7.addAdjacent(room5, DoorDirection.NORTH);
        room5.addAdjacent(room8, DoorDirection.EAST);
        room8.addAdjacent(room5, DoorDirection.WEST);

        room8.addAdjacent(room9, DoorDirection.EAST);
        room9.addAdjacent(room8, DoorDirection.WEST);

        room9.addAdjacent(exit, DoorDirection.EAST);


        // Initialise Events
        room7.setEvent(new EventLongboi(playerController.getPlayer(), game));
        room3.setEvent(new EventGreggs(playerController.getPlayer(), game));
        room5.setEvent(new EventTHE3(playerController.getPlayer(), game, scoreManager));
        room4.setEvent(new EventFreeze(playerController.getPlayer(), game, timer));

        currentRoom = room1;

        doorController.updateForRoom(currentRoom);
        eventSystem.onEnterRoom(currentRoom);
    }

    public void setDoorController(DoorController doorController) {
        this.doorController = doorController;
    }

    public void changeRoom(DoorDirection direction) {
        // Will unload current room and load next room
        // Will update which doors are visible. Note that only 4 door objects are used -
        // they can be made visible or invisible.

        Room newRoom = currentRoom.getAdjacent(direction);
        if (newRoom == null){
            return;
        }

        eventSystem.onExitRoom(currentRoom);

        currentRoom = newRoom;

        doorController.updateForRoom(newRoom);
        playerController.positionAfterRoomChange(direction);
        eventSystem.onEnterRoom(newRoom);

        if (newRoom.isExit())
        {
            GameplayStateManager.triggerWin(game, uiController, timer, scoreManager);
        }

    }
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void drawCurrentRoom() {
        Texture t = currentRoom.getRoomTexture();
        float w = game.viewport.getWorldWidth();
        float h = game.viewport.getWorldHeight();
        game.batch.draw(t,0,0, w, h);
    }

    public void dispose(){
        for (Texture t: roomTextures.values()){
            t.dispose();
        }
    }

}
