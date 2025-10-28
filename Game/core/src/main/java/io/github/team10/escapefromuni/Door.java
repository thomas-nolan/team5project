package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Door {
    public RoomManager roomManager;
    public DoorDirection direction;
    public boolean isActive;
    public Texture doorTexture;
    public Sprite doorSprite;

    public Door(RoomManager roomManager, DoorDirection direction, float x, float y)
    {
        this.roomManager = roomManager;
        this.direction = direction;
        doorTexture = new Texture("Door_opened.png");
        doorSprite = new Sprite(doorTexture);
        doorSprite.setSize(1f, 1f);
        doorSprite.setPosition(x, y);
        isActive = true;
    }

    public void draw()
    {
        if (isActive)
        {
            doorSprite.draw(roomManager.game.batch);    
        }
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public boolean getActive()
    {
        return isActive;
    }

    public void dispose()
    {
        doorTexture.dispose();
    }
}
