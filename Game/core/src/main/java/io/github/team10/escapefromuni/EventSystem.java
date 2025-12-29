package io.github.team10.escapefromuni;

import java.util.EnumMap;
import java.util.Map;

public class EventSystem {

    private IEvent activeEvent;

    private final Map<EventType, Integer> triggeredCounts = new EnumMap<>(EventType.class);
    private final Map<EventType, Integer> maxCounts = new EnumMap<>(EventType.class);

    public EventSystem() {

        for (EventType type: EventType.values()){
            triggeredCounts.put(type, 0);
        }

        maxCounts.put(EventType.NEGATIVE, 5);
        maxCounts.put(EventType.POSITIVE, 4);
        maxCounts.put(EventType.HIDDEN, 3);
    }

    public void onEnterRoom(Room room){
        activeEvent = room.getEvent();

        if(activeEvent != null){
            activeEvent.startEvent();
        }
    }

    public void onExitRoom(Room room) {
        if(activeEvent != null) activeEvent = null;
    }

    public void update(float delta){
        if(activeEvent != null) activeEvent.update(delta);
    }

    public void drawWorld(){
        if(activeEvent != null) activeEvent.draw();
    }

    public void drawUI(){
        if(activeEvent != null) activeEvent.drawUI();
    }

    public void registerEvent(EventType type){
        if(type == EventType.NONE) return;

        int current = triggeredCounts.get(type);
        int max = maxCounts.get(type);

        if (current < max){
            triggeredCounts.put(type, current + 1);
        }

    }

    public int getTriggered(EventType type){
        return triggeredCounts.getOrDefault(type, 0);
    }

    public int getMax(EventType type){
        return maxCounts.getOrDefault(type, 0);
    }
    
    public void reset(){
        for (EventType type: EventType.values()){
            triggeredCounts.put(type, 0);
        }
    }
}
