public class Timer {

    private float time;
    private float timeLeft;

    public Timer() {
        this.time = 0;
        this.timeLeft = 300; // start at 300 seconds
    }

    public void update(float delta) {
        time += delta;
    }

    public float getTime() {
        return time;
        timeLeft -= delta;
        if (timeLeft < 0) {
            timeLeft = 0;
        }
    }

    public int getTimeSeconds() {
        return (int) time;
        return (int) timeLeft;
    }

    public boolean hasReached(float seconds) {
        return time >= seconds;
    public boolean isFinished() {
        return timeLeft <= 0;
    }

    public void reset() {
        time = 0;
        timeLeft = 300;
    }
}
