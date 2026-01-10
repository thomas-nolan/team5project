package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * New for Assessment 2
 *
 * A class that manages the rooms in the map
 */
public class RoomFlowManager {

    private final EscapeGame game;
    private final UIController uiController;
    private final PlayerController playerController;
    private DoorController doorController;
    private final EventSystem eventSystem;
    private final ScoreManager scoreManager;
    private final AchievementManager achievementManager;
    private final Timer timer;
    private final ObjectMap<String, Texture> roomTextures = new ObjectMap<>();
    private Room currentRoom;
    private Dean dean;
    private final Array<Room> allRooms = new Array<>();
    private boolean bookshelfRoomVisited = false;
    private boolean keyRoomVisited = false;

    /**
     * The constructor for the class
     * @param game
     * @param uiController
     * @param playerController
     * @param doorController
     * @param eventSystem
     * @param scoreManager
     * @param timer
     * @param achievementManager
     */
    public RoomFlowManager(EscapeGame game, UIController uiController, PlayerController playerController,
        DoorController doorController, EventSystem eventSystem, ScoreManager scoreManager, Timer timer, AchievementManager achievementManager){

        this.game = game;
        this.uiController = uiController;
        this.playerController = playerController;
        this.doorController = doorController;
        this.eventSystem = eventSystem;
        this.scoreManager = scoreManager;
        this.timer = timer;
        this.achievementManager = achievementManager;
    }

    /**
     * Initialises the game map and all of its rooms
     * It also defines which rooms lead to each other and in what direction
     * Also responsible for initialising the Dean
     */
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
        roomTextures.put("keyRoom", new Texture("PossibleRoom1.png"));
        roomTextures.put("hiddenBookshelfRoom", new Texture("BookshelfRoom.png"));
        roomTextures.put("lostStudentRoom", new Texture("Room8.png"));

        // Iniitalises all the rooms
        Room room1 = new Room(roomTextures.get("room1"));
        Room room2 = new Room(roomTextures.get("room2"));
        Room room3 = new Room(roomTextures.get("room3"));
        Room room4 = new Room(roomTextures.get("room4"));
        Room room5 = new Room(roomTextures.get("room5"));
        Room room6 = new Room(roomTextures.get("room6"));
        Room room7 = new Room(roomTextures.get("room7"));
        Room room8 = new Room(roomTextures.get("room8"));
        Room room9 = new Room(roomTextures.get("room9"));
        room8.setLocked(DoorDirection.EAST, true);
        Room keyRoom = new Room(roomTextures.get("keyRoom"));
        float roomWidth = game.viewport.getWorldWidth();
        float roomHeight = game.viewport.getWorldHeight();
        dean = new Dean(0.6f, 1f, 1f, game, roomWidth, roomHeight);
        Room hiddenBookshelfRoom = new Room(roomTextures.get("hiddenBookshelfRoom"));
        Room lostStudentRoom = new Room(roomTextures.get("lostStudentRoom"));


        allRooms.add(room1);
        allRooms.add(room2);
        allRooms.add(room3);
        allRooms.add(room4);
        allRooms.add(room5);
        allRooms.add(room6);
        allRooms.add(room7);
        allRooms.add(room8);
        allRooms.add(hiddenBookshelfRoom);

        // Exit room is not actually displayed - game ends as soon as player steps inside.
        Room exit = new Room(roomTextures.get("room1"), true);


        // Initialise room connections - remember both ways.
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

        room3.addAdjacent(keyRoom, DoorDirection.NORTH);
        keyRoom.addAdjacent(room3, DoorDirection.SOUTH);

        room4.addAdjacent(hiddenBookshelfRoom, DoorDirection.EAST);


        // Initialise Events
        room7.setEvent(new EventLongboi(playerController.getPlayer(), game, achievementManager, eventSystem));
        room3.setEvent(new EventGreggs(playerController.getPlayer(), game, eventSystem));
        room5.setEvent(new EventTHE3(playerController.getPlayer(), game, scoreManager, achievementManager, eventSystem));
        room4.setEvent(new EventFreeze(playerController.getPlayer(), game, timer, eventSystem));
        room6.setEvent(new BearTrapEvent(game, playerController.getPlayer(), timer, eventSystem));
        keyRoom.setEvent(new KeyEvent(playerController.getPlayer(), game, eventSystem));
        hiddenBookshelfRoom.setEvent(new BookshelfEvent(playerController.getPlayer(), this, lostStudentRoom, game, eventSystem));
        lostStudentRoom.setEvent(new TeleportEvent(playerController.getPlayer(), this, game, eventSystem));
        room8.setEvent(new InvincibleEvent(playerController.getPlayer(), game, eventSystem));

        currentRoom = room1;

        doorController.updateForRoom(currentRoom);
        eventSystem.onEnterRoom(currentRoom);

    }

    /**
     * Gets a random room
     * @param exclude - Room to exclude
     * @return - A random valid room
     */
    public Room getRandomRoom(Room exclude){
        Array<Room> validRooms = new Array<>();
        for (Room r: allRooms){
            if(r != exclude ){
                validRooms.add(r);
            }
        }
        return validRooms.random();
    }

    /**
     * Returns a Room based on its name
     * @param room - The name of a room
     * @return - A room with the given name or null if none found
     */
    public Room getRoomByName(String room){
        for (Room r: allRooms){
            if (r.getRoomTexture() == roomTextures.get(room)){
                return r;
            }
        }
        return null;
    }

    /**
     * Getter for bookshelfRoomVisited
     * @return - True if the bookshelf room is visited, false if not
     */
    public boolean bookshelfRoomVisited(){
        return bookshelfRoomVisited;
    }

    /**
     * Removes the entrance in the bookshelf room after it is activated
     * @param bookshelfRoom - The bookshelf room
     * @param roomFrom - The room the player entered the bookshelf room from
     * @param direction - The door direction
     */
    public void removeEnterence(Room bookshelfRoom, Room roomFrom, DoorDirection direction){
        roomFrom.removeAdjacent(bookshelfRoom, direction);
    }

    /**
     * Registers negative events for rooms that contain them
     * @param room
     */
    public void handleNegativeRoomEntry(Room room){
        if (room.getEvent() instanceof KeyEvent && !keyRoomVisited){
            keyRoomVisited = true;
            eventSystem.registerEvent(EventType.NEGATIVE);
        }

        if (room.getEvent() instanceof BookshelfEvent && !bookshelfRoomVisited){
            eventSystem.registerEvent(EventType.NEGATIVE);
        }

    }

    /**
     * Changes the room the player is in when entering a door
     * @param room - The room to enter
     */
    public void changeRoomTo(Room room){
        eventSystem.onExitRoom(currentRoom);
        currentRoom = room;
        doorController.updateForRoom(room);

        float centerX = game.viewport.getWorldWidth() / 2f;
        float centerY = game.viewport.getWorldHeight() / 2f;
        playerController.getPlayer().setCenter(centerX, centerY);

        eventSystem.onEnterRoom(currentRoom);
        handleNegativeRoomEntry(currentRoom);

    }

    /**
     * Runs each frame, updates game data
     * @param delta
     */
    public void update(float delta) {
        playerController.update(delta);

        if (currentRoom.getEvent() instanceof KeyEvent){
            dean.update(delta);

            if (dean.checkReached(playerController.getPlayer())){
                GameplayStateManager.triggerLose(game, uiController, timer, scoreManager);
        }
        }

    }

    /**
     * Setter method for doorController
     * @param doorController - New DoorController
     */
    public void setDoorController(DoorController doorController) {
        this.doorController = doorController;
    }

    /**
     * Loads a new room to replace the old one
     * Updates which doors are visible
     * Triggers a win if the player enters the exit door
     * @param direction
     */
    public void changeRoom(DoorDirection direction) {

        Room newRoom = currentRoom.getAdjacent(direction);
        if (newRoom == null){
            return;
        }

        eventSystem.onExitRoom(currentRoom);

        currentRoom = newRoom;

        doorController.updateForRoom(newRoom);
        playerController.positionAfterRoomChange(direction);
        eventSystem.onEnterRoom(newRoom);
        handleNegativeRoomEntry(newRoom);

        if (newRoom.isExit())
        {
            achievementManager.addAchievement("Complete game");
            GameplayStateManager.triggerWin(game, uiController, timer, scoreManager);
        }

    }

    /**
     * Getter for the current room
     * @return - currrentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Draws the current room and the dean if necessary
     */
    public void drawCurrentRoom() {
        Texture t = currentRoom.getRoomTexture();
        float w = game.viewport.getWorldWidth();
        float h = game.viewport.getWorldHeight();
        game.batch.draw(t,0,0, w, h);

        if (currentRoom.getEvent() instanceof KeyEvent) {
            dean.draw();
        }
    }

    /**
     * Disposes of textures
     */
    public void dispose(){
        for (Texture t: roomTextures.values()){
            t.dispose();
        }
    }

}
