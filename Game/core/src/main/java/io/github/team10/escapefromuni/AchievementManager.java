package io.github.team10.escapefromuni;

import java.util.HashMap;


public class AchievementManager {

    HashMap<String, Boolean> achievements = new HashMap<String, Boolean>();

    public AchievementManager(){

        //ones that are commented out arent done yet bc 
        //  they arent possible yet
        //'No incorrect answers' is true by default, you lose the achievement
        //  if you fail a question

        achievements.put("Complete game", false);
        achievements.put("Speedrun", false);
        achievements.put("All positive events", false);
        achievements.put("All negative events", false);
        achievements.put("All hidden events", false);
        achievements.put("Find Long Boi", false);
        achievements.put("No incorrect answers", true);

    }

    public void addAchievement(String achievementName){
        
        achievements.put(achievementName,true);

    }

    public void removeAchievement(String achievementName){
        
        achievements.put(achievementName,false);

    }

    public int getTotalAchievements(){

        int total = 0;

        for (Boolean i : achievements.values()){
            if (i){
                total += 1;
            }
        }

        return total;
    }

    public String[] getAchievements(){

        String[] currentAchievements = new String[getTotalAchievements()];

        int index = 0;
        for (String key : achievements.keySet()){

            if (achievements.get(key)){
                
                currentAchievements[index] = key;
                index += 1;

            }
        }

        return currentAchievements;

    }

    public void reset(){

        achievements.put("Complete game", false);
        achievements.put("Speedrun", false);
        achievements.put("All positive events", false);
        achievements.put("All negative events", false);
        achievements.put("All hidden events", false);
        achievements.put("Find Long Boi", false);
        achievements.put("No incorrect answers", true);

    }


}
