package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * A hidden event where the player encounters the Ghost of Longboi.
 * 
 * The event begins hidden (only outline is visible) and is revealed when the player approaches within a certain 
 * distance. Once revealed, Longboi appears and displays a dialogue panel.
 */
public class EventLongboi implements IEvent {

    private final Player player;
    private final EscapeGame game;
    private final EventType type;

    private boolean eventFinished = false;
    private boolean hidden = true;
    private final Texture longboiHiddenTexture;
    private final Texture longboiTexture;
    private Sprite longboiSprite;
    private final Texture speechPanelTexture;
    private final Sprite speechPanelSprite;

    /**
     * Creates a new EventLongboi.
     */
    public EventLongboi(Player player, EscapeGame game)
    {
        this.player = player;
        this.game = game;
        this.type = EventType.HIDDEN;

        longboiTexture = new Texture("Longboi.png");
        longboiHiddenTexture = new Texture("LongboiShadow.png");
        speechPanelTexture = new Texture("UIWideBottomPanel.png");
        speechPanelSprite = new Sprite(speechPanelTexture);
        speechPanelSprite.setSize(1200f, 240f);
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public boolean IsFinished() {
        return eventFinished;
    }

    @Override
    public void startEvent()
    {
        if (eventFinished) return;

        longboiSprite = new Sprite(longboiHiddenTexture);
        longboiSprite.setSize(1f, 2f);
        longboiSprite.setPosition(8f, 3f);
        AudioManager.getInstance().playEventSound(this.type);
    }

    @Override
    public void endEvent()
    {
        if (!eventFinished && !hidden){
            eventFinished = true;
            longboiHiddenTexture.dispose();
            longboiTexture.dispose();
        }
    }

    @Override
    public void update(float delta)
    {
        // Check every frame if player is close enough to reveal.
        if (hidden)
        {
            float playerDist = getPlayerLongboiDist();
            if (playerDist < 3f)
            {
                reveal();
            }
        }
    }

    /**
     * Reveal the hidden event - longboi will appear.
     */
    private void reveal()
    {
        hidden = false;
        longboiSprite = new Sprite(longboiTexture);
        longboiSprite.setSize(1f, 2f);
        longboiSprite.setPosition(8f, 3f);
    }

    /**
     * Calculates the distance between the center of the player and Longboi.
     * @return The distance between the player and longboi.
     */
    private float getPlayerLongboiDist()
    {
        Vector2 playerPos = player.getCenter();
        float longboiX = longboiSprite.getX() + longboiSprite.getWidth() / 2f;
        float longboiY = longboiSprite.getY() + longboiSprite.getHeight() / 2f;
        Vector2 longboiCenter = new Vector2(longboiX, longboiY);
        return longboiCenter.dst(playerPos);
    }

    @Override
    public void draw()
    {
        if (eventFinished) return;
        longboiSprite.draw(game.batch);
    }

    @Override
    public void drawUI()
    {
        if (eventFinished) return;

        if (!hidden)
        {
            float uiWidth = game.uiViewport.getWorldWidth();
            
            float panelY = 150f;
            float panelX = uiWidth / 2f;
            speechPanelSprite.setCenter(panelX, panelY);
            speechPanelSprite.draw(game.batch);

            String message = "Ghost of Longboi: \"Quack ... Quack\"";

            GlyphLayout layout = new GlyphLayout(game.font, message);
            float textWidth = layout.width;
            float textHeight = layout.height;
            float textX = (uiWidth - textWidth) / 2f;
            float textY = panelY + textHeight / 2f;

            game.font.setColor(Color.BLACK);
            game.font.draw(game.batch, layout, textX, textY);
        }
    }
}
