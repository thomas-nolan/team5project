package io.github.team10.escapefromuni;

public class Timer {

    private float time;
    private float timeLeft;
    private boolean isFrozen;
    private float frozenTimer;

    public Timer() {
        this.time = 0;
        this.timeLeft = 300; // start at 300 seconds
        this.frozenTimer = 30; // Freeze lasts 30 seconds when activated
        this.isFrozen = false;
    }

    public void update(float delta) {
        if (!isFrozen) {
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

    public void setFrozen() {
        isFrozen = true;
    }

    public void reset() {
        time = 0;
        timeLeft = 300;
    }
}
