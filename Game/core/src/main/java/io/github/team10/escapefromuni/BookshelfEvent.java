package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BookshelfEvent implements IEvent {
    private final Player player;
    private final RoomFlowManager roomFlow;
    private boolean isFinished = false;
    private Sprite bookshelfSprite;
    private Texture bookshelfTexture;
    private final EscapeGame game;
    private final Room destinationRoom;

    public BookshelfEvent(Player player, RoomFlowManager roomFlow, Room destinationRoom, EscapeGame game){
        this.player = player;
        this.roomFlow = roomFlow;
        this.game = game;
        this.destinationRoom = destinationRoom;
    }

    @Override
    public EventType getType() {
        return EventType.HIDDEN;
    }

    @Override
    public void startEvent() {
        bookshelfTexture = new Texture("GreggsSausageRoll.png");
        bookshelfSprite = new Sprite(bookshelfTexture);
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
            game.font.draw(game.batch, "This looks weird", bookshelfSprite.getX(), bookshelfSprite.getY());

        }
    }

    @Override
    public boolean IsFinished() {
        return isFinished;
    }


}
