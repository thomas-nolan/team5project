package io.github.team10.escapefromuni;

public class DifficultyModifiers {
    public float playerSpeedModifier;
    public float enemySpeedModifier;
    // Add any others later

    public DifficultyModifiers() {

    }

    public DifficultyModifiers(float playerSpeedModifier, float enemySpeedModifier) {
        this.playerSpeedModifier = playerSpeedModifier;
        this.enemySpeedModifier = enemySpeedModifier;
    }
}
