package io.github.team10.escapefromuni;


import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class EventSystemTest {
    private EventSystem eventSystem;

    @BeforeEach
    void setUp() {
        eventSystem = new EventSystem();
    }

    private static class TestEvent implements IEvent { 
        private final EventType type;

        int startEventCalls = 0;
        int updateEventCalls = 0;
        int drawEventCalls = 0;
        int drawUICalls = 0;

        boolean isFinished = false;

        TestEvent(EventType type) {
            this.type = type;
        }

        @Override
        public EventType getType() {
            return type;
        }

        @Override
        public void startEvent() {
            startEventCalls++;
        }

        @Override
        public void endEvent() {
            return;
        }

        @Override
        public void update(float delta) {
            updateEventCalls++;
        }

        @Override
        public void draw() {
            drawEventCalls++;
        }

        @Override
        public void drawUI(){
            drawUICalls++;
        }

        @Override
        public boolean IsFinished() {
            return isFinished;
        }
    }

    @Test 
    void constructor_setsCorrectDefaults() {
        assertEquals(0, eventSystem.getTriggered(EventType.NEGATIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.POSITIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.HIDDEN));

        assertEquals(5, eventSystem.getMax(EventType.NEGATIVE));
        assertEquals(4, eventSystem.getMax(EventType.POSITIVE));
        assertEquals(3, eventSystem.getMax(EventType.HIDDEN));
    }

    @Test
    void onEnterRoom_withNegativeEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.NEGATIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    @Test
    void onEnterRoom_withPosotiveEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    @Test
    void onEnterRoom_withHiddenEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.HIDDEN);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    @Test
    void onExitRoom_ClearsActiveEvent() {
        TestEvent event = new TestEvent(EventType.HIDDEN);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);
        assertEquals(1, event.startEventCalls);

        eventSystem.onExitRoom(room);
        eventSystem.update(10f);
        
        assertEquals(0, event.updateEventCalls);
    }

    @Test
    void drawWold_CallsDrawWoldMethod() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);
        eventSystem.drawWorld();

        assertEquals(1, event.drawEventCalls);
    }

    @Test
    void drawUI_CallsDrawUiMethod() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);
        eventSystem.drawUI();

        assertEquals(1, event.drawUICalls);
    }

    @Test
    void registerEvent_cantGoBeyondMax() {
        for (int i = 0; i < eventSystem.getMax(EventType.NEGATIVE) + 1; i++) { 
            eventSystem.registerEvent(EventType.NEGATIVE);
        }

        assertEquals(eventSystem.getMax(EventType.NEGATIVE), eventSystem.getTriggered(EventType.NEGATIVE));
    }

    @Test
    void registerEvent_doesntAddToTriggered_ifNoEventType(){
        eventSystem.registerEvent(EventType.NONE);
        assertEquals(0, eventSystem.getTriggered(EventType.NONE));
    }

    @Test
    void getTriggered_keepsValidCount() {
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.POSITIVE);

        assertEquals(3, eventSystem.getTriggered(EventType.HIDDEN));
        assertEquals(1, eventSystem.getTriggered(EventType.POSITIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.NEGATIVE));
    }

    @Test 
    void reset_setsTriggeredBackToZero(){
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.HIDDEN);
        eventSystem.registerEvent(EventType.POSITIVE);

        eventSystem.reset();

        assertEquals(0, eventSystem.getTriggered(EventType.HIDDEN));
        assertEquals(0, eventSystem.getTriggered(EventType.POSITIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.NEGATIVE));
    }



    


}
