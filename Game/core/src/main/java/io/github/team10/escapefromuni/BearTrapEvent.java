package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class BearTrapEvent implements IEvent {

    private static final float FREEZE_TIME = 5f;
    private final Texture trapTexture;
    private Sprite trapSprite;


    private final Player player;
    private final Timer timer;
    private final EscapeGame game;

    private float remaining = FREEZE_TIME;
    private boolean finished = false;
    private boolean triggered = false;

    private BitmapFont font = new BitmapFont();

    public BearTrapEvent(EscapeGame game, Player player, Timer timer){
        this.player = player;
        this.timer = timer;
        this.game = game;

        this.trapTexture = new Texture("GreggsSausageRoll.png");
        font.setColor(Color.RED);

    }
    @Override
    public EventType getType() {
        return EventType.NEGATIVE;
    }

    @Override
    public void startEvent() {
        trapSprite = new Sprite(trapTexture);
        trapSprite.setSize(2f,2f);
        trapSprite.setPosition(7f, 4f);
    }

    @Override
    public void endEvent() {
        trapTexture.dispose();
        font.dispose();
    }

    @Override
    public void update(float delta) {
        if(finished) return;

        if(!triggered){
            if(player.checkCollision(trapSprite) && !player.isInvincible()) {
                triggered = true;
                player.enableMovement(false);
            }
            return;

        }

        remaining -= delta;

        if(remaining <= 0) {
            player.enableMovement(true);
            finished = true;
        }
    }

    @Override
    public void draw() {
        if(!triggered) {
            trapSprite.draw(game.batch);
        } 
    }

    @Override
    public void drawUI() {
        if (!finished && triggered) {
        // batch is already begun by GameScreen, just draw
        // position and size
            font.getData().setScale(4f);

            String message = "Bear trap! Frozen for " + (int)Math.ceil(remaining) + "s";

            com.badlogic.gdx.graphics.g2d.GlyphLayout layout = new com.badlogic.gdx.graphics.g2d.GlyphLayout(font, message);
            float uiWidth = game.uiViewport.getWorldWidth();
            float uiHeight = game.uiViewport.getWorldHeight();

            float textX = (uiWidth - layout.width) / 2f;
            float textY = uiHeight * 0.9f; // top of screen

            font.setColor(Color.RED);
            font.draw(game.batch, layout, textX, textY);
    }
    }


    @Override
    public boolean IsFinished() {
       return finished;
    }
    
}
