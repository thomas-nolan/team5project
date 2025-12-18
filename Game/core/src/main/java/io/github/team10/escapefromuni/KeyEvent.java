package io.github.team10.escapefromuni;

import javax.print.attribute.standard.MediaSize.ISO;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class KeyEvent implements IEvent{
    
    private final Player player;
    private final EscapeGame game;

    private Texture keyTexture;
    private Sprite keySprite;

    private boolean isFinished = false;
    private boolean pickedUp = false;

    private BitmapFont font = new BitmapFont();

    public KeyEvent(Player player, EscapeGame game){
        this.player = player;
        this.game = game;

        font.setColor(Color.RED);
    }

    @Override
    public EventType getType() {
        return EventType.POSITIVE;
    }

    @Override
    public void startEvent() {
        keyTexture = new Texture("keycard1.png");
        keySprite = new Sprite(keyTexture);
        keySprite.setSize(1f, 1f);
        keySprite.setPosition(7f,4f);
    }

    @Override
    public void endEvent() {
        keyTexture.dispose();
    }

    @Override
    public void update(float delta) {
        if(isFinished) return;

        if (!pickedUp && player.checkCollision(keySprite)){
            player.giveKey();
            pickedUp = true;
            isFinished = true;
        }
    }

    @Override
    public void draw() {
        if(!isFinished){
            keySprite.draw(game.batch);
        }
    }

    @Override
    public void drawUI() {
        if(!pickedUp){};
    }

    @Override
    public boolean IsFinished() {
        return isFinished;
    }
}
