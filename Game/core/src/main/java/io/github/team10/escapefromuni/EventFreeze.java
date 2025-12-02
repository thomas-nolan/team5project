package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for positive event.
 * This event freezes the timer and the dean for a fixed period of time
 */
public class EventFreeze implements IEvent {

    private final Texture freezeTexture;
    private Sprite freezeSprite;
    private Timer timer;
    private boolean eventFinished;
    private final EventType type;
    private Player player;
    private EscapeGame game;

    private boolean used = false;

    public EventFreeze(Player player, EscapeGame game, Timer timer) {
        this.player = player;
        this.game = game;
        this.type = EventType.POSITIVE;
        freezeTexture = new Texture("GreggsSausageRoll.png");
        this.timer = timer;
        this.eventFinished = false;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public void startEvent() {
        if (eventFinished) return;

        freezeSprite = new Sprite(freezeTexture);
        freezeSprite.setSize(2f, 1f);
        freezeSprite.setPosition(7.5f, 5.5f);
        //AudioManager.getInstance().playEventSound(this.type);
    }

    public void endEvent() {
        if (!eventFinished && used) {
            eventFinished = true;
            freezeTexture.dispose();
        }
    }

    public void update(float delta) {
        if (!used) {
            float playerDist = getPlayerFreezeDist();
            if (playerDist < 1f) {
                pickupFreeze();
            }
        }
    }

    private float getPlayerFreezeDist() {
        Vector2 playerPos = player.getCenter();
        float freezeX = freezeSprite.getX() + freezeSprite.getWidth() / 2f;
        float freezeY = freezeSprite.getY() + freezeSprite.getHeight() / 2f;
        Vector2 freezeCenter = new Vector2(freezeX, freezeY);
        return freezeCenter.dst(playerPos);
    }

    private void pickupFreeze() {
        used = true;
        // Implement here
        timer.setFrozen();
    }

    public void draw() {
        if (!used) {
            freezeSprite.draw(game.batch);
        }
    }

    @Override
    public void drawUI() {

    }

    @Override
    public boolean IsFinished() {
        return eventFinished;
    }
}

