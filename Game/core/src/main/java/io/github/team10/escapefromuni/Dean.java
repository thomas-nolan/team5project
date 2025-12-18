package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Dean extends Player{

    private boolean reached = false;
    private final float roomHeight;
    private final float roomWidth;
    private Texture deanTexture;

    public Dean(float speed, float width, float height, EscapeGame game, float roomWidth, float roomHeight){
        super(speed, width, height, game);
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;

        deanTexture = new Texture("dean.png");
        playerSprite.setTexture(deanTexture);
        playerSprite.setSize(width, height);
        
        setCenter(roomWidth / 2f, roomHeight / 2f);
    }

    @Override
    public void update(float delta){
        if(reached) return;

        chasePlayer(delta);

        super.update(delta);
    }

    private void chasePlayer(float delta){

        Player player = game.gameController.getPlayer();
        Vector2 playerPos = player.getCenter();
        Vector2 deanPos = getCenter();

        float dx = playerPos.x - deanPos.x;
        float dy = playerPos.y - deanPos.y;

        float distance = deanPos.dst(playerPos);

        if (distance > 0){
            float moveX = (dx / distance) * speed * delta;
            float moveY = (dy / distance) * speed * delta;
            playerSprite.translateX(moveX);
            playerSprite.translateY(moveY);
        }
    }

    public boolean checkReached(Player player){
        if (!reached && player.checkCollision(this.playerSprite) && !player.isInvincible()){
            reached = true;
            return true;
        }
        return false;
    }

    @Override
    public void dispose(){
        super.dispose();
        if (deanTexture != null){
            deanTexture.dispose();
        }
    }
}
