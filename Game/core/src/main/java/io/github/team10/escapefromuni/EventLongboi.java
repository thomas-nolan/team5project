package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * A hidden event where the player encounters the Ghost of Longboi.
 * The event begins hidden (only outline is visible) and
 * is revealed when the player approaches within a certain
 * distance. Once revealed, Longboi appears and displays a dialogue panel.
 */
public class EventLongboi implements IEvent {

  // EXTENDED FROM ASSESSMENT 1 - Core game references including new EventSystem
  private final Player player;
  private final EscapeGame game;
  private final EventType type;
  private final AchievementManager achievementManager;
  private final EventSystem eventSystem;

  // Used to track states
  private boolean eventFinished = false;
  private boolean hidden = true;

  // Textures used in the event
  private final Texture longboiHiddenTexture;
  private final Texture longboiTexture;
  private Sprite longboiSprite;

  // UI to display dialogue
  private final Texture speechPanelTexture;
  private final Sprite speechPanelSprite;

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Creates an instance of hidden Longboi event
   *
   * @param player player controlled by the user
   * @param game the main LibGDX game instance
   * @param achievementManager manages achievements unlocked by the event
   * @param eventSystem the central events manager
   */
  public EventLongboi(Player player, EscapeGame game,
        AchievementManager achievementManager, EventSystem eventSystem) {
    this.player = player;
    this.game = game;
    this.type = EventType.HIDDEN;
    this.achievementManager = achievementManager;
    this.eventSystem = eventSystem;

    // Loads the texture for hidden and revealed states
    longboiTexture = new Texture("Longboi.png");
    longboiHiddenTexture = new Texture("LongboiShadow.png");

    // Loads and configures the speech UI panel
    speechPanelTexture = new Texture("UIWideBottomPanel.png");
    speechPanelSprite = new Sprite(speechPanelTexture);
    speechPanelSprite.setSize(1200f, 240f);
  }

  /**
   * Returns the type of the event.
   */
  @Override
  public EventType getType() {
    return type;
  }

  /**
   * Indicates if the event is finished.
   */
  @Override
  public boolean IsFinished() {
    return eventFinished;
  }

  /**
   * Intialises the hidden Longboi event.
   */
  @Override
  public void startEvent() {
    if (eventFinished) {
      return;
    }
    // NEW FOR ASSESSMENT 2 - awards the "Find Long Boi" achievement when activated
    achievementManager.addAchievement("Find Long Boi");

    // Creates the sprite and sets its size and position
    longboiSprite = new Sprite(longboiHiddenTexture);
    longboiSprite.setSize(1f, 2f);
    longboiSprite.setPosition(8f, 3f);
    AudioManager.getInstance().playEventSound(this.type);
  }

  /**
   * Cleans up the resources once the event has finished.
   */
  @Override
  public void endEvent() {
    if (!eventFinished && !hidden) {
      eventFinished = true;
      longboiHiddenTexture.dispose();
      longboiTexture.dispose();
    }
  }

  /**
   * EXTENDS FROM ASSESSMENT 1.
   * Updates the event logic, displays longboi if the player is close enough.
   */
  @Override
  public void update(float delta) {
    // Check every frame if player is close enough to reveal.
    if (hidden) {
      float playerDist = getPlayerLongboiDist();
      if (playerDist < 3f) {
        reveal();
        // NEW FOR ASSESSMENT 2 - registers the event as hidden, adding it to the events counter
        eventSystem.registerEvent(EventType.HIDDEN);
      }
    }
  }

  /**
   * Reveal the hidden event - longboi will appear.
   */
  private void reveal() {
    hidden = false;
    longboiSprite = new Sprite(longboiTexture);
    longboiSprite.setSize(1f, 2f);
    longboiSprite.setPosition(8f, 3f);
  }

  /**
   * Calculates the distance between the center of the player and Longboi.
   *
   * @return The distance between the player and longboi.
   */
  private float getPlayerLongboiDist() {
    Vector2 playerPos = player.getCenter();
    // Calculates longboi's vector coordinates
    float longboiX = longboiSprite.getX() + longboiSprite.getWidth() / 2f;
    float longboiY = longboiSprite.getY() + longboiSprite.getHeight() / 2f;
    Vector2 longboiCenter = new Vector2(longboiX, longboiY);
    return longboiCenter.dst(playerPos);
  }

  /**
   * Renders the event sprite whilst active.
   */
  @Override
  public void draw() {
    if (eventFinished) {
      return;
    }
    longboiSprite.draw(game.batch);
  }

  /**
   * Renders the UI, the dialouge once Longboi has been revealed.
   */
  @Override
  public void drawUI() {
    if (eventFinished) {
      return;
    }

    if (!hidden) {
      // Positions the dialouge on the screen
      float uiWidth = game.uiViewport.getWorldWidth();
      float panelY = 150f;
      float panelX = uiWidth / 2f;

      speechPanelSprite.setCenter(panelX, panelY);
      speechPanelSprite.draw(game.batch);

      // Dialouge text displayed on the screen to the player
      String message = "Ghost of Longboi: \"Quack ... Quack\"";

      // Centers the text within the panel
      GlyphLayout layout = new GlyphLayout(game.font, message);
      float textWidth = layout.width;
      float textHeight = layout.height;
      float textX = (uiWidth - textWidth) / 2f;
      float textY = panelY + textHeight / 2f;

      // Sets the colour of the dialouge as well as drawing it in the game
      game.font.setColor(Color.BLACK);
      game.font.draw(game.batch, layout, textX, textY);
    }
  }
}
