package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class DifficultySetup {

    private String fileName;
    private Difficulty difficulty;

    public DifficultySetup() {
        this.fileName = "difficulty.json";
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        writeDifficulty();
    }

    private void writeDifficulty() {
        DifficultyModifiers modifiers;
        if (difficulty == Difficulty.HARD) {
            modifiers = new DifficultyModifiers(0.8f,1.2f);
            //modifiers = new DifficultyModifiers(0f,2f); // TEST
        } else if (difficulty == Difficulty.EASY) {
            modifiers = new DifficultyModifiers(1.2f,0.75f);
            //modifiers = new DifficultyModifiers(2f,0f); // TEST
        } else {
            modifiers = new DifficultyModifiers(1f,1f);
        }
        Json json = new Json();
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(json.prettyPrint(modifiers),false);
        //System.out.println(file.file().getAbsolutePath());
    }

    public DifficultyModifiers readDifficulty() {
        Json json = new Json();
        FileHandle file = Gdx.files.local(fileName);
        DifficultyModifiers difficulty = json.fromJson(DifficultyModifiers.class, file);
        return difficulty;
    }

}
