package io.github.team10.escapefromuni;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileManager {
    public Map<String,Integer> readFile(String fileName) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                map.put(parts[0], Integer.valueOf(parts[1]));
            }
        } catch (IOException e) {
            System.out.println("READ ERROR");
            e.printStackTrace();
        }

        return map;
    }

    public void writeFile(String fileName, Map<String,Integer> scores) {
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

    Map<String, Integer> sortMap(Map<String,Integer> map) {
        Map<String, Integer> sortedMap = map.entrySet()
            .stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()) // Descending order
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));
        return sortedMap;
    }
}
