package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class EventBookshelf implements IEvent {
    
    private final Player player;
    private final EscapeGame game;
    private final RoomFlowManager roomFlowManager;
    private final Room hiddenRoom;
    private final Room originalRoom;
    private final EventType type;
    
    private Texture bookshelfTexture;
    private Sprite bookshelfSprite;
    
    private boolean eventFinished;
    
    private boolean isInHiddenRoom;
    
    public EventBookshelf(Player player, EscapeGame game, RoomFlowManager roomFlowManager, 
                         Room hiddenRoom, Room originalRoom) {
        this.player = player;
        this.game = game;
        this.roomFlowManager = roomFlowManager;
        this.hiddenRoom = hiddenRoom;
        this.originalRoom = originalRoom;
        this.isInHiddenRoom = false;
        this.type = EventType.HIDDEN;
    }
    
    @Override
    public void update(float delta) {
        if (getPlayerDistance() < 1f && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            interact();
        }
    }
    
    @Override
    public void startEvent() {
        if (eventFinished) return;

        bookshelfTexture = new Texture("BookshelfTemp.jpg");
        bookshelfSprite = new Sprite(bookshelfTexture);
        bookshelfSprite.setSize(2f, 2f);
        bookshelfSprite.setPosition(2f, 3f);
    }
    
    private float getPlayerDistance()
    {
    	Vector2 playerPos = player.getCenter();
        float bookshelfX = bookshelfSprite.getX() + bookshelfSprite.getWidth() / 2f;
        float bookshelfY = bookshelfSprite.getY() + bookshelfSprite.getHeight() / 2f;
        Vector2 bookshelfCenter = new Vector2(bookshelfX, bookshelfY);
        return bookshelfCenter.dst(playerPos);
    }
    
    private void interact() {
        if (isInHiddenRoom) {
            roomFlowManager.goToRoom(originalRoom);
            isInHiddenRoom = false;
        }
        else {
            roomFlowManager.goToRoom(hiddenRoom);
            isInHiddenRoom = true;
        }
    }
    
    @Override
    public void draw() {
        bookshelfSprite.draw(game.batch);
    }
    
    @Override
    public boolean IsFinished() {
        return eventFinished;
    }
    
    private void dispose() {
        bookshelfTexture.dispose();
    }
    
    @Override
    public void endEvent() {
        dispose();
    }

	@Override
	public EventType getType() {
		return type;
	}

    @Override
    public void drawUI() {
    }
}