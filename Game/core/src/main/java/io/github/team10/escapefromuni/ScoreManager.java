package io.github.team10.escapefromuni;

public class ScoreManager {

    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public void increaseScore(int scoreIncrease)
    {
        score += scoreIncrease;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }

    public int CalculateFinalScore(int timeLeftSeconds, AchievementManager achievementManager)
    {
        int timeScore = 50 * timeLeftSeconds;
        int achievementScore = 200 * achievementManager.getTotalAchievements();
        return timeScore + score + achievementScore;
    }
}
