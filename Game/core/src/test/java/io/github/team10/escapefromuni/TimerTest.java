package io.github.team10.escapefromuni;

import java.beans.Transient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {
    
    @Test
    void contrutor_SetsCorrectDefault() { 
        Timer timer = new Timer();

        assertEquals(0f, timer.getTime());
        assertEquals(300f, timer.getTimeLeftSeconds());
        assertFalse(timer.isFinished());
    }

    @Test 
    void update_drecreasesTime() {
        Timer timer = new Timer();

        timer.update(100f);
        assertEquals(200f, timer.getTimeLeftSeconds());
    }

    @Test 
    void update_increasesElapsedTime() {
        Timer timer = new Timer();
        timer.update(100f);
        assertEquals(100f, timer.getTime());
    }
}
