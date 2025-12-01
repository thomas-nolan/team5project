package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;

public class DoorController {

    private final EscapeGame game;
    private final PlayerController playerController;
    private RoomFlowManager roomFlowManager;

    private Door[] doors = new Door[4];
    private final Texture positiveIndicator = new Texture("PositiveIndicator.png");
    private final Texture negativeIndicator = new Texture("NegativeIndicator.png");
    private Texture[] indicatorTextures = new Texture[4];

    public DoorController(EscapeGame game, PlayerController playerController, RoomFlowManager roomFlowManager){
        this.game = game;
        this.playerController = playerController;
        this.roomFlowManager = roomFlowManager;

        doors[0] = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        doors[1] = new Door(this, DoorDirection.EAST, 15f, 4f);
        doors[2] = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        doors[3] = new Door(this, DoorDirection.WEST, 0f, 4f);
        
    }
    public void updateForRoom(Room room) {
        
        Room[] adj = room.getAllAdjacent();

        for(int i = 0; i < 4; i++){
            doors[i].setActive(adj[i] != null);

            indicatorTextures[i] = null;

            if (adj[i] != null) {
                EventType type = adj[i].getEventType();
                if(type == EventType.POSITIVE) indicatorTextures[i] = positiveIndicator;
                if(type == EventType.NEGATIVE) indicatorTextures[i] = negativeIndicator;

            }
        }

    }

    public void setRoomFlowManager(RoomFlowManager roomFlowManager){
        this.roomFlowManager = roomFlowManager;
    }

    public EscapeGame getGame(){
        return game;
    }

    public void update() {
        Player player = playerController.getPlayer();

        for(Door d : doors){
            if (d.isActive && player.checkCollision(d.doorSprite)) {
                roomFlowManager.changeRoom(d.direction);
                return;
            }
        }
    }

    public void draw() {
        for (Door d: doors) d.draw();

        if(indicatorTextures [0] != null) game.batch.draw(indicatorTextures[0], 7.5f, 8f, 1f, 1f);
        if(indicatorTextures [1] != null) game.batch.draw(indicatorTextures[1], 15f, 4f, 1f, 1f);
        if(indicatorTextures [2] != null) game.batch.draw(indicatorTextures[2], 7.5f, 0f, 1f, 1f);
        if(indicatorTextures [3] != null) game.batch.draw(indicatorTextures[3], 0f, 4f, 1f, 1f);
    }

    public void dispose() {
        for( Door d: doors) d.dispose();
        positiveIndicator.dispose();
        negativeIndicator.dispose();   
    }
}
