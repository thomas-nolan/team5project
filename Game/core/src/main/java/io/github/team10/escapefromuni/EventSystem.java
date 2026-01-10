package io.github.team10.escapefromuni;

import java.util.EnumMap;
import java.util.Map;

/**
 * NEW FOR ASSESSMENT 2.
 * This class handles the logic for starting events, rendering event
 * related content and tracking events across the game.
 */
public class EventSystem {

  private IEvent activeEvent;

  // NEW FOR ASSESSMENT 2 - Used to track the events
  private final Map<EventType, Integer> triggeredCounts = new EnumMap<>(EventType.class);
  private final Map<EventType, Integer> maxCounts = new EnumMap<>(EventType.class);

  /**
   * NEW FOR ASSESSMENT 2.
   * This constructor creates a new EventSystem and sets up
   * the events. It also sets up the event counter
   */
  public EventSystem() {

    // NEW FOR ASSESSMENT 2 - Initialises the event counter
    for (EventType type : EventType.values()) {
      triggeredCounts.put(type, 0);
    }

    // NEW FOR ASSESSMENT 2
    // Sets the number of events for each type in the event counter
    maxCounts.put(EventType.NEGATIVE, 5);
    maxCounts.put(EventType.POSITIVE, 4);
    maxCounts.put(EventType.HIDDEN, 3);
  }

  /**
   * This activates the event upon entering the room.
   *
   * @param room the {@link Room} object you enter
   */
  public void onEnterRoom(Room room) {
    activeEvent = room.getEvent();

    if (activeEvent != null) {
      activeEvent.startEvent();
    }
  }

  /**
   * Finishes the event upon exiting the room.
   *
   * @param room the {@link Room} object you leave
   */
  public void onExitRoom(Room room) {
    if (activeEvent != null) {
      activeEvent = null;
    }
  }

  /**
   * Updates the logic for the event upon every frame.
   *
   * @param delta the time between each frame
   */
  public void update(float delta) {
    if (activeEvent != null) {
      activeEvent.update(delta);
    }
  }

  /**
   * This renders the world visuals for the active event.
   */
  public void drawWorld() {
    if (activeEvent != null) {
      activeEvent.draw();
    }
  }

  /**
   * This method draws the UI for the active event.
   */
  public void drawUI() {
    if (activeEvent != null) {
      activeEvent.drawUI();
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This method used for the event counter, registering an event
   * if it is interacted with.
   *
   * @param type the type of event
   */
  public void registerEvent(EventType type) {
    if (type == EventType.NONE) {
      return;
    }

    // NEW FOR ASSESSMENT 2 - Gets the current event type and the max number of that type
    int current = triggeredCounts.get(type);
    int max = maxCounts.get(type);

    // NEW FOR ASSESSMENT 2 - If the max hasn't been excided add the event to the counter
    if (current < max) {
      triggeredCounts.put(type, current + 1);
    }

  }

  /**
   * NEW FOR ASSESSMENT 2.
   * This returns the number of times an event type has been triggered.
   *
   * @param type type of the event
   * @return the number of times an event has been triggered
   */
  public int getTriggered(EventType type) {
    return triggeredCounts.getOrDefault(type, 0);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Returns the max number of times an event type may be triggered
   *
   * @param type the type of event
   * @return the max number of times an event type may be triggered
   */
  public int getMax(EventType type) {
    return maxCounts.getOrDefault(type, 0);
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Resets the events counter
   */
  public void reset() {
    for (EventType type : EventType.values()) {
      triggeredCounts.put(type, 0);
    }
  }
}
