package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Updated for Assessment 2
 *
 * Represents the THE3 exam negative event.
 * This event temporarily disables player movement,
 * displays a quiz question with TRUE/FALSE buttons, and provides
 * feedback based on the player's answer. If the player gets the answer
 * correct the score increases, otherwise the
 * player is slowed down.
 */
public class EventTHE3 implements IEvent {

  // NEW FOR ASSESSMENT 2 - sets up the systems and controllers needed for the event
  private final ScoreManager scoreManager;
  private final Player player;
  private final EscapeGame game;
  private final EventType type;
  private final AchievementManager achievementManager;
  private final EventSystem eventSystem;

  private boolean eventFinished = false;

  // Sets up the textures needed for the event
  private final Texture titlePanelTexture;
  private final Texture questionPanelTexture;
  private final Texture trueButtonTexture;
  private final Texture falseButtonTexture;

  // Sets up the sprites to be used for the event
  private final Sprite titlePanelSprite;
  private final Sprite questionPanelSprite;
  private final Sprite trueButtonSprite;
  private final Sprite falseButtonSprite;

  private boolean questionAnswered = false;
  private float answerDisplayTimer = 0f;

  private String questionText;
  private String feedbackText = "";

  // Sets up the hit box for the buttons
  private Rectangle trueButtonBounds;
  private Rectangle falseButtonBounds;

  private HashMap<String, Boolean> questions;
  private Random randomNum;

  // NEW FOR ASSESSMENT 2 - Modifies the type of questions being asked
  private DifficultySetup diffSetup = new DifficultySetup();

  /**
   * EXTENDS ON FROM ASSESSMENT 1.
   * It is used to set up the systems needed as well as the
   * textures and the UI for the question.
   *
   * @param player the player controlled by the user
   * @param game the main LibGDX game instance
   * @param scoreManager the manager which handles the players score
   * @param achievementManager the manager which handles the players achievement
   * @param eventSystem the central manager for the events
   */
  public EventTHE3(Player player, EscapeGame game, ScoreManager scoreManager,
      AchievementManager achievementManager, EventSystem eventSystem) {

    this.player = player;
    this.game = game;
    this.eventSystem = eventSystem;
    this.scoreManager = scoreManager;
    this.questions = new HashMap<>();
    this.type = EventType.NEGATIVE;
    this.achievementManager = achievementManager;

    // Creates the hash map for the questions
    this.questions = new HashMap<String, Boolean>();

    // Sets up the textures used
    titlePanelTexture = new Texture("UI/Blue4x1Panel.png");
    questionPanelTexture = new Texture("UI/BlueBorder10x3Panel.png");
    trueButtonTexture = new Texture("UI/GreenBorder5x2Panel.png");
    falseButtonTexture = new Texture("UI/OrangeBorder5x2Panel.png");

    // Creates the needed sprites with their texture
    titlePanelSprite = new Sprite(titlePanelTexture);
    questionPanelSprite = new Sprite(questionPanelTexture);
    trueButtonSprite = new Sprite(trueButtonTexture);
    falseButtonSprite = new Sprite(falseButtonTexture);
  }

  /**
   * Returns the event type.
   */
  @Override
  public EventType getType() {
    return type;
  }

  /**
   * Returns a boolean value depending on if the game is finished.
   */
  @Override
  public boolean IsFinished() {
    return eventFinished;
  }


  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Starts the event by disabling player movement and initialising the quiz UI.
   * Does nothing if the event has already finished previously.
   */
  @Override
  public void startEvent() {
    if (eventFinished) {
      return;
    }

    // Stops the player from moving and sets up the event
    player.enableMovement(false);
    AudioManager.getInstance().playEventSound(this.type);
    questionAnswered = false;
    // NEW FOR ASSESSMENT 2 - adds a count to the negative events counter
    eventSystem.registerEvent(EventType.NEGATIVE);
    initialiseQuizUI();
  }

  /**
   * Initialises and positions all UI components for the quiz screen.
   * This includes a title, question display and two buttons (true or false).
   */
  private void initialiseQuizUI() {

    // Used to select a random question
    randomNum = new Random();
    initialiseQuestions();

    feedbackText = "";
    questionText = selectQuestion();

    float uiWidth = game.uiViewport.getWorldWidth();
    float uiHeight = game.uiViewport.getWorldHeight();

    // Sets the size and position for the panel
    titlePanelSprite.setSize(480f, 120f);
    titlePanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.75f);

    // Sets the size and position of the question
    questionPanelSprite.setSize(1200f, 360f);
    questionPanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.5f);

    // Set the size and position of the questions
    trueButtonSprite.setSize(600f, 240f);
    falseButtonSprite.setSize(600f, 240f);
    trueButtonSprite.setCenter(uiWidth / 2f - 320f, uiHeight * 0.20f);
    falseButtonSprite.setCenter(uiWidth / 2f + 320f, uiHeight * 0.20f);

    // Creates a rectangle for the true button
    trueButtonBounds = new Rectangle(
        trueButtonSprite.getX(), trueButtonSprite.getY(),
        trueButtonSprite.getWidth(), trueButtonSprite.getHeight()
    );

    // Creates a rectangle for the false button
    falseButtonBounds = new Rectangle(
        falseButtonSprite.getX(), falseButtonSprite.getY(),
        falseButtonSprite.getWidth(), falseButtonSprite.getHeight()
    );
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Fills the questions hashmap with questions (keys)
   * and answers (values)
   */
  public void initialiseQuestions() {
    // NEW FOR ASSESSMENT 2 - Questions for hard difficulty
    if (diffSetup.readDifficulty().hardQuestions) {
      questions.put(
          "True or False:\nThe do ... while loop is in the C programming language",
          Boolean.TRUE
      );
      questions.put("True or False:\n2n^3 +5n^2 +3 is NOT in O(n3)", Boolean.FALSE);
      questions.put(
           "True or False:\nP <-> Q is true whenever P and Q\nhave different truth values",
           Boolean.FALSE
      );
      questions.put(
           "True or False:\nUniversal introduction can be expressed in Carnap with AE",
           Boolean.FALSE
      );
      questions.put(
           "True or False:\nThe Java programming language\nis named after a coffee\n from Indonesia",
           Boolean.TRUE
      );

    } else { // EXTENDED FROM QUESTION 1 - Questions for easy and medium difficulty
      questions.put(
          "True or False:\nThe self-accepting problem SA \nis semi-decidable", Boolean.TRUE);
      questions.put(
          "True or False:\nIpV4 is 64-bits", Boolean.FALSE);
      questions.put(
          "True or False:\nThe predecessor to the C programming\n language was called B",
          Boolean.TRUE
      );
      questions.put(
          "True or False:\nThe programming language Python\nis named after a British comedy series",
          Boolean.TRUE
      );
      questions.put("True or False:\nThe HTTPS protocol uses port 80", Boolean.FALSE);
    }
  }

  /**
   * Using a randomly generated number, this function selects a random question.
   * from the hash map.
   *
   * @return String - A random key from the hash map containing the question
   */
  private String selectQuestion() {
    // TO DO
    List<String> questionList = new ArrayList<String>(questions.keySet());
    int questionNumber = randomNum.nextInt(questions.size());

    return questionList.get(questionNumber);
  }


  /**
   * Ends the event, enabling player movement again and disposing of textures.
   */
  @Override
  public void endEvent() {
    eventFinished = true;
    player.enableMovement(true);

    titlePanelTexture.dispose();
    questionPanelTexture.dispose();
    trueButtonTexture.dispose();
    falseButtonTexture.dispose();
  }

  /**
   * Called every frame to update the event's logic.
   * Handles input detection for true/false buttons.
   * Controls the post-answer delay before ending the event.
   *
   * @param delta The time elapsed since the last frame in seconds.
   *
   */
  @Override
  public void update(float delta) {
    if (eventFinished) {
      return;
    }

    // End event 1 second after answering the question.
    if (questionAnswered) {
      answerDisplayTimer += delta;
      if (answerDisplayTimer > 1f) {
        endEvent();
      }
      return;
    }

    if (Gdx.input.justTouched()) {
      Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      game.uiCamera.unproject(touchPos);

      if (trueButtonBounds.contains(touchPos.x, touchPos.y)) {
        // TRUE selected
        handleAnswer(true);
      } else if (falseButtonBounds.contains(touchPos.x, touchPos.y)) {
        // FALSE selected
        handleAnswer(false);
      }
    }
  }

  /**
   * Apply's effects based on the player's answer.
   * If correct, score is increased. If incorrect, player speed is decreased.
   *
   * @param answer {@code true} if the true button was pressed, {@code false} otherwise.
   */
  private void handleAnswer(boolean answer) {
    questionAnswered = true;
    answerDisplayTimer = 0f;

    // Handles the logic of the answer selected by the user
    if (answer == questions.get(questionText)) {
      feedbackText = "Correct: Score +500";
      scoreManager.increaseScore(500);
    } else {
      feedbackText = "Incorrect: Speed Decrease";
      achievementManager.removeAchievement("No incorrect answers");
      player.increaseSpeed(-2f);
    }
  }

  @Override
   public void draw() {}

  /**
   * This Draws all the UI for the elements for the event
   * This includes the text, the panel as well as the True/False buttons.
   */
  @Override
  public void drawUI() {
    if (eventFinished) {
      return;
    }

    // Sets the font to black
    game.font.setColor(Color.BLACK);

    // Used to measure text width and height for centering
    final GlyphLayout layout = new GlyphLayout();

    // Draws the UI into the game
    titlePanelSprite.draw(game.batch);
    questionPanelSprite.draw(game.batch);
    trueButtonSprite.draw(game.batch);
    falseButtonSprite.draw(game.batch);

    float uiWidth = game.uiViewport.getWorldWidth();

    // Draws the title text
    String titleText = "EXAM";
    layout.setText(game.font, titleText);
    float titleX = (uiWidth - layout.width) / 2f;
    float titleY = titlePanelSprite.getY() + titlePanelSprite.getHeight() / 2f + layout.height / 2f;
    game.font.draw(game.batch, layout, titleX, titleY);

    // Draw question or feedback test, depending on whether the question has been answered.
    String displayText = questionAnswered ? feedbackText : questionText;
    layout.setText(game.font, displayText);
    float questionX = (uiWidth - layout.width) / 2f;
    float questionY = questionPanelSprite.getY()
        + questionPanelSprite.getHeight() / 2f
        + layout.height / 2f;
    game.font.draw(game.batch, layout, questionX, questionY);

    // Draws the true button label
    layout.setText(game.font, "TRUE");
    float trueTextX = trueButtonSprite.getX() + (trueButtonSprite.getWidth() - layout.width) / 2f;
    float trueTextY = trueButtonSprite.getY() + (trueButtonSprite.getHeight() + layout.height) / 2f;
    game.font.draw(game.batch, layout, trueTextX, trueTextY);

    // Draws the False button label
    layout.setText(game.font, "FALSE");
    float falseTextX = falseButtonSprite.getX()
        + (falseButtonSprite.getWidth()
        - layout.width) / 2f;
    float falseTextY = falseButtonSprite.getY()
        + (falseButtonSprite.getHeight()
        + layout.height) / 2f;
    game.font.draw(game.batch, layout, falseTextX, falseTextY);
  }
}
