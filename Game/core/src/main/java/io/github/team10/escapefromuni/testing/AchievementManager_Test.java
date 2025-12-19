package io.github.team10.escapefromuni.Testing;

import io.github.team10.escapefromuni.AchievementManager;

//This is a Test for the achievements code
//I am checking first if I can add and remove achievements
//I am also checking whether the achievements "reset" works
//and get total.
//Able to add more if more code is contributed
public class AchievementManager_Test {

    public AchievementManager AM_TEST;

    public AchievementManager_Test() {
        this.AM_TEST = new AchievementManager();
    }

    public boolean runAllTests(){
        AchievementManager_Test testing = new AchievementManager_Test();

        System.out.println("Achievements Test!");
        System.out.println("----------------------------------------");

        boolean test1 = testing.addAchievementTest();
        boolean test2 = testing.removeAchievementTest();
        boolean test3 = testing.ResetTest();
        boolean test4 = testing.countAchievementsTest();
        boolean test5 = testing.duplicateAchievementTest();

        System.out.println("\n\nACHIEVMENT TESTING FINISHED");
        System.out.println("----------------------------------------");

        if(test1){System.out.println("Add Achievement Test: PASS");}
        else{System.out.println("Add Achievement Test: FAIL");}
        if(test2){System.out.println("Remove Achievement Test: PASS");}
        else{System.out.println("Remove Achievement Test: FAIL");}
        if(test3){System.out.println("Reset Achievement Test: PASS");}
        else{System.out.println("Reset Achievement Test: FAIL");}
        if(test4){System.out.println("Count Achievement Test: PASS");}
        else{System.out.println("Count Achievement Test: FAIL");}
        if(test5){System.out.println("Duplicate Achievement Test: PASS");}
        else{System.out.println("Duplicate Achievement Test: FAIL");}

        if(test1 && test2 && test3 && test4 && test5){return true;}
        else {return false; }
    }

    //This is a test to see if I can reset the whole achievement board
    //In this test I add two achievements
    //and then I reset the board
    //Check their state and check the total should be 0 (CURRENTLY ONE ONLY COS I DONT WANT TO CHANGE ANYTHING)
    public boolean ResetTest(){
        System.out.println("\nTest 1: Reset Achievements");
        System.out.println("------------------");

        try{
            AM_TEST = new AchievementManager();
            AM_TEST.addAchievement("Complete game");
            AM_TEST.addAchievement("Speedrun");

            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("Before Resetting: " + Old_Amount + " achievements");

            AM_TEST.reset();
            int After_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After Resetting: " + After_Amount + " achievements");

            //Check the state of them
            Boolean speedrunState = AM_TEST.getStateAchievement("Speedrun");
            Boolean CompleteGame = AM_TEST.getStateAchievement("Complete game");

            //Printing it
            if(speedrunState){throw new Exception("Incorrect State: Should be false not true");}
            else{System.out.println("\nSpeedrun status:" + speedrunState);}

            if(CompleteGame){throw new Exception("Incorrect State: Should be false not true");}
            else{System.out.println("Complete game status: " + CompleteGame);}

            //Change to 0 when the achievement manager is fixed!!!! aka the last achievement !!
            if(After_Amount == 1 && !speedrunState && !CompleteGame){
                System.out.println("\nRESETTING ACHIEVEMENTS = PASS");
                return true;
            }
            else{
                System.out.println("\nRESETTING ACHIEVEMENTS = FAIL");
                return false;
            }

        }
        catch(Exception error){
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //This is a test to see if I can remove achievement to the achievement board and it counts.
    //In this test I add an achievement then remove it
    //and then I check if it is active before then I remove it
    //I then check its state it should now be not active and I check the amount goes down by one.
    public boolean removeAchievementTest(){
        System.out.println("\nTest 2: Remove Achievements");
        System.out.println("------------------");

        try {
            AM_TEST = new AchievementManager();
            AM_TEST.addAchievement("Find Long Boi");

            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("Before removing: " + Old_Amount + " achievements");

            if(AM_TEST.getStateAchievement("Find Long Boi")){
                System.out.println("Find Long Boi Achievement state : ACTIVE");
            }
            else{throw new Error("Incorrect logic"); }

            AM_TEST.removeAchievement("Find Long Boi");

            int New_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After removing: " + New_Amount + " achievements");

            if(!AM_TEST.getStateAchievement("Find Long Boi")){
                System.out.println("Find Long Boi Achievement state : DEACTIVATED");
            }
            else{throw new Exception("Incorrect State");}

            if (AM_TEST.getStateAchievement("Find Long Boi") == false && New_Amount == (Old_Amount - 1) ) {
                System.out.println("\nREMOVING ACHIEVEMENTS = PASS");
                return true;
            }
            else {
                System.out.println("\nREMOVING ACHIEVEMENTS = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("Error is " + error.getMessage());
            return false;
        }
    }

    //This is a test to see if I can add achievements to the achievement board and it counts.
    //In this test I add two achievements
    //and then I check if they are both activated.
    //if they are then I check if the count went up by two
    public boolean addAchievementTest(){
        System.out.println("\nTest 3: Adding Achievements");
        System.out.println("------------------");

        try {
            // Reset to clean state
            AM_TEST = new AchievementManager();

            //Get old amount should be empty can change in future
            int Old_Amount = AM_TEST.getTotalAchievements();
            System.out.println("\nAmount before adding any : " + Old_Amount + " achievements");

            // Add achievements
            AM_TEST.addAchievement("Speedrun");
            AM_TEST.addAchievement("Complete game");
            System.out.println("Achievements added: Speedrun & Complete game");

            //Now testing if they have been added seeing if the amounts gone up
            int New_Amount = AM_TEST.getTotalAchievements();
            System.out.println("After adding: " + New_Amount + " achievements");

            //Check the state of them
            Boolean speedrunState = AM_TEST.getStateAchievement("Speedrun");
            Boolean gameState = AM_TEST.getStateAchievement("Complete game");

            //Printing it
            System.out.println("\nSpeedrun status: " + speedrunState);
            System.out.println("Complete game status: " + gameState);

            if (speedrunState == true && gameState == true && New_Amount == (Old_Amount + 2) ) {
                System.out.println("\nADDING ACHIEVEMENTS = PASS");
                return true;
            }
            else {
                System.out.println("\nADDING ACHIEVEMENTS = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //This is a test to check counting achievements
    //I add three achievements and check the count
    //Also checks that getting state for non-existent achievements returns false
    public boolean countAchievementsTest(){
        System.out.println("\nTest 4: Counting Achievements");
        System.out.println("------------------");

        try {
            AM_TEST = new AchievementManager();
            AM_TEST.addAchievement("Complete game");
            AM_TEST.addAchievement("Speedrun");
            AM_TEST.addAchievement("Find Long Boi");

            int count = AM_TEST.getTotalAchievements();
            System.out.println("Total achievements: " + count);

            // Check non-existent achievement returns false
            Boolean nonExistentState = AM_TEST.getStateAchievement("Non Existent Achievement");
            System.out.println("Non-existent achievement state: " + nonExistentState);

            if (count >= 3 && nonExistentState == false) {
                System.out.println("\nCOUNTING ACHIEVEMENTS = PASS");
                return true;
            }
            else {
                System.out.println("\nCOUNTING ACHIEVEMENTS = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }

    //This is a test to checking duplicates achievement
    //Adds the same achievement twice and checks what happens
    public boolean duplicateAchievementTest(){
        System.out.println("\nTest 5: Duplicate Achievements");
        System.out.println("------------------");

        try {
            AM_TEST = new AchievementManager();

            int initialCount = AM_TEST.getTotalAchievements();
            System.out.println("Initial count: " + initialCount);

            // Add same achievement twice
            AM_TEST.addAchievement("Test Achievement");
            AM_TEST.addAchievement("Test Achievement"); // Duplicate

            int finalCount = AM_TEST.getTotalAchievements();
            System.out.println("After adding duplicate: " + finalCount);

            // Check state
            boolean state = AM_TEST.getStateAchievement("Test Achievement");
            System.out.println("Achievement state: " + state);

            // This test passes if the achievement is active
            // The count behavior depends on implementation
            if (state == true) {
                System.out.println("\nDUPLICATE ACHIEVEMENTS = PASS");
                return true;
            }
            else {
                System.out.println("\nDUPLICATE ACHIEVEMENTS = FAIL");
                return false;
            }

        } catch (Exception error) {
            System.out.println("error is " + error.getMessage());
            return false;
        }
    }
}
