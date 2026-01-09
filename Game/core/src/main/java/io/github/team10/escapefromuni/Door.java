package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a door used to connect rooms.
 * Each {@code Door} is has a direction and is managed by the {@link DoorController}.
 * The door can be active or inactive indicating whether it is visible and can be used.  
 */
public class Door {
  public DoorController doorController;
  public DoorDirection direction;

  public boolean isActive;
  public Texture doorTexture;
  public Sprite doorSprite;

  // NEW FOR ASSESSMENT 2 - used for locking doors
  private boolean isLocked = false;

  /**
   * Creates a new Door instance.
   * The door is always active initially and can be locked.
   * 
   * @param direction Direction of the door in relation to the center of the room.
   * @param x The x-coord of the bottom left corner of the door.
   * @param y The y-coord of the bottom left corner of the door.
   */
  public Door(DoorController doorController, DoorDirection direction, float x, float y) {
    // initialises the door controller and direction
    this.doorController = doorController;
    this.direction = direction;

    // loads in the texture and sprite
    doorTexture = new Texture("DoorNew.png");
    doorSprite = new Sprite(doorTexture);

    // sets the size and position of the sprite
    doorSprite.setSize(1f, 1f);
    doorSprite.setPosition(x, y);

    isActive = true;
  }
 
  /**
   * Draws the door sprite into the game.
   */
  public void draw() {
    if (isActive) {
      doorSprite.draw(doorController.getGame().batch);    
    }
  }

  /**
   * This method sets the door to active or not active.
   * 
   * @param isActive boolean value containing the active preference
   */
  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  /**
   * This method gets the status of the door.
   * 
   * @return true if the door is active, false otherwise
   */
  public boolean getActive() {
    return isActive;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This method sets a doors lock status
   * 
   * @param locked a boolean value setting the door to locked or unlocked
   */
  public void setLocked(boolean locked) {
    this.isLocked = locked;
  }
  
  /**
   * NEW FOR ASSESSMENT 2
   * The method returns if a door is locked or not.
   * 
   * @return true if the door is locked, false otherwise
   */
  public boolean isLocked() {
    return isLocked;
  }
  
  /**
   * This method disposes of the door texture.
   */
  public void dispose() {
    doorTexture.dispose();
  }
}
