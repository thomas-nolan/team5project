package io.github.team10.escapefromuni;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NEW FOR ASSESSMENT 2
 * This class tests the functionality of the ScoreManager class
 */
public class TimerTest {

    private Timer timer;

    /**
     * Creates a fresh Timer instance before each test
     */
    @BeforeEach
    void setUp() {
        timer = new Timer();
    }
    
    /**
     * Tests that the constructer sets the correct default achievements
     */
    @Test
    void contrutor_setsCorrectDefault() { 
        assertEquals(0f, timer.getTime());
        assertEquals(300f, timer.getTimeLeftSeconds());
        assertFalse(timer.isFinished());
    }

    /**
     * Tests that the update method decreases time left
     */
    @Test 
    void update_drecreasesTime() {
        timer.update(100f);

        assertEquals(200f, timer.getTimeLeftSeconds());
    }

    /**
     * Tests that the update method increases elapsed time
     */
    @Test 
    void update_increasesElapsedTime() {
        timer.update(100f);

        assertEquals(100f, timer.getTime());
    }

    /**
     * Tests that is finished never goes below 0
     */
    @Test
    void isFinished_neverIsNegative() {
        timer.update(1000f);

        assertEquals(0f, timer.getTimeLeftSeconds());
        assertTrue(timer.isFinished());
    }

    /**
     * Tests that has reached returns True when reached x amount of time
     */
    @Test
    void hasReached_returnsTrueWhenReached() {
        timer.update(100f);

        assertTrue(timer.hasReached(50f));
        assertFalse(timer.hasReached(200f));
    }

    /**
     * Tests that is finished returns True when the timer is finished
     */
    @Test
    void isFinished_returnsTrueWhenFinished() {
        timer.update(300f);

        assertTrue(timer.isFinished());
    }

    /**
     * Tests that set frozen freezes elapsed time and time left
     */
    @Test
    void setFrozen_freezesTimeAndTimeLeft() {
        timer.setFrozen();
        timer.update(100f);

        assertEquals(0f, timer.getTime());
        assertEquals(300, timer.getTimeLeftSeconds());
    } 

    /**
     * Tests that set frozen finishes and time left and elapsed time resume
     */
    @Test
    void setFrozen_finishesAndTimerContinues() {
        timer.setFrozen();
        timer.update(30f);
        timer.update(10f);

        assertEquals(10f, timer.getTime());
        assertEquals(290f, timer.getTimeLeftSeconds());
    }

    /**
     * Tests that reset sets the timer back to default
     */
    @Test 
    void reset_setsDefaultTimer() {
        timer.update(100f);
        timer.setFrozen();
        timer.reset();

        assertEquals(0f, timer.getTime());
        assertEquals(300f, timer.getTimeLeftSeconds());
        assertFalse(timer.isFinished());
    }
}
