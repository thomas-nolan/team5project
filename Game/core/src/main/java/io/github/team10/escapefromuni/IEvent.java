package io.github.team10.escapefromuni;

/**
 * Abstract base event class.
 * Events are contained within a {@link Room}, where there can be up to 1 event per room.
 */
public interface IEvent {
    EventType getType();

    /**
     * Called when the event starts (e.g. the player enters the room).
     */
    void startEvent();

    /**
     * Called when the event should end. 
     * 
     * Disposes of resources and restores normal gameplay.
     */
    void endEvent();

    /**
     * Called every frame to update the event's logic.
     * @param delta The time elapsed since the last frame in seconds.
     */
    void update(float delta);

    /**
     * Draws world-space graphics related to the event.
     */
    void draw();

    /**
     * Draws UI-space graphics for the event.
     */
    void drawUI();

    /**
     * Returns whether the event has finished.
     */
    boolean IsFinished();
}
