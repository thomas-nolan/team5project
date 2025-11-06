package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Positive event where the player encounters a Greggs sausage roll.
 * 
 * When the player runs into the sausage roll, they gain a speed increase.
 */
public class EventGreggs extends Event {

    private final Texture greggsTexture;
    private Sprite greggsSprite;

    private boolean used = false;

    /**
     * Creates a new EventGreggs.
     */
    public EventGreggs(Player player, EscapeGame game)
    {
        super(EventType.POSITIVE, player, game);
        greggsTexture = new Texture("GreggsSausageRoll.png");
    }

    @Override
    public void startEvent() {
        if (eventFinished) return;

        greggsSprite = new Sprite(greggsTexture);
        greggsSprite.setSize(3f, 2f);
        greggsSprite.setPosition(6.5f, 3.5f);
    }

    @Override
    public void endEvent() {
        if (!eventFinished && used)
        {
            eventFinished = true;
            greggsTexture.dispose();
        }
    }

    @Override
    public void update(float delta) {
        if (!used)
        {
            float playerDist = getPlayerGreggsDist();
            if (playerDist < 1f)
            {
                pickupGreggs();
            }
        }
    }

    /**
     * Calculates the distance between the center of the player and the Greggs sprite.
     *
     * @return the distance between the player and the Greggs sprite.
     */
    private float getPlayerGreggsDist()
    {
        Vector2 playerPos = player.getCenter();
        float greggsX = greggsSprite.getX() + greggsSprite.getWidth() / 2f;
        float greggsY = greggsSprite.getY() + greggsSprite.getHeight() / 2f;
        Vector2 greggsCenter = new Vector2(greggsX, greggsY);
        return greggsCenter.dst(playerPos);
    }

    /**
     * Handles applying the effects (speed increase) of collecting the Greggs sausage roll.
     */
    private void pickupGreggs()
    {
        used = true;
        player.increaseSpeed(2f);
    }

    @Override
    public void draw() {
        if (!used)
        {
            greggsSprite.draw(game.batch);
        }
    }

    @Override
    public void drawUI() {}
    
}
 