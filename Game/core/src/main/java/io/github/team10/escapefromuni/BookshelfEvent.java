package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BookshelfEvent implements IEvent {
    private final Player player;
    private final RoomFlowManager roomFlow;
    private boolean isFinished = false;
    private Sprite bookshelfSprite;
    private Texture bookshelfTexture;
    private final EscapeGame game;
    private final Room destinationRoom;
    private final EventSystem eventSystem;

    public BookshelfEvent(Player player, RoomFlowManager roomFlow, Room destinationRoom, EscapeGame game, EventSystem eventSystem){
        this.player = player;
        this.roomFlow = roomFlow;
        this.game = game;
        this.destinationRoom = destinationRoom;
        this.eventSystem = eventSystem;
    }

    @Override
    public EventType getType() {
        return EventType.HIDDEN;
    }

    @Override
    public void startEvent() {
        bookshelfTexture = new Texture("GreggsSausageRoll.png");
        bookshelfSprite = new Sprite(bookshelfTexture);

        bookshelfSprite.setAlpha(0f);
        bookshelfSprite.setSize(1f, 2f);
        float roomWidth = game.viewport.getWorldWidth();
        float roomHeight = game.viewport.getWorldHeight();
        bookshelfSprite.setPosition(roomWidth - bookshelfSprite.getWidth(), roomHeight / 2f - bookshelfSprite.getHeight() / 2f);
    }

    @Override
    public void endEvent() {
        bookshelfTexture.dispose();
    }

    @Override
    public void update(float delta) {
        if (isFinished){
            return;
        }

        if(player.checkCollision(bookshelfSprite) && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            eventSystem.registerEvent(EventType.HIDDEN);
            roomFlow.changeRoomTo(destinationRoom);
            isFinished = true;
        
            if(!roomFlow.bookshelfRoomVisited()){
                Room bookshelfRoom = roomFlow.getRoomByName("hiddenBookshelfRoom");
                Room room4 = roomFlow.getRoomByName("room4");
                roomFlow.removeEnterence(bookshelfRoom, room4, DoorDirection.EAST);
            }

        }
    }

    @Override
    public void draw() {
        if(!isFinished){
            bookshelfSprite.draw(game.batch);
        }
    }

    @Override
    public void drawUI() {
        if(!isFinished && player.checkCollision(bookshelfSprite)) {
            game.font.getData().setScale(1f);
            game.font.setColor(Color.BLACK);

            String message = "Something looks off here...";
            GlyphLayout layout = new GlyphLayout(game.font, message);
            
            float uiWidth = game.uiViewport.getWorldWidth();
            float uiHeight = game.uiViewport.getWorldHeight();
            float textX = (uiWidth - layout.width) / 2f;
            float textY = uiHeight * 0.9f;

            game.font.draw(game.batch, layout, textX, textY);

            game.font.getData().setScale(1.1f);
            game.font.setColor(Color.WHITE);


        }
    }

    @Override
    public boolean IsFinished() {
        return isFinished;
    }


}


