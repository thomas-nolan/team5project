package io.github.team10.escapefromuni;

public class PlayerController {
    
    public final EscapeGame game;
    public final Player player;

    public PlayerController(EscapeGame game, Player player){
        this.game = game;
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }
    
    public void positionAfterRoomChange(DoorDirection direction){

        float w = game.viewport.getWorldWidth();
        float h = game.viewport.getWorldHeight();

        switch (direction) {
            case NORTH:
                player.setCenter(w/2, 2);
                break;
            case EAST:
                player.setCenter(2f, h/2);
                break;
            case SOUTH:
                player.setCenter(w/2, h-2);
                break;
            case WEST:
                player.setCenter(w-2, h/2);
                break;
        }
    }

    public void update(float delta) {
        player.update(delta);
    }

    public void drawPlayer() {
        player.draw();
    }

    public void dispose() {
        player.dispose();
    }
}
