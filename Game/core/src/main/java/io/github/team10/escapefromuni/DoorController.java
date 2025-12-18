package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class DoorController {

    private final EscapeGame game;
    private final PlayerController playerController;
    private RoomFlowManager roomFlowManager;

    private Door[] doors = new Door[4];
    private final Texture positiveIndicator = new Texture("PositiveIndicator.png");
    private final Texture negativeIndicator = new Texture("NegativeIndicator.png");
    private Texture[] indicatorTextures = new Texture[4];
    private BitmapFont font = new BitmapFont();
    private boolean isTouchingDoor = false;

    public DoorController(EscapeGame game, PlayerController playerController, RoomFlowManager roomFlowManager){
        this.game = game;
        this.playerController = playerController;
        this.roomFlowManager = roomFlowManager;

        doors[0] = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        doors[1] = new Door(this, DoorDirection.EAST, 15f, 4f);
        doors[2] = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        doors[3] = new Door(this, DoorDirection.WEST, 0f, 4f);
        
        font.setColor(Color.RED);
        
    }
    public void updateForRoom(Room room) {
        
        // gets all adjacent rooms
        Room[] adj = room.getAllAdjacent();

        // loops through these rooms and if there is a room for the direction sets it to active
        for(int i = 0; i < 4; i++){
            doors[i].setActive(adj[i] != null);

            // sets the textures to null
            indicatorTextures[i] = null;

            // checks if the room has an event type and then displays its texture
            if (adj[i] != null) {
                EventType type = adj[i].getEventType();
                if(type == EventType.POSITIVE) indicatorTextures[i] = positiveIndicator;
                if(type == EventType.NEGATIVE) indicatorTextures[i] = negativeIndicator;

            }
        }

    }

    public Door getDoor(DoorDirection direction){
        return doors[direction.ordinal()];
    }




    public void setRoomFlowManager(RoomFlowManager roomFlowManager){
        this.roomFlowManager = roomFlowManager;
    }

    public EscapeGame getGame(){
        return game;
    }

    public void update() {
        // gets the player
        Player player = playerController.getPlayer();
        isTouchingDoor = false;

        // checks all the doors to see if the player is touching it and changes the room in that direction
        // ensures player moves to the next room when touching door
        for(Door d : doors){

            if (d.isActive && player.checkCollision(d.doorSprite)) {
                Room currentRoom = roomFlowManager.getCurrentRoom();
                if (currentRoom.isLocked(d.direction)){
                    if(player.hasKey()){
                        player.useKey();
                        currentRoom.setLocked(d.direction, false);
                    } else{
                        isTouchingDoor = true;
                        return;
                    }
                }
                roomFlowManager.changeRoom(d.direction);
                return;
            }
        }
    }

    public void draw() {
        // for all the doors draw them and adds indicator texture if they have one
        for (Door d: doors) d.draw();

        if(indicatorTextures [0] != null) game.batch.draw(indicatorTextures[0], 7.5f, 8f, 1f, 1f);
        if(indicatorTextures [1] != null) game.batch.draw(indicatorTextures[1], 15f, 4f, 1f, 1f);
        if(indicatorTextures [2] != null) game.batch.draw(indicatorTextures[2], 7.5f, 0f, 1f, 1f);
        if(indicatorTextures [3] != null) game.batch.draw(indicatorTextures[3], 0f, 4f, 1f, 1f);
    }

    public void drawUI(){
        if(isTouchingDoor) {
            font.getData().setScale(3f);
            String message = "This door is Locked! Find a key to get through.";
            GlyphLayout layout = new GlyphLayout(font, message);

            float uiWidth = game.uiViewport.getWorldWidth();
            float uiHeight = game.uiViewport.getWorldHeight();

            float textX = (uiWidth - layout.width) / 2f;
            float textY = uiHeight * 0.9f;

            font.draw(game.batch, layout, textX, textY);

        }



    }

    public void dispose() {
        for( Door d: doors) d.dispose();
        positiveIndicator.dispose();
        negativeIndicator.dispose();   
    }
}
