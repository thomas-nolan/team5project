package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * AudioManager for handling all game audio.
 * Manages background music for menus and gameplay, and sound effects for different events and button clicks.
 */
public class AudioManager {
    private static AudioManager instance;
    
    // Music tracks (looping background music)
    private Music menuMusic;
    private Music gameMusic;
    
    // Sound effects (played once when the even is triggered or button click)
    private Sound clickSound;
    private Sound negativeEventSound;
    private Sound positiveEventSound;
    private Sound hiddenEventSound;
    
    
    private Music currentMusic;
    private float currentVolume = 0.5f;
    
    
    private AudioManager() {
        // loads music and sound files from the files
        loadAudio();
    }
    
    //gets the audiomanager instance
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
    // Loads all audio files
    private void loadAudio() {
        try {
            // Load music tracks 
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.mp3"));
            gameMusic = Gdx.audio.newMusic(Gdx.files.internal("GameMusic.mp3"));
            
            // for the gameplay and menu music to loop 
            menuMusic.setLooping(true);
            gameMusic.setLooping(true);
            
            // Set initial volume
            menuMusic.setVolume(currentVolume);
            gameMusic.setVolume(currentVolume);
            
            // Load sound effects
            clickSound = Gdx.audio.newSound(Gdx.files.internal("Click.mp3"));
            negativeEventSound = Gdx.audio.newSound(Gdx.files.internal("NegMus.mp3"));
            positiveEventSound = Gdx.audio.newSound(Gdx.files.internal("PosMus.mp3"));
            hiddenEventSound = Gdx.audio.newSound(Gdx.files.internal("HidMus.mp3"));
            
        } catch (Exception e) {
            Gdx.app.error("AudioManager", "Error loading audio files: " + e.getMessage());
        }
    }
    
    // Plays menu music
    public void playMenuMusic() {
        if (currentMusic != menuMusic) {
            stopCurrentMusic();
            currentMusic = menuMusic;
            menuMusic.play();
        }
    }
    
    // Plays game music
    public void playGameMusic() {
        if (currentMusic != gameMusic) {
            stopCurrentMusic();
            currentMusic = gameMusic;
            gameMusic.play();
        }
    }
    
    /**
     * Plays event sound effect based on event type.
     * @param eventType The type of event (NEGATIVE, POSITIVE, or HIDDEN)
     */
    public void playEventSound(EventType eventType) {
        Sound eventSound = null;
        
        switch (eventType) {
            case NEGATIVE:
                eventSound = negativeEventSound;
                break;
            case POSITIVE:
                eventSound = positiveEventSound;
                break;
            case HIDDEN:
                eventSound = hiddenEventSound;
                break;
            default:
                break;
        }
        
        if (eventSound != null) {
            eventSound.play(currentVolume); 
        }
    }
    
    //click sound effect
    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(currentVolume);
        }
    }
    
    // Stops the currently playing music
    private void stopCurrentMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }
    
    //pauses the currently playing music
    public void pauseMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }
    
    //resumes the currently playing music
    public void resumeMusic() {
        if (currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }
    
    // Sets the master volume for all audio from 0.1f to 1.0f
    public void setVolume(float volume) {
        this.currentVolume = Math.max(0f, Math.min(1f, volume));
        
        if (menuMusic != null) menuMusic.setVolume(currentVolume);
        if (gameMusic != null) gameMusic.setVolume(currentVolume);
    }

    public float getVolume() {
        return currentVolume;
    }
    
    //Sets the volume for every audio
    
    public void setMusicVolume(float volume) {
        if (menuMusic != null) menuMusic.setVolume(volume);
        if (gameMusic != null) gameMusic.setVolume(volume);
    }

    public void setSoundVolume(float volume) {
    }
    
    // Disposes all audio 
    public void dispose() {
        
        if (menuMusic != null) menuMusic.dispose();
        if (gameMusic != null) gameMusic.dispose();
        if (clickSound != null) clickSound.dispose();
        if (negativeEventSound != null) negativeEventSound.dispose();
        if (positiveEventSound != null) positiveEventSound.dispose();
        if (hiddenEventSound != null) hiddenEventSound.dispose();
    }
}