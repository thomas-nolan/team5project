package io.github.team10.escapefromuni;

public class EventSystem {

    private IEvent activeEvent;

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
    
}
