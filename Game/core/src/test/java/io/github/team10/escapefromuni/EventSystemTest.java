package io.github.team10.escapefromuni;


import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NEW FOR ASSESSMENT 2
 * This class tests the functionality of the EventSystem class
 */
public class EventSystemTest {
    private EventSystem eventSystem;

    /**
     * Creates a fresh EventSystem instance before each test
     */
    @BeforeEach
    void setUp() {
        eventSystem = new EventSystem();
    }

    /**
     * Test case of the IEvent interface
     * Tracks method calls and simulates Event behaviour
     */
    private static class TestEvent implements IEvent {
        private final EventType type;

        int startEventCalls = 0;
        int updateEventCalls = 0;
        int drawEventCalls = 0;
        int drawUICalls = 0;

        boolean isFinished = false;

        /**
         * Creates an test event with specified event type
         * @param type the event type to test
         */
        TestEvent(EventType type) {
            this.type = type;
        }

        /**
         * Returns the event type
         * @return the event type
         */
        @Override
        public EventType getType() {
            return type;
        }

        /**
         * Increments when the event is started
         */
        @Override
        public void startEvent() {
            startEventCalls++;
        }

        /**
         * Increments when the event is ended
         */
        @Override
        public void endEvent() {
            return;
        }

        /**
         * Increments when the event is updated
         */
        @Override
        public void update(float delta) {
            updateEventCalls++;
        }

        /**
         * Increments when the event is drawn
         */
        @Override
        public void draw() {
            drawEventCalls++;
        }

        /**
         * Increments when the UI is drawn
         */
        @Override
        public void drawUI(){
            drawUICalls++;
        }

        /**
         * Inidicates whether the event is finished
         * @Return True if the event is finished
         */
        @Override
        public boolean IsFinished() {
            return isFinished;
        }
    }

    /**
     * Tests that the constructor sets the correct default Events
     */
    @Test
    void constructor_setsCorrectDefaults() {
        assertEquals(0, eventSystem.getTriggered(EventType.NEGATIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.POSITIVE));
        assertEquals(0, eventSystem.getTriggered(EventType.HIDDEN));

        assertEquals(5, eventSystem.getMax(EventType.NEGATIVE));
        assertEquals(4, eventSystem.getMax(EventType.POSITIVE));
        assertEquals(3, eventSystem.getMax(EventType.HIDDEN));
    }

    /**
     * Tests that entering a room with a negative event starts the event
     */
    @Test
    void onEnterRoom_withNegativeEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.NEGATIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    /**
     * Tests that entering a room with a posotive event starts the event
     */
    @Test
    void onEnterRoom_withPosotiveEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    /**
     * Tests that entering a room with a hidden event starts the event
     */
    @Test
    void onEnterRoom_withHiddenEvent_StartsTheEvent() {
        TestEvent event = new TestEvent(EventType.HIDDEN);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);

        assertEquals(1, event.startEventCalls);
    }

    /**
     * Tests that leaving a room clears the event
     */
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

    /**
     * Tests that drawing the world calls the event draw method
     */
    @Test
    void draw_CallsDrawWoldMethod() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);
        eventSystem.drawWorld();

        assertEquals(1, event.drawEventCalls);
    }

    /**
     * Tests that drawing the UI calls the draw UI method
     */
    @Test
    void drawUI_CallsDrawUiMethod() {
        TestEvent event = new TestEvent(EventType.POSITIVE);
        Room room = new Room(null);

        room.setEvent(event);
        eventSystem.onEnterRoom(room);
        eventSystem.drawUI();

        assertEquals(1, event.drawUICalls);
    }

    /**
     * Tests that registering events doesn't go beyond the max
     */
    @Test
    void registerEvent_cantGoBeyondMax() {
        for (int i = 0; i < eventSystem.getMax(EventType.NEGATIVE) + 2; i++) {
            eventSystem.registerEvent(EventType.NEGATIVE);
        }

        assertEquals(eventSystem.getMax(EventType.NEGATIVE), eventSystem.getTriggered(EventType.NEGATIVE));
    }

    /**
     * Tests that registering an event with type NONE doesn't change trigger count
     */
    @Test
    void registerEvent_doesntAddToTriggered_ifNoEventType(){
        eventSystem.registerEvent(EventType.NONE);
        assertEquals(0, eventSystem.getTriggered(EventType.NONE));
    }

    /**
     * Tests that trigger count remains correct
     */
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

    /**
     * Tests that reset method rests trigger count back to zero
     */
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
