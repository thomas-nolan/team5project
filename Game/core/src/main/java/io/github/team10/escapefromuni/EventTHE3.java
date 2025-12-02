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

/**
 * Represents the THE3 exam negative event.
 *
 * This event temporarily disables player movement, displays a quiz question with TRUE/FALSE buttons, and provides
 * feedback based on the player's answer. If the player gets the answer correct the score increases, otherwise the
 * player is slowed down.
 */
public class EventTHE3 implements IEvent {

    private final ScoreManager scoreManager;
    private final Player player;
    private final EscapeGame game;
    private final EventType type;

    private boolean eventFinished = false;

    private final Texture titlePanelTexture;
    private final Texture questionPanelTexture;
    private final Texture trueButtonTexture;
    private final Texture falseButtonTexture;

    private final Sprite titlePanelSprite;
    private final Sprite questionPanelSprite;
    private final Sprite trueButtonSprite;
    private final Sprite falseButtonSprite;

    private boolean questionAnswered = false;
    private float answerDisplayTimer = 0f;

    private String questionText;
    private String feedbackText = "";

    private Rectangle trueButtonBounds;
    private Rectangle falseButtonBounds;

    private HashMap<String, Boolean> questions;
    private Random questionNumber;

    /**
     * Creates a new instance of EventTHE3.
     */
    public EventTHE3(Player player, EscapeGame game, ScoreManager scoreManager)
    {
        this.player = player;
        this.game = game;
        this.scoreManager = scoreManager;
        this.type = EventType.NEGATIVE;

        titlePanelTexture = new Texture("UI/Blue4x1Panel.png");
        questionPanelTexture = new Texture("UI/BlueBorder10x3Panel.png");
        trueButtonTexture = new Texture("UI/GreenBorder5x2Panel.png");
        falseButtonTexture = new Texture("UI/OrangeBorder5x2Panel.png");

        titlePanelSprite = new Sprite(titlePanelTexture);
        questionPanelSprite = new Sprite(questionPanelTexture);
        trueButtonSprite = new Sprite(trueButtonTexture);
        falseButtonSprite = new Sprite(falseButtonTexture);
    }

    @Override
    public EventType getType(){
        return type;
    }
    @Override
    public boolean IsFinished() {
        return eventFinished;
    }


    /**
     * Starts the event by disabling player movement and initialising the quiz UI.
     * Does nothing if the event has already finished previously.
     */
    @Override
    public void startEvent() {
        if (eventFinished) return;

        player.enableMovement(false);
        AudioManager.getInstance().playEventSound(this.type);
        questionAnswered = false;
        initialiseQuizUI();
    }

    /**
     * Initialises and positions all UI components for the quiz screen.
     *
     * This includes a title, question display and two buttons (true or false).
     */
    private void initialiseQuizUI()
    {
        questionNumber = new Random();
        initialiseQuestions();

        feedbackText = "";
        questionText = "True or False:\nThe self-accepting problem SA \nis semi-decidable.";

        float uiWidth = game.uiViewport.getWorldWidth();
        float uiHeight = game.uiViewport.getWorldHeight();

        titlePanelSprite.setSize(480f, 120f);
        titlePanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.75f);

        questionPanelSprite.setSize(1200f, 360f);
        questionPanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.5f);

        trueButtonSprite.setSize(600f, 240f);
        falseButtonSprite.setSize(600f, 240f);
        trueButtonSprite.setCenter(uiWidth / 2f - 320f, uiHeight * 0.20f);
        falseButtonSprite.setCenter(uiWidth / 2f + 320f, uiHeight * 0.20f);

        trueButtonBounds = new Rectangle(
            trueButtonSprite.getX(), trueButtonSprite.getY(),
            trueButtonSprite.getWidth(), trueButtonSprite.getHeight()
        );

        falseButtonBounds = new Rectangle(
            falseButtonSprite.getX(), falseButtonSprite.getY(),
            falseButtonSprite.getWidth(), falseButtonSprite.getHeight()
        );
    }

    public void initialiseQuestions() {
        questions.put("True or False:\nThe self-accepting problem SA \nis semi-decidable.", Boolean.TRUE);
        // Add more questions later
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
     *
     * Handles input detection for true/false buttons.
     * Controls the post-answer delay before ending the event.
     * @param delta The time elapsed since the last frame in seconds.
     */
    @Override
    public void update(float delta) {
        if (eventFinished) return;

        // End event 1 second after answering the question.
        if (questionAnswered)
        {
            answerDisplayTimer += delta;
            if (answerDisplayTimer > 1f)
            {
                endEvent();
            }
            return;
        }

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.uiCamera.unproject(touchPos);

            if (trueButtonBounds.contains(touchPos.x, touchPos.y)) {
                // TRUE selected.
                handleAnswer(true);
            }
            else if (falseButtonBounds.contains(touchPos.x, touchPos.y)) {
                // FALSE selected.
                handleAnswer(false);
            }
        }


    }

    /**
     * Apply's effects based on the player's answer.
     *
     * If correct, score is increased. If incorrect, player speed is decreased.
     * @param answer {@code true} if the true button was pressed, {@code false} otherwise.
     */
    private void handleAnswer(boolean answer) {
        questionAnswered = true;
        answerDisplayTimer = 0f;

        if (answer) {
            feedbackText = "Correct: Score +500";
            scoreManager.increaseScore(500);
        }
        else {
            feedbackText = "Incorrect: Speed Decrease";
            player.increaseSpeed(-2f);
        }
    }

    private void selectQuestion() {
        // TO DO
    }

    @Override
    public void draw() {
    }

    @Override
    public void drawUI() {
        if (eventFinished) return;

        game.font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout();

        titlePanelSprite.draw(game.batch);
        questionPanelSprite.draw(game.batch);
        trueButtonSprite.draw(game.batch);
        falseButtonSprite.draw(game.batch);

        float uiWidth = game.uiViewport.getWorldWidth();

        String titleText = "THE3 Exam";
        layout.setText(game.font, titleText);
        float titleX = (uiWidth - layout.width) / 2f;
        float titleY = titlePanelSprite.getY() + titlePanelSprite.getHeight() / 2f + layout.height / 2f;
        game.font.draw(game.batch, layout, titleX, titleY);

        // Draw question or feedback test, depending on whether the question has been answered.
        String displayText = questionAnswered ? feedbackText : questionText;
        layout.setText(game.font, displayText);
        float questionX = (uiWidth - layout.width) / 2f;
        float questionY = questionPanelSprite.getY() + questionPanelSprite.getHeight() / 2f + layout.height / 2f;
        game.font.draw(game.batch, layout, questionX, questionY);

        layout.setText(game.font, "TRUE");
        float trueTextX = trueButtonSprite.getX() + (trueButtonSprite.getWidth() - layout.width) / 2f;
        float trueTextY = trueButtonSprite.getY() + (trueButtonSprite.getHeight() + layout.height) / 2f;
        game.font.draw(game.batch, layout, trueTextX, trueTextY);

        layout.setText(game.font, "FALSE");
        float falseTextX = falseButtonSprite.getX() + (falseButtonSprite.getWidth() - layout.width) / 2f;
        float falseTextY = falseButtonSprite.getY() + (falseButtonSprite.getHeight() + layout.height) / 2f;
        game.font.draw(game.batch, layout, falseTextX, falseTextY);
    }
}
