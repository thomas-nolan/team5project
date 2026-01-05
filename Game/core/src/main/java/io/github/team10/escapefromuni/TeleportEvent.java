package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;

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
        teleportTexture = new Texture("map.png");
        teleportSprite = new Sprite(teleportTexture);
        teleportSprite.setSize(3.5f, 3.5f);

        float width = game.viewport.getWorldWidth();
        float height = game.viewport.getWorldHeight();
        teleportSprite.setCenter(width - teleportSprite.getWidth() - 0.5f, height / 2f - teleportSprite.getHeight() /2f + 1.5f);

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
            String message = "It appears you are lost, interact with this map to get back to campus!!!";

            float uiWidth = game.uiViewport.getWorldWidth();
            float uiHeight = game.uiViewport.getWorldHeight();
            float wrapWidth = uiWidth * 0.8f;


            game.font.getData().setScale(1.1f);
            game.font.setColor(Color.WHITE);

            GlyphLayout layout = new GlyphLayout(game.font, message, game.font.getColor(), wrapWidth, Align.center, true);

            float textX = (uiWidth - wrapWidth) / 2f;
            float textY = 50f + layout.height;

            game.font.draw(game.batch, layout, textX, textY);

            game.font.getData().setScale(1.1f);
            game.font.setColor(Color.WHITE);
        }
    }

    @Override
    public boolean IsFinished() {
        return finished;
    }
    
}

