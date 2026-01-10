package io.github.team10.escapefromuni;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NEW FOR ASSESSMENT 2
 * This class tests the functionality of the FileManager class
 */
public class FileManagerTest {
    
    private FileManager fileManager;

    /**
     * Creates a fresh FileManager instance before each test
     */
    @BeforeEach
    void setUp() {
        fileManager = new FileManager();
    }

    /**
     * Tests that reading a scores file returns a map containing the
     * correct player names and scores
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test
    void readFile_returnsMap(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file.toString()))) { 
            writer.println("Tom,10");
            writer.println("Will,11");
            writer.println("Stan,12");
        }

        Map<String,Integer> map = fileManager.readFile(file.toString());

        assertEquals(3, map.size());
        assertEquals(10, map.get("Tom"));
        assertEquals(11, map.get("Will"));
        assertEquals(12, map.get("Stan"));
    }

    /**
     * Tests that with duplicate player entries the most recent score overrides
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test
    void readFile_latestResultOverridesPreviousOne(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file.toString()))) { 
            writer.println("Tom,10");
            writer.println("Tom,11");
            writer.println("Tom,9");
        }

        Map<String,Integer> map = fileManager.readFile(file.toString());

        assertEquals(1, map.size());
        assertEquals(9, map.get("Tom"));
    }

    /**
     * Tests that with an empty file a empty map is returned
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test
    void readFile_OnEmptyFileReturnsEmptyMap(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");

        Map<String,Integer> map = fileManager.readFile(file.toString());

        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    /**
     * Tests that read file string only returns the first line of the file
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test 
    void readFileString_ReturnsFirstLine(@TempDir Path tempdir) throws IOException {
        Path file = tempdir.resolve("scores.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file.toString()))) { 
            writer.println("Tom,10");
            writer.println("Will,11");
            writer.println("Stan,12");
        }

        String line = fileManager.readFileString(file.toString());
        assertEquals("Tom,10", line);
    }

    /**
     * Tests that with an empty file read file string returns NULL
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test 
    void readFileString_OnEmptyFileReturnsNull(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");

        String line = fileManager.readFileString(file.toString());
        assertNull(line);
    }

    /**
     * Tests that when trying to write more than five entries only a maximum
     * of five are actually written
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test
    void writeFile_WritesMaximumOfFive(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Tom", 10);
        map.put("Harry", 10);
        map.put("Will", 10);
        map.put("Stan", 10);
        map.put("Mimi", 10);
        map.put("Lottie", 10);
        map.put("Ruth", 10);

        fileManager.writeFile(file.toString(), map);
        Map<String, Integer> result = fileManager.readFile(file.toString());

        assertEquals(5, result.size());
        assertEquals(10, result.get("Tom"));
        assertEquals(10, result.get("Harry"));
        assertEquals(10, result.get("Will"));
        assertEquals(10, result.get("Stan"));
        assertEquals(10, result.get("Mimi"));

    }

    /**
     * Tests that write file writes the scores correctly
     * @param tempDir Temporary directory used by JUnit
     * @throws IOException If can't access the file
     */
    @Test
    void writeFile_WritesScoresCorrectly(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("scores.csv");

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Tom", 10);
        map.put("Harry", 10);
        map.put("Will", 10);

        fileManager.writeFile(file.toString(), map);
        Map<String, Integer> result = fileManager.readFile(file.toString());

        assertEquals(3, result.size());
        assertEquals(10, result.get("Tom"));
        assertEquals(10, result.get("Harry"));
        assertEquals(10, result.get("Will"));
    }
}
