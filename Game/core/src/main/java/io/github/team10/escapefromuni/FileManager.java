package io.github.team10.escapefromuni;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * NEW FOR ASSESSMENT 2.
 * A class used to manage the files within the game.
 * Mostly used for storing and retrieving data such as scores and config values.
 */
public class FileManager {
  
  /**
   * NEW FOR ASSESSMENT 2.
   * This is a method used to read the contents of a file and write it to a map
   * 
   * @param fileName the name of the file to read
   * @return a map containing the key-pair values
   */
  public Map<String, Integer> readFile(String fileName) {
    Map<String, Integer> map = new HashMap<String, Integer>();

    // NEW FOR ASSESSMENT 2 - attemps to read the file and places it in a map
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        map.put(parts[0], Integer.valueOf(parts[1]));
      }
    // NEW FOR ASSESSMENT 2 - Prints an error if the file cannot be read
    } catch (IOException e) {
      System.out.println("READ ERROR with file: " + fileName);
      e.printStackTrace();
    }

    return map;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Reads and returns the first line of a text file.
   * 
   * @param fileName the file to read
   * @return the first line of the file 
   */
  public String readFileString(String fileName) {

    // NEW FOR ASSESSMENT 2 - attemps to read the first line, returns an error if it cannot
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line = reader.readLine();
      return line;
    } catch (IOException e) {
      System.out.println("Read Error with file: " + fileName);
      e.printStackTrace();
    }
    return null;
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Writes the content of the map to a file, 
   * only writes the first 5 lines.
   * 
   * @param fileName the name of the file you wish to write to
   * @param scores the map of the scores to write
   */
  public void writeFile(String fileName, Map<String, Integer> scores) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
      Set<String> keys = scores.keySet();
      int count = 0;
      for (String key : keys) {
        String line = key + "," + scores.get(key);
        writer.println(line);
        count++;
        if (count == 5) { // Reads the first 5
          break;
        }
      }
    } catch (IOException e) {
      System.out.println("WRITE ERROR");
      e.printStackTrace();
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Writes a single line of text to the file
   * 
   * @param fileName the name of the file to write to 
   * @param line the string to write into the desired file
   */
  public void writeFileString(String fileName, String line) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
      writer.println(line);
    } catch (IOException e) {
      System.out.println("Write Error");
      e.printStackTrace();
    }
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Sorts the map by descending order, used for the leaderboard.
   * 
   * @param map a map to sort
   * @return a new map sorted by descending order
   */
  Map<String, Integer> sortMap(Map<String, Integer> map) {
    Map<String, Integer> sortedMap = map.entrySet()
        .stream()
        // NEW TO ASSESSMENT 2 - Descending order
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) 
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (a, b) -> a,
            LinkedHashMap::new
        ));
    return sortedMap;
  }
}
