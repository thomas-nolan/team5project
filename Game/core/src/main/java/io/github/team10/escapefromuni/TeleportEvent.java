package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TeleportEvent implements IEvent{

    private final Player player;
    private final RoomFlowManager roomFlow;
    private final EscapeGame game;
    private final EventSystem eventSystem;

    private Sprite teleportSprite;
    private Texture teleportTexture;

    private boolean finished = false;

    public TeleportEvent (Player player, RoomFlowManager roomFlow, EscapeGame game, EventSystem eventSystem){
        this.player = player;
        this.roomFlow = roomFlow;
        this.game = game;
        this.eventSystem = eventSystem;

    }

    @Override
    public EventType getType() {
        return EventType.HIDDEN;
    }

    @Override
    public void startEvent() {
        teleportTexture = new Texture("GreggsSausageRoll.png");
        teleportSprite = new Sprite(teleportTexture);
        teleportSprite.setSize(2f, 2f);

        float width = game.viewport.getWorldWidth();
        float height = game.viewport.getWorldHeight();
        teleportSprite.setCenter(width/2f, height/2f);

    }

    @Override
    public void endEvent() {
        teleportTexture.dispose();
    }

    @Override
    public void update(float delta) {
       if(finished) return;

       if (player.checkCollision(teleportSprite) && Gdx.input.isKeyJustPressed(Input.Keys.F)){

            Room destination = roomFlow.getRandomRoom(roomFlow.getRoomByName("hiddenBookshelfRoom"));
            
            if(destination != null){
                eventSystem.registerEvent(EventType.HIDDEN);
                roomFlow.changeRoomTo(destination);
                finished = true;
            }
       }
    }

    @Override
    public void draw() {
        if(!finished){
            teleportSprite.draw(game.batch);
        }
    }

    @Override
    public void drawUI() {
        if(!finished && player.checkCollision(teleportSprite)) {
            game.font.draw(game.batch, "This looks weird", teleportSprite.getX(), teleportSprite.getY() + 2.5f);
        }
    }

    @Override
    public boolean IsFinished() {
        return finished;
    }
    
}
