package io.github.team10.escapefromuni;

public class DifficultyModifiers {
    public float playerSpeedModifier;
    public float enemySpeedModifier;
    public float speedBoostModifier;
    public float freezeModifier;
    public boolean hardQuestions;
    // Add any others later

    public DifficultyModifiers() {
        // Empty constructor. Do not remove
    }

    public DifficultyModifiers(float playerSpeedModifier, float enemySpeedModifier, float speedBoostModifier, float freezeModifier, boolean hardQuestions) {
        this.playerSpeedModifier = playerSpeedModifier;
        this.enemySpeedModifier = enemySpeedModifier;
        this.speedBoostModifier = speedBoostModifier;
        this.freezeModifier = freezeModifier;
        this.hardQuestions = hardQuestions;
    }
}
