package io.github.team10.escapefromuni;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }
    
    @Test
    void contrutor_setsCorrectDefault() { 
        assertEquals(0f, timer.getTime());
        assertEquals(300f, timer.getTimeLeftSeconds());
        assertFalse(timer.isFinished());
    }

    @Test 
    void update_drecreasesTime() {
        timer.update(100f);

        assertEquals(200f, timer.getTimeLeftSeconds());
    }

    @Test 
    void update_increasesElapsedTime() {
        timer.update(100f);

        assertEquals(100f, timer.getTime());
    }

    @Test
    void isFinished_neverIsNegative() {
        timer.update(1000f);

        assertEquals(0f, timer.getTimeLeftSeconds());
        assertTrue(timer.isFinished());
    }

    @Test
    void hasReached_returnsTrueWhenReached() {
        timer.update(100f);

        assertTrue(timer.hasReached(50f));
        assertFalse(timer.hasReached(200f));
    }

    @Test
    void isFinished_returnsTrueWhenFinished() {
        timer.update(300f);

        assertTrue(timer.isFinished());
    }

    @Test
    void setFrozen_freezesTimeAndTimeLeft() {
        timer.setFrozen();
        timer.update(100f);

        assertEquals(0f, timer.getTime());
        assertEquals(300, timer.getTimeLeftSeconds());
    } 

    @Test
    void frozenTimer_finishesAndTimerContinues() {
        timer.setFrozen();
        timer.update(30f);
        timer.update(10f);

        assertEquals(10f, timer.getTime());
        assertEquals(290f, timer.getTimeLeftSeconds());
    }

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
