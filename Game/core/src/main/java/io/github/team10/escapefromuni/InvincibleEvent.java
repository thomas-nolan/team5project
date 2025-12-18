package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InvincibleEvent implements IEvent{
    private final Player player;
    private final EscapeGame game;
    private boolean finished = false;
    private boolean pickedUp = false;
    private Texture texture;
    private Sprite sprite;


    public InvincibleEvent(Player player, EscapeGame game){
        this.player = player;
        this.game = game;

        texture = new Texture("GreggsSausageRoll.png");
        sprite = new Sprite(texture);
        sprite.setSize(1.5f, 1.5f);
        float width = game.viewport.getWorldWidth();
        float height = game.viewport.getWorldHeight();
        sprite.setPosition(width / 2f - sprite.getWidth(), height / 2f - sprite.getHeight());
    }

    @Override
    public EventType getType() {
        return EventType.POSITIVE;
    }

    @Override
    public void startEvent() {
    }

    @Override
    public void endEvent() {
       texture.dispose();
    }

    @Override
    public void update(float delta) {
        if(finished) return;


        if(!pickedUp){
            if(player.checkCollision(sprite)){
                pickedUp = true;
                player.setInvincible(10f);
            } 
        }
    }

    @Override
    public void draw() {
       if(!pickedUp){
        sprite.draw(game.batch);
       }
    }

    @Override
    public void drawUI() {
    }

    @Override
    public boolean IsFinished() {
        return finished;
    }
    
}
