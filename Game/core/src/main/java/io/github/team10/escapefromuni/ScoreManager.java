package io.github.team10.escapefromuni;

public class ScoreManager {

    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public void addEventPoint() {
        score++;
    }

    public void subtractEventPoint() {
        score--;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
