package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a door used to connect rooms.
 * 
 * Each {@code Door} is has a direction and is managed by the {@link DoorController}.
 * The door can be active or inactive indicating whether it is visible and can be used.  
 */
public class Door {
    public DoorController doorController;
    public DoorDirection direction;

    public boolean isActive;
    public Texture doorTexture;
    public Sprite doorSprite;

    /**
     * Creates a new Door instance.
     * 
     * The door is always active initially (but this may be changed by the roomManager).
     * @param roomManager Manages the door.
     * @param direction Direction of the door in relation to the center of the room.
     * @param x The x-coord of the bottom left corner of the door.
     * @param y The y-coord of the bottom left corner of the door.
     */
    public Door(DoorController doorController, DoorDirection direction, float x, float y)
    {
        this.doorController = doorController;
        this.direction = direction;

        doorTexture = new Texture("DoorNew.png");
        doorSprite = new Sprite(doorTexture);

        doorSprite.setSize(1f, 1f);
        doorSprite.setPosition(x, y);

        isActive = true;
    }

    public void draw()
    {
        if (isActive)
        {
            doorSprite.draw(doorController.getGame().batch);    
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
