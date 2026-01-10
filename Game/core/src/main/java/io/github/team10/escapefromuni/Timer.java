package io.github.team10.escapefromuni;

/**
 * A class that manages the game timer.
 * The game timer starts at 300 seconds (5 minutes) and counts down to zero
 * This class also manages the timer for the freeze event (Lasts 30 seconds on normal difficulty)
 */
public class Timer {

    private float time;
    private float timeLeft;
    private boolean isFrozen;
    private float frozenTimer;

    private DifficultySetup diffSetup = new DifficultySetup();

    /**
     * Constructor for the timer, initialises all values
     * The value of the frozen timer depends on the difficulty (Default 30 seconds)
     */
    public Timer() {
        this.time = 0;
        this.timeLeft = 300; // start at 300 seconds
        this.frozenTimer = 30 * diffSetup.readDifficulty().freezeModifier; // Freeze lasts 30 seconds when activated
        this.isFrozen = false;
    }

    /**
     * Checks each frame if the frozen event is triggered.
     * Decreases the timer and frozen timer (if it is active)
     * @param delta
     */
    public void update(float delta) {
        if (!isFrozen) {
            this.frozenTimer = 30 * diffSetup.readDifficulty().freezeModifier;
            time += delta;
            timeLeft -= delta;
            if (timeLeft < 0) {
                timeLeft = 0;
            }
        }
        else {
            frozenTimer -= delta;
            if (frozenTimer <= 0) {
                isFrozen = false;
            }
        }
    }

    public float getTime() {
        return time;
    }

    public int getTimeSeconds() {
        return (int) time;
    }

    public int getTimeLeftSeconds() {
        return (int) timeLeft;
    }

    public boolean hasReached(float seconds) {
        return time >= seconds;
    }

    public boolean isFinished() {
        return timeLeft <= 0;
    }

    /**
     * Sets frozen to true when the event is triggered
     */
    public void setFrozen() {
        isFrozen = true;
    }

    /**
     * Resets the timer to the default values
     */
    public void reset() {
        time = 0;
        timeLeft = 300;
        isFrozen = false;
    }
}
